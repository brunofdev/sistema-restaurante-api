## 🍽️ API Restaurante — Sistema Completo de Gestão

> Backend production-ready para restaurantes com arquitetura hexagonal, eventos de domínio e consistência eventual garantida via Outbox Pattern.

**Stack:** `Java 17` · `Spring Boot 3.4.5` · `PostgreSQL` · `JWT` · `WebSocket/STOMP` · `Spring Events` · `JPA/Hibernate`

---

## Arquitetura

Projeto organizado em **Hexagonal Architecture (Ports & Adapters) + DDD**, com 9 módulos de negócio independentes (`pedido`, `produto`, `cardapio`, `cardapioproduto`, `cupom`, `cliente`, `operador`). Cada módulo segue a estrutura: `domínio → aplicação → api → infraestrutura`, garantindo total separação de responsabilidades e testabilidade.

---

## O que o sistema faz

### Para o Cliente
- Cadastro e autenticação via **JWT** com CPF + senha
- Consulta de produtos e cardápios ativos sem login (endpoint público)
- Criação de pedidos com múltiplos itens, observações por item e endereço alternativo de entrega
- Aplicação de cupons de desconto com validação completa de elegibilidade
- Acompanhamento do pedido em **tempo real via WebSocket (STOMP)**
- Acúmulo de pontos de fidelidade com **3 faixas por valor do pedido** (Bronze / Prata / Ouro)
- Histórico de pedidos com paginação

### Para a Operação (3 níveis de acesso: ADMIN1 / ADMIN2 / ADMIN3)
- Controle do fluxo completo via **máquina de estados**:
  `PENDENTE → EM_PREPARACAO → SAIU_PARA_ENTREGA → ENTREGUE` (cancelamento em qualquer etapa anterior à entrega)
- Notificações de novos pedidos em tempo real no painel administrativo
- Gestão de produtos: CRUD, controle de estoque, alerta de baixa quantidade
- Gestão de cardápios com **vigência por data** (início/fim), podendo customizar preço, quantidade e descrição **por produto/cardápio**
- Criação de cupons com desconto por **porcentagem ou valor fixo**, limites de uso, faixas de valor mínimo/máximo do pedido e regras de recorrência (reuso a cada 10/15/20/30 dias)
- Relatório de **top produtos mais vendidos** por período
- Gestão de operadores (ADMIN2+) e clientes (ADMIN3)

---

## Regras de Negócio

| Regra | Descrição |
|---|---|
| **Estoque duplo** | Rastreamento no catálogo global do produto e na associação por cardápio simultaneamente |
| **Estoque automático** | Debitado ao criar pedido; estornado automaticamente no cancelamento |
| **Cupom inteligente** | Consumido no pedido; devolvido se cancelado; bloqueado por cooldown de recorrência por cliente |
| **Fidelidade honesta** | Pontos creditados **apenas após status ENTREGUE** — nunca no momento do pedido |
| **Cardápio ativo** | Produto esgotado torna-se indisponível automaticamente; cardápio respeita janela de datas |
| **Transições protegidas** | Máquina de estados no domínio bloqueia transições inválidas em nível de entidade |
| **Rastreabilidade** | Todas as entidades auditáveis: `createdAt`, `createdBy`, `modifiedAt`, `modifiedBy` |

---

## Consistência Eventual — Outbox Pattern

Operações críticas entre domínios (estoque, cupom, pontuação) são coordenadas via **Outbox Pattern** sobre Spring Events:

```
Pedido criado (transação) → evento salvo em outbox_eventos
      ↓ AFTER_COMMIT (nova transação isolada)
  ├── BaixarEstoqueOuvinte    → debita produto + associação
  ├── ConsumirCupomOuvinte    → decrementa uso do cupom
  └── ComputarPontuacaoOuvinte → acredita pontos ao cliente

Pedido cancelado → eventos de estorno publicados com mesma garantia
OutboxDespachante (scheduled) → reprocessa falhas até 3 tentativas
```

Garante que **nenhum evento crítico é perdido**, mesmo em falhas de rede ou exceções em handlers.

---

## Segurança

- Autenticação por **JWT** com claims de CPF e role
- **3 perfis de operador** com endpoints protegidos via `@PreAuthorize`
- Endpoints públicos restritos ao mínimo necessário
- Senhas armazenadas com hash via `PasswordEncoder`
- WebSocket protegido por token no frame CONNECT

---

## Qualidade

- **24+ classes de teste** cobrindo entidades de domínio e casos de uso
- Exceções de domínio tipadas + `GlobalExceptionHandler` com respostas padronizadas
- Documentação completa via **OpenAPI 3.0 / Swagger UI**
- Injeção por construtor em toda a aplicação (sem field injection)
