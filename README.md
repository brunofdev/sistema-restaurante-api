# 🍽️ Sistema Restaurante — API REST

> API REST completa para gestão de restaurantes, desenvolvida com **Java 17** e **Spring Boot**.  
> Projeto educacional com foco em boas práticas de engenharia de software, arquitetura limpa e segurança.

---

##  Sobre o Projeto

Este projeto simula o backend completo de um sistema de restaurante, cobrindo desde a vitrine pública de cardápios até o painel operacional da cozinha. Foi construído com atenção especial a princípios de design como SOLID, coesão de classes, separação entre domínio e infraestrutura, e segurança por níveis de acesso.

---

##  Funcionalidades

### Salão — Área do Cliente

**Acesso público (sem login)**
- Visualização de cardápios ativos e produtos com preços

**Área do cliente autenticado (`ROLE_USER`)**
- Login exclusivo via CPF com geração de token JWT
- Abertura de pedidos com seleção de itens
- Comanda digital — histórico pessoal de pedidos com status em tempo real

---

### Cozinha e Escritório — Área Operacional

**Gestão de pedidos (`ADMIN1`, `ADMIN2`, `ADMIN3`)**
- Painel do dia com filtro automático por data
- Controle do fluxo de status: `PENDENTE → EM_PREPARAÇÃO → SAIU_PARA_ENTREGA → ENTREGUE | CANCELADO (SE ENTREGUE, NÃO CANCELA MAIS, SE CANCELADO, NÃO PODE MAIS SER REABERTO)`
- Histórico completo de vendas para auditoria
- Saldo de produtos baixam automaticamente pós pedido feito
- Saldo de produtos retornam automaticamente quando um StatusPedido = CANCELADO
  

**Engenharia de cardápio**
- Criação e gerenciamento de cardápios temáticos (ex: Cardápio de Verão, Promoção de Terça)
- Associação de produtos a cardápios específicos
- Customização de preço, quantidade e descrição por cardápio sem alterar o cadastro original do produto

**Administração de usuários**
- Controle de acesso por níveis hierárquicos de operadores
- Gerenciamento da base de clientes
- Pontuação de fidelidade para clientes conforme valor total de pedidos realizados com status ENTREGUE.

---

### 🏗️ Infraestrutura

| Recurso | Descrição |
|---|---|
| 🔐 Segurança por papéis | Clientes não alteram status de pedidos; operadores não criam pedidos como clientes |
| ⚡ WebSocket | Painel da gerência recebe alertas em tempo real ao chegar novos pedidos |
| 🌱 Database Seeder | Sistema inicializa com dados de teste prontos — clientes, produtos e pedidos |
| 📖 Swagger / OpenAPI | Documentação interativa de todas as rotas em `/swagger-ui.html` |
| 🛡️ Tratamento de erros | Respostas padronizadas com mensagens claras em vez de stack traces |

---

## 🗺️ Rotas Principais

<img width="861" height="1091" alt="image" src="https://github.com/user-attachments/assets/505a863e-1c64-44f1-8d64-e65211469705" />


---

## ⚙️ Tecnologias

- **Java 17** · **Spring Boot** · **Spring Security** · **Spring Data JPA**
- **Hibernate** · **H2** (testes) · **PostgreSQL** (produção)
- **JWT** · **WebSocket (STOMP)**
- **Swagger / OpenAPI**
- **JUnit 5** · **Mockito**
- **Docker** · **Maven**

---

## 🏛️ Arquitetura

O projeto segue uma **arquitetura modular por funcionalidade**, onde cada módulo possui suas próprias camadas internas:

```
módulo/
├── api/              → controllers e DTOs
├── aplicacao/        → use cases, mappers e validadores
├── dominio/          → entidades, repositórios (interfaces) e exceções
└── infraestrutura/   → adapters e persistência JPA
```

Princípios aplicados:
- **SOLID** — especialmente SRP e DIP, com separação clara entre domínio e infraestrutura
- **Programação para interfaces** — serviços dependem de contratos, não de implementações concretas
- **Padrão Repository + Adapter** — o domínio não conhece JPA; a infraestrutura não conhece as regras de negócio
- **Enums com comportamento** — regras de transição de status encapsuladas no próprio enum `StatusPedido`
- **Padrão observer** — modulos que se comunicam através de eventos 

---

## 🚀 Como Rodar

```bash
# 1. Clone o repositório
git clone https://github.com/brunofdev/sistema-restaurante-api

# 2. Entre na pasta
cd sistema-restaurante-api

# 3. Sincronize as dependências Maven e rode
./mvnw spring-boot:run
```

> A API sobe com banco **H2 em memória** e dados de teste já carregados automaticamente.  
> Não é necessário configurar nenhum banco de dados externo para testar.

Acesse a documentação interativa em:
```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Testando o WebSocket

Na raiz do projeto existe um arquivo `TestadorWebSocket.html` — abra-o no navegador para simular alertas em tempo real ao criar pedidos.

---

## 📐 Diagramas

- `Modelagem do Banco Sistema.drawio` — modelagem do banco de dados
- `DIAGRAMA UML - ESTRUTURA CARDAPIO, PRODUTO E CARDAPIOPRODUTO.pdf` — relacionamento entre cardápios e produtos
- `UML-PLANT-API.puml` — diagrama de classes no formato PlantUML

---

## 📬 Contato

Desenvolvido por **Bruno Fraga** · [github.com/brunofdev](https://github.com/brunofdev)
