<div align="center">

# 🍽️ API Restaurante

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=flat-square&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring_Security-6.4.6-brightgreen?style=flat-square&logo=springsecurity)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![H2](https://img.shields.io/badge/H2-in--memory-lightgrey?style=flat-square)
![Flyway](https://img.shields.io/badge/Flyway-migrations-red?style=flat-square)
![WebSocket](https://img.shields.io/badge/WebSocket-STOMP-purple?style=flat-square)
![JWT](https://img.shields.io/badge/JWT-HS256-black?style=flat-square)
![Status](https://img.shields.io/badge/status-em_desenvolvimento-yellow?style=flat-square)

**API REST para gestão de restaurante com pedidos, avaliações e fidelidade orientada a eventos.**

</div>

---

## O que este projeto é

Backend completo de restaurante construído como projeto de estudo para aplicar, na prática, os conceitos de arquitetura hexagonal, DDD, padrão Outbox e comunicação orientada a eventos entre módulos desacoplados.

Não é um CRUD. Cada módulo tem domínio próprio, regras de negócio encapsuladas nas entidades e se comunica com os outros exclusivamente via eventos — sem chamadas diretas entre serviços.

---

## Decisões Arquiteturais

### Arquitetura Hexagonal + Use Cases

**O quê:** cada operação de negócio vive em uma classe `CasoDeUso` com método `executar()`. Repositórios e adaptadores externos são acessados via interfaces (portas), nunca diretamente.

**Por quê:** isola o domínio de frameworks. Se trocar JPA por JDBC ou PostgreSQL por MongoDB, o caso de uso não muda. O domínio não sabe que Spring existe.

```java
// O caso de uso não depende de JPA, Spring Data, nem HTTP.
// Depende apenas da interface FidelidadeRepositorio — definida no próprio domínio.
@Service
@AllArgsConstructor
public class ProcessarPontuacaoAposPedidoEntregueCasoDeUso {

    private final FidelidadeRepositorio repositorio;

    @Transactional
    public void executar(Long clienteId, BigDecimal valorTotal) {
        Fidelidade fidelidade = repositorio.buscarPorClienteId(clienteId)
                .orElseThrow(() -> new FidelidadeNaoEncontradaExcecao(
                        "Fidelidade não encontrada para o cliente: " + clienteId));
        int pontos = CalculadoraDeFidelidade.calcular(valorTotal);
        fidelidade.creditarPontos(pontos, "Pontuação por pedido entregue");
        repositorio.salvar(fidelidade);
    }
}
```

---

### Transactional Outbox Pattern

**O problema que resolve:** publicar um evento Spring dentro de uma `@Transactional` tem dois riscos opostos. Se o evento for publicado antes do commit e a transação fizer rollback, o consumidor já processou algo que não existe no banco. Se for publicado após o commit e a JVM cair nesse instante, o evento se perde para sempre — sem nenhuma forma de recuperação.

**A solução:** o evento é persistido na tabela `outbox_eventos` **dentro da mesma transação** que altera o dado de negócio. Commit único — o dado e o registro do evento existem juntos ou falham juntos. Um Scheduler independente processa os pendentes e reprocessa falhas.

```java
// CadastrarClienteCasoDeUso.java
// OutboxEvento e Cliente são persistidos na mesma transação.
// Se rollback ocorrer, o evento não existe. Consistência garantida.
@Transactional
public ClienteDTO executar(CadastrarClienteDTO dto) {
    validator.validarNovoCliente(dto, false);
    Cliente clienteSalvo = repository.salvar(mapper.mappearNovoCliente(dto));

    ClienteCriadoPayload payload = new ClienteCriadoPayload(clienteSalvo.getId());
    outboxRepositorio.salvar(OutboxEvento.criar(
            Agregado.CLIENTE,
            clienteSalvo.getId(),
            GatilhoEvento.CLIENTE_CADASTRADO,
            TipoEvento.CRIAR_FIDELIDADE_CLIENTE,
            objectMapper.writeValueAsString(payload)));

    publicadorDeEvento.publishEvent(new ClienteCriadoEvento(clienteSalvo.getId()));
    return mapper.mapearClienteParaClienteDTO(clienteSalvo);
}
```

O Scheduler varre eventos com `status = PENDENTE` a cada 60 segundos e delega ao Handler correto via mapa `TipoEvento → Handler`:

```java
// OutboxDespachante.java
@Scheduled(fixedDelay = 60000)
@Transactional
public void reprocessarPendentes() {
    outboxRepositorio.buscarPendentes().forEach(evento -> {
        OutboxEventoHandler handler = handlers.get(evento.getTipo());
        if (handler == null) return;
        try {
            handler.processar(evento);
            evento.processar();        // status → PROCESSADO
        } catch (Exception e) {
            evento.registrarFalha();   // tentativas++; status → ERRO após 3x
        }
        outboxRepositorio.salvar(evento);
    });
}
```

A tabela `outbox_eventos` tem constraint `UNIQUE (agregado_tipo, agregado_id, tipo)` — impossível registrar o mesmo evento duas vezes para o mesmo agregado.

---

### Event-Driven com `@TransactionalEventListener` + `@Async` + `REQUIRES_NEW`

**Por quê essa combinação específica:**

- `@TransactionalEventListener` — o listener só é invocado **após o commit** da transação que publicou o evento. Garante que o dado já está no banco quando o consumidor lê.
- `@Async` — executa em thread separada. Sem isso, o listener rodaria na thread da transação original, bloqueando a resposta ao cliente.
- `REQUIRES_NEW` — cria uma transação independente. Se o processamento do evento falhar, apenas essa transação sofre rollback — o dado de negócio já está commitado e seguro.

```java
// PedidoEntregueOuvinte.java — módulo Fidelidade
// As três anotações trabalham juntas: segurança, não-bloqueio e isolamento de falha.
@Async
@TransactionalEventListener
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void quandoPedidoEntregue(PedidoEntregueEvento evento) {
    OutboxEvento outbox = outboxRepositorio.buscarPorAgregadoEIdAgregadoETipoEvento(
            Agregado.PEDIDO, evento.pedido().getId(), TipoEvento.COMPUTAR_PONTUACAO_FIDELIDADE);
    try {
        casoDeUso.executar(
                evento.pedido().getCliente().clienteId(),
                evento.pedido().getValorTotal());
        outbox.processar();
    } catch (Exception e) {
        outbox.registrarFalha();
    } finally {
        outboxRepositorio.salvar(outbox);
    }
}
```

---

### Domínio sem setters — Agregados com comportamento real

**Por quê:** entidade com setter público é um objeto anêmico. Qualquer camada pode colocar o agregado em estado inválido. Aqui, o estado só muda via métodos de domínio que encapsulam e validam as invariantes.

```java
// Fidelidade.java — sem setter público. Estado muda apenas via creditarPontos().
public void creditarPontos(Integer pontos, String motivo) {
    if (motivo == null || motivo.isBlank())
        throw new MotivoRegistroVazioExcecao("O motivo da pontuação não pode ser vazio.");
    Integer novoSaldo = this.pontuacaoAtual.valor() + pontos;
    this.historico.add(RegistroPontuacao.criar(pontos, motivo, novoSaldo));
    this.pontuacaoAtual = this.pontuacaoAtual.acrescentar(pontos); // VO imutável — retorna nova instância
}
```

Value Objects são `record` Java — imutáveis por definição:

```java
// PontuacaoAtual.java — record garante imutabilidade em nível de linguagem.
@Embeddable
public record PontuacaoAtual(Integer valor) {
    public PontuacaoAtual {
        if (valor == null || valor < 0)
            throw new PontuacaoAtualInvalidaExcecao("A pontuação atual não pode ser nula ou negativa.");
    }
    public static PontuacaoAtual zero() { return new PontuacaoAtual(0); }
    public PontuacaoAtual acrescentar(Integer pontos) { return new PontuacaoAtual(this.valor + pontos); }
}
```

---

### Inversão de Dependência entre Módulos

**O problema:** o módulo `Fidelidade` precisa atualizar o `Cliente` após criar um registro. Se importasse `ClienteRepositorio` diretamente, haveria acoplamento bidirecional — qualquer mudança no módulo `Cliente` quebraria `Fidelidade`.

**A solução:** `Fidelidade` define uma porta de saída (`AtualizarReferenciaClientePorta`). O módulo `Cliente` fornece o adaptador. A dependência aponta para dentro do domínio — que não conhece a implementação.

```
Fidelidade (define a porta) ──▶ AtualizarReferenciaClientePorta (interface)
                                              ▲
                               FidelidadeClienteAdaptador (módulo Cliente implementa)
```

---

## Diagrama de Módulos

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           API Restaurante                               │
│                                                                         │
│  ┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────────────┐  │
│  │ Produto  │    │ Cardápio │    │  Cupom   │    │   Compartilhado  │  │
│  └────┬─────┘    └────┬─────┘    └────┬─────┘    │  OutboxEvento    │  │
│       │               │               │           │  OutboxReposit.  │  │
│  ┌────▼───────────────▼───────────────▼────────┐  │  OutboxDespach.  │  │
│  │                   Pedido                    │  └──────────────────┘  │
│  │  máquina de estados → publica eventos       │                        │
│  └──┬──────────────────────────────────────────┘                        │
│     │ PedidoEntregueEvento / PedidoCanceladoEvento                      │
│     ├──────────────────────────────┐                                    │
│     ▼                              ▼                                    │
│  ┌──────────┐              ┌──────────────┐                             │
│  │Fidelidade│◀─────────────│  Avaliação   │  AvaliacaoConcluidaEvento   │
│  │          │              │  Schedulers  │                             │
│  └──────────┘              └──────────────┘                             │
│     ▲                                                                   │
│  ┌──┴───────┐                                                           │
│  │ Usuario  │──── ClienteCriadoEvento ──────────────────────────────▶  │
│  │ Cliente  │                                                           │
│  └──────────┘                                                           │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Sistema de Fidelidade — Fluxo Completo

O saldo de pontos é construído por dois fluxos independentes, ambos orientados a eventos:

### Fluxo 1 — Pedido Entregue

```
Operador marca pedido como ENTREGUE
         │
         ▼
AtualizarStatusPedidoCasoDeUso  (transação única)
├── pedido.mudarStatus(ENTREGUE)
├── outbox.salvar(COMPUTAR_PONTUACAO_FIDELIDADE, payload={clienteId, valorTotal})
├── outbox.salvar(CRIAR_AVALIACAO, payload={clienteId, itens[]})
└── publishEvent(PedidoEntregueEvento)
         │
         │ após commit ──────────────────────────────────────────────────────┐
         ▼  nova transação (REQUIRES_NEW)                          fallback  │
PedidoEntregueOuvinte                                         OutboxDespachante
└── ProcessarPontuacaoAposPedidoEntregueCasoDeUso             (a cada 60s)
    └── CalculadoraDeFidelidade.calcular(valorTotal)
        ├── ≤ R$50,00  → 1 ponto
        ├── ≤ R$90,00  → 2 pontos
        └── > R$90,00  → 3 pontos
```

### Fluxo 2 — Avaliação Concluída

```
Cliente conclui avaliação via POST /avaliacao/cliente/concluir
         │
         ▼
ConcluirAvaliacaoAvaliadaCasoDeUso  (transação única)
├── avaliacao.concluirAvaliacao(respostaGeral, respostasPorItem)
├── deriva ClassificacaoAvaliacao
├── outbox.salvar(COMPUTAR_PONTUACAO_AVALIACAO_REALIZADA, payload={clienteId, classificacao, totalItens})
└── publishEvent(AvaliacaoConcluidaEvento)
         │
         │ após commit
         ▼  nova transação (REQUIRES_NEW)
AvaliacaoConcluidaOuvinte
└── ProcessaPontuacaoAposAvaliacaoConcluidaCasoDeUso
    └── pontos = pts_classificacao + (2 × itensAvaliados)
        ├── NAO_AVALIADO → 0 pts base
        ├── INSATISFEITO → 1 pt  base
        ├── MODERADO     → 2 pts base
        └── SATISFEITO   → 3 pts base
```

**Resultado real no banco** (cliente com 4 pedidos entregues + 3 avaliações):

```
POSICAO  PONTOS  MOTIVO                                          SALDO_NO_DIA
0        1       Pontuação por pedido entregue                   1
1        2       Pontuação por pedido entregue                   3
2        2       Pontuação por pedido entregue                   5
3        1       Pontuação por pedido entregue                   6
4        2       Pontuação gerada por ter avaliado pedidos...    8
5        3       Pontuação gerada por ter avaliado pedidos...    11
6        5       Pontuação gerada por ter avaliado pedidos...    16
```

---

## Fluxo de Eventos

| Gatilho | Tipo de Evento | Módulo Consumidor | Efeito |
|---|---|---|---|
| `CLIENTE_CADASTRADO` | `CRIAR_FIDELIDADE_CLIENTE` | Fidelidade | Cria registro com saldo zero + vincula `fidelidade_referencia_id` no Cliente |
| `PEDIDO_CRIADO` | `BAIXAR_ESTOQUE_ASSOCIACAO` | Cardápio | Reduz quantidade disponível na associação cardápio-produto |
| `PEDIDO_CRIADO` | `BAIXAR_ESTOQUE_PRODUTO` | Produto | Reduz estoque físico do produto |
| `PEDIDO_CRIADO` | `CONSUMIR_CUPOM` | Cupom | Marca cupom como consumido para o cliente |
| `PEDIDO_ENTREGUE` | `COMPUTAR_PONTUACAO_FIDELIDADE` | Fidelidade | Credita 1–3 pontos conforme valor total do pedido |
| `PEDIDO_ENTREGUE` | `CRIAR_AVALIACAO` | Avaliação | Cria solicitação de avaliação com status `PENDENTE` |
| `PEDIDO_CANCELADO` | `ESTORNAR_ESTOQUE_ASSOCIACAO` | Cardápio | Reverte baixa de quantidade na associação |
| `PEDIDO_CANCELADO` | `ESTORNAR_ESTOQUE_PRODUTO` | Produto | Reverte baixa no estoque físico |
| `AVALIACAO_CONCLUIDA` | `COMPUTAR_PONTUACAO_AVALIACAO_REALIZADA` | Fidelidade | Credita pontos pela classificação e itens avaliados |

Todos os eventos são rastreados em `outbox_eventos`. Cada linha tem `gatilho`, `tipo`, `payload` JSON, `status` (`PENDENTE` → `PROCESSADO` | `ERRO`), número de tentativas e timestamps.

---

## Requisitos Funcionais

### Cliente

| # | Funcionalidade |
|---|---|
| RF01 | Cadastrar conta com CPF, e-mail, telefone e endereço |
| RF02 | Autenticar via CPF + senha — recebe JWT com role e expiração configurável |
| RF03 | Criar pedido vinculado a um cardápio ativo com itens e endereço de entrega |
| RF04 | Aplicar cupom de desconto percentual ou valor fixo no pedido |
| RF05 | Consultar histórico de pedidos próprios |
| RF06 | Receber solicitação de avaliação automaticamente após pedido entregue |
| RF07 | Responder avaliação com nota geral (1–5) e avaliação individual por item |
| RF08 | Encerrar avaliação sem nota (opt-out) sem penalização |
| RF09 | Acumular pontos automaticamente por pedidos entregues e avaliações concluídas |
| RF10 | Consultar saldo e histórico de pontos *(backlog)* |
| RF11 | Usar pontos como desconto em novo pedido *(backlog)* |

### Operador

| # | Funcionalidade |
|---|---|
| RF12 | Cadastrar e gerenciar produtos com preço e controle de estoque |
| RF13 | Criar cardápios com período de vigência e preços customizados por produto |
| RF14 | Visualizar pedidos do dia e histórico completo |
| RF15 | Atualizar status do pedido — máquina de estados validada: `PENDENTE → EM_PREPARACAO → SAIU_PARA_ENTREGA → ENTREGUE \| CANCELADO` |
| RF16 | Consultar ranking dos produtos mais vendidos |
| RF17 | Criar e gerenciar cupons com regras de recorrência, validade e limite de uso |
| RF18 | Visualizar avaliações por status (pendentes, disponíveis, concluídas) |

---

## Requisitos Não Funcionais

| Categoria | Decisão | Implementação |
|---|---|---|
| **Segurança** | JWT HS256 | `JwtProvider` — CPF como subject, role no payload, expiração configurável via `application.properties` |
| **Autorização** | 5 níveis de acesso | `USER`, `CLIENT`, `ADMIN1`, `ADMIN2`, `ADMIN3` — mapeados por rota em `SecurityConfigurations` |
| **Consistência** | Outbox Pattern | Evento e dado persistidos na mesma transação; Scheduler reprocessa falhas com até 3 tentativas |
| **Desacoplamento** | Eventos de domínio | Módulos se comunicam via Spring Events — zero import direto entre domínios |
| **Tempo real** | WebSocket STOMP | Endpoint `/ws` com SockJS fallback, broker em `/topico` |
| **Schema** | Flyway migrations | 5 migrations versionadas — schema nunca gerado pelo Hibernate em produção (`ddl-auto=validate`) |
| **Rastreabilidade** | Histórico de pontuação | `RegistroPontuacao` captura snapshot do saldo no momento exato de cada crédito |
| **Imutabilidade** | Value Objects como `record` | `PontuacaoAtual`, `RegistroPontuacao`, `NotaAvaliacao`, `ComentarioAvaliacao` — estado inválido impossível por construção |

---

## Como Rodar

### Opção 1 — H2 in-memory (sem instalação)

```bash
git clone https://github.com/brunofdev/sistema-restaurante-api.git
cd sistema-restaurante-api
./mvnw spring-boot:run
```

A aplicação sobe com H2 e executa o `DatabaseSeeder` automaticamente — banco populado com 2 clientes, 10 produtos, 2 cardápios, 22 pedidos, 8 avaliações e histórico de fidelidade pronto para uso.

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| H2 Console | http://localhost:8080/h2-console |
| Painel de Testes | http://localhost:8080/index.html |

**Credenciais H2:** JDBC URL `jdbc:h2:mem:restaurantedb` · usuário `sa` · senha em branco.

**Credenciais de teste pré-populadas:**

| Perfil | CPF | Senha |
|---|---|---|
| Cliente — Maria | `00000000000` | `123456` |
| Cliente — João | `00000000353` | `123456` |
| Operador | `00000000000` | `123456` |

---

### Opção 2 — PostgreSQL com Docker

```bash
# Sobe o banco
docker run --name restaurante-db \
  -e POSTGRES_DB=restaurantedb \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:16

# Executa a aplicação apontando para o PostgreSQL
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/restaurantedb \
SPRING_DATASOURCE_USERNAME=postgres \
SPRING_DATASOURCE_PASSWORD=postgres \
JWT_SECRET_KEY=SuaChaveBase64SuperSeguraAqui123456789== \
./mvnw spring-boot:run
```

O Flyway executa as migrations automaticamente na primeira inicialização.

---

## Tecnologias

| Tecnologia | Versão | Por que foi escolhida |
|---|---|---|
| **Java 17** | 17 | LTS ativo — `record` para Value Objects imutáveis sem boilerplate |
| **Spring Boot** | 3.4.5 | Autoconfiguration onde cabe, explícito onde importa — sem abrir mão de controle |
| **Spring Security** | 6.4.6 | Filtro JWT customizado integrado ao `SecurityFilterChain` sem XML nem mágica |
| **JJWT** | 0.12.5 | API fluente para geração e validação de tokens HS256 |
| **Spring Data JPA** | — | `@Embeddable` para Value Objects, `@ElementCollection` para histórico de pontuação |
| **Flyway** | — | Migrations versionadas — schema nunca gerado em runtime; histórico rastreável |
| **PostgreSQL** | 16 | Banco principal em produção — constraints complexas e queries analíticas |
| **H2** | — | In-memory para desenvolvimento — zero configuração, reset automático ao reiniciar |
| **WebSocket + STOMP** | — | Notificações em tempo real sem polling |
| **SpringDoc OpenAPI** | 2.7.0 | Swagger UI gerado a partir das anotações — documentação sempre sincronizada |
| **Lombok** | — | Elimina boilerplate em classes de infraestrutura sem tocar no domínio |
| **Instancio** | 5.0.2 | Geração de objetos aleatórios para testes — reduz fixture manual sem perder cobertura |
| **Mockito** | 5.11.0 | Mock de dependências nos testes de casos de uso — isolamento total do domínio |

---

## Estrutura de Pacotes

```
src/main/java/com/restaurante01/api_restaurante/
├── compartilhado/
│   ├── dominio/
│   │   ├── entidade/     OutboxEvento
│   │   ├── enums/        GatilhoEvento, TipoEvento, Agregado, StatusOutbox
│   │   └── repositorio/  OutboxRepositorio
│   └── infraestrutura/
│       └── scheduler/    OutboxDespachante (reprocessamento a cada 60s)
│
├── infraestrutura/
│   ├── security/         JwtProvider, JwtAuthenticationFilter, SecurityConfigurations
│   └── configs/          WebSocketConfig, SwaggerConfig
│
└── modulos/
    ├── avaliacao/        Aggregate Avaliacao — ciclo de vida com Schedulers
    ├── cardapio/         Cardapio + Associacao produto-cardápio com preço customizado
    ├── cupom/            Cupom com regras de recorrência, validade e limite de uso
    ├── fidelidade/       Aggregate Fidelidade + CalculadoraDeFidelidade
    ├── pedido/           Aggregate Pedido — máquina de estados de status
    ├── produto/          Produto com controle de estoque
    └── usuario/
        ├── cliente/      Cliente + FidelidadeReferenciaId
        └── operador/     Operador com níveis de permissão
```

---

<div align="center">

Projeto desenvolvido por **Bruno Fraga** como estudo aplicado de arquitetura de software com Spring Boot.

</div>
