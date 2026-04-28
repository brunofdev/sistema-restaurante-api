<div align="center">

# 🍽️ Sistema Restaurante — API REST

**Backend completo para gestão de restaurantes, desenvolvido com Java 17 e Spring Boot.**  
Projeto com foco em arquitetura hexagonal, design orientado a domínio e boas práticas de engenharia de software.

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-6DB33F?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-6.4-6DB33F?style=flat-square&logo=springsecurity)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-jjwt_0.12.5-black?style=flat-square&logo=jsonwebtokens)](https://github.com/jwtk/jjwt)
[![WebSocket](https://img.shields.io/badge/WebSocket-STOMP-blue?style=flat-square)](https://stomp.github.io)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_2.7-85EA2D?style=flat-square&logo=swagger)](https://swagger.io)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker)](https://www.docker.com)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Produção-336791?style=flat-square&logo=postgresql)](https://www.postgresql.org)
[![H2](https://img.shields.io/badge/H2-Testes-blue?style=flat-square)](https://h2database.com)

</div>

---

## 📖 Sobre o Projeto

Este projeto simula o **backend completo de um sistema de restaurante**, cobrindo desde a vitrine pública de cardápios até o painel operacional da cozinha com alertas em tempo real.

O objetivo não foi só fazer funcionar — foi tomar **decisões conscientes de design** a cada etapa: onde cada regra deve viver, como isolar o domínio da infraestrutura, como garantir que o sistema possa crescer sem virar um emaranhado de `if/else`. As seções abaixo documentam essas decisões.

---

## 🏗️ Arquitetura e Decisões de Design

### Estrutura Modular por Domínio

O projeto é organizado por módulos de negócio, cada um com suas próprias camadas internas. O domínio não conhece JPA, Spring, nem nenhum detalhe de infraestrutura — ele expõe interfaces (portas) que a infraestrutura implementa (adaptadores).

```
módulo/
├── api/              → controllers e DTOs
├── aplicacao/        → casos de uso e mapeadores
├── dominio/          → entidades, value objects, interfaces de repositório e exceções
└── infraestrutura/   → adaptadores JPA e implementações das portas
```

Módulos implementados: `auth`, `cardapio`, `cardapioproduto`, `cliente`, `cupom`, `operador`, `pedido`, `produto`, `websocket`.

---

### Enums com Comportamento — Strategy Pattern sem cerimônia

Um dos padrões mais utilizados no projeto é encapsular comportamento diretamente nos enums, eliminando `switch/case` espalhados e respeitando o Open/Closed Principle.

**Exemplo 1 — Tipos de desconto de cupom:**

```java
public enum TipoDesconto {
    PORCENTAGEM {
        @Override
        public BigDecimal aplicar(BigDecimal bruto, BigDecimal desconto) {
            return bruto.multiply(desconto).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        }
    },
    VALOR {
        @Override
        public BigDecimal aplicar(BigDecimal bruto, BigDecimal desconto) {
            return desconto;
        }
    };

    public abstract BigDecimal aplicar(BigDecimal bruto, BigDecimal desconto);
}
```

Adicionar um novo tipo de desconto (`FRETE_GRATIS`, por exemplo) não exige alterar nenhuma outra classe — apenas um novo valor no enum com sua própria implementação.

**Exemplo 2 — Regras de recorrência de cupom:**

```java
public enum RegraRecorrencia {
    DEZ_DIAS(10), QUINZE_DIAS(15), VINTE_DIAS(20), TRINTA_DIAS(30);

    private final int dias;

    RegraRecorrencia(int dias) { this.dias = dias; }

    public void aplicar(LocalDateTime dataUltimoUso) {
        if (!dataUltimoUso.isBefore(LocalDateTime.now().minusDays(dias))) {
            throw new CupomNaoPodeSerConsumidoExcecao(
                "Este cupom só pode ser reutilizado de " + dias + " em " + dias + " dias. Último uso: " + dataUltimoUso);
        }
    }
}
```

Cada valor do enum carrega seus próprios dados e comportamento. A lógica de validação não vaza para os casos de uso.

**Exemplo 3 — Transição de status de pedido:**

```java
// O enum StatusPedido sabe quais transições são válidas
PENDENTE → EM_PREPARACAO → SAIU_PARA_ENTREGA → ENTREGUE
                                              ↘ CANCELADO
```

> ⚠️ Pedidos `ENTREGUE` não podem ser cancelados. Pedidos `CANCELADO` não podem ser reabertos. Essas regras vivem no próprio enum, não nos controllers nem nos casos de uso.

---

### Separação de Contextos — Cupom não conhece Cliente

Ao implementar a validação de recorrência de cupom por cliente, uma decisão importante foi tomada: **o módulo de Cupom não deve conhecer o módulo de Cliente**. Criar esse acoplamento quebraria a independência entre os domínios.

A solução foi usar o que o módulo de Pedido já possui naturalmente — ele registra qual cliente fez qual pedido com qual cupom. A query de verificação vive no repositório de Pedido:

```java
@Query("""
    SELECT p.dataCriacao FROM Pedido p
    WHERE p.cliente.clienteId = :clienteId
    AND p.cupom.codigoCupom = :codigoCupom
    ORDER BY p.dataCriacao DESC
    LIMIT 1
""")
Optional<LocalDateTime> buscarDataUltimoUsoDoCupomPeloCliente(
    @Param("clienteId") Long clienteId,
    @Param("codigoCupom") String codigoCupom);
```

O caso de uso de pedido passa essa informação ao validar o cupom, e a regra de intervalo fica encapsulada no `RegraRecorrencia`.

---

### Padrão Observer — Módulos Desacoplados por Eventos

Ao criar ou atualizar um pedido, diversas ações precisam acontecer em outros módulos: subtrair estoque, acumular pontos de fidelidade, estornar cupom em caso de cancelamento. Chamar esses módulos diretamente do caso de uso criaria acoplamento desnecessário.

A solução foi o **padrão Observer via `ApplicationEventPublisher`** do Spring:

| Evento | Ações disparadas automaticamente |
|---|---|
| Pedido criado | Valida estoque, valida produtos, consome cupom |
| Status → `ENTREGUE` | Calcula e acrescenta pontos de fidelidade |
| Status → `CANCELADO` | Estorna estoque e estorna cupom |

O caso de uso de pedido apenas publica o evento — ele não sabe quem vai reagir.

---

## ⚙️ Funcionalidades

### 🔓 Acesso Público

- Visualização de cardápios ativos com produtos e preços
- Cadastro de clientes e operadores

### 🔐 Autenticação

- Login de clientes via CPF ou username com geração de token JWT
- Login de operadores via username com geração de token JWT
- Todos os tokens carregam as roles do usuário para controle de acesso hierárquico

### 👤 Área do Cliente (`ROLE_USER`)

- Criar pedido com múltiplos itens, observações individuais, endereço alternativo e cupom de desconto
- Acompanhar status do pedido **em tempo real** via WebSocket
- Consultar histórico de pedidos com paginação
- Gerenciar perfil e endereços
- Consultar saldo de pontos de fidelidade

### ⚙️ Área Operacional (`ADMIN1`, `ADMIN2`, `ADMIN3`)

- Receber **alertas em tempo real** quando novos pedidos chegam
- Gerenciar fluxo de status dos pedidos
- CRUD completo de produtos, cardápios, associações, cupons, clientes e operadores
- Controle de estoque com subtração automática por venda

---

## 📡 WebSocket — Tempo Real

O sistema usa WebSocket com protocolo **STOMP** (via SockJS):

| Tópico | Descrição | Consumidor |
|---|---|---|
| `/topico/admin-pedidos` | Novo pedido chegou | Painel da cozinha |
| `/topico/pedido/{id}` | Status do pedido atualizado | App do cliente |

O arquivo `TestadorWebSocket.html` na raiz simula o sistema completo em dois painéis simultâneos no navegador.

---

## 🛡️ Segurança

- Autenticação **stateless** via JWT em todas as rotas protegidas
- Segregação estrita de papéis: clientes não alteram status de pedidos, operadores não criam pedidos como clientes
- Bean Validation em todos os DTOs com mensagens de erro descritivas
- Respostas de erro com estrutura uniforme — sem stack traces expostos
- Vulnerabilidades de dependências transitivas corrigidas via pinning de versão no `pom.xml`

---

## 🚀 Como Rodar

### Opção 1 — H2 em memória (recomendado para explorar)

```bash
git clone https://github.com/brunofdev/sistema-restaurante-api
cd sistema-restaurante-api
./mvnw spring-boot:run
```

A API sobe com banco H2 em memória e dados de teste carregados automaticamente. Nenhuma configuração externa necessária.

### Opção 2 — Docker Compose com PostgreSQL

```bash
docker-compose up --build
```

Acesse a documentação interativa em `http://localhost:8080/swagger-ui.html`

---

## 🧪 Testando

O sistema já inicia com dados de teste em todos os módulos. O fluxo mais completo para testar:

1. Autentique-se como cliente no Swagger
2. Crie um pedido com itens e cupom
3. Autentique-se como operador e avance o status
4. Observe os eventos sendo disparados (estoque, pontos, cupom)
5. Abra o `TestadorWebSocket.html` para ver as notificações em tempo real

---

## 🗺️ Rotas Principais

![Rotas da API](https://github.com/user-attachments/assets/505a863e-1c64-44f1-8d64-e65211469705)

---

## 📐 Diagramas

| Arquivo | Descrição |
|---|---|
| `Modelagem do Banco Sistema.drawio` | Modelagem completa do banco de dados |
| `DIAGRAMA UML - ESTRUTURA CARDAPIO, PRODUTO E CARDAPIOPRODUTO.pdf` | Relacionamento entre cardápios, produtos e a tabela associativa |
| `UML-PLANT-API.puml` | Diagrama de classes completo no formato PlantUML |

---

## ⚙️ Tecnologias

| Categoria | Tecnologias |
|---|---|
| Linguagem & Framework | Java 17, Spring Boot 3.4.5, Spring Security 6.4, Spring Data JPA |
| Persistência | Hibernate, H2 (dev/testes), PostgreSQL (produção) |
| Segurança | JWT — jjwt 0.12.5 |
| Tempo real | WebSocket, STOMP, SockJS |
| Documentação | Swagger / SpringDoc OpenAPI 2.7 |
| Testes | JUnit 5, Mockito, Instancio |
| Build & DevOps | Maven, Docker, Docker Compose |
| Utilitários | Lombok, Bean Validation |

---

## 📬 Contato

Desenvolvido por **Bruno Fraga** · [github.com/brunofdev](https://github.com/brunofdev) · [linkedin.com/in/bruno-fraga-dev](https://linkedin.com/in/bruno-fraga-dev)
