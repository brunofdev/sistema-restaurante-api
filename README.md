<div align="center">

# 🍽️ Sistema Restaurante — API REST

**Backend completo para gestão de restaurantes, desenvolvido com Java 17 e Spring Boot.**  
Projeto educacional com foco em boas práticas de engenharia de software, arquitetura limpa e segurança.

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-6DB33F?style=flat-square&logo=springsecurity)](https://spring.io/projects/spring-security)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=flat-square&logo=apachemaven)](https://maven.apache.org)
[![H2](https://img.shields.io/badge/H2-In--Memory-blue?style=flat-square)](https://h2database.com)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Production-336791?style=flat-square&logo=postgresql)](https://www.postgresql.org)

</div>

---

## 📖 Sobre o Projeto

Este projeto simula o backend completo de um sistema de restaurante, cobrindo desde a vitrine pública de cardápios até o painel operacional da cozinha. Foi construído com atenção especial a princípios de design como **SOLID**, coesão de classes, separação entre domínio e infraestrutura, e segurança por níveis de acesso.

---

##  Funcionalidades

###  Área do Cliente

**Acesso público (sem login)**
- Visualização de cardápios ativos e produtos com preços

**Cliente autenticado (`ROLE_USER`)**
- Login exclusivo via **CPF** com geração de token **JWT**
- Abertura de pedidos com seleção de itens e observações por item
- Endereço alternativo no pedido ou utilização do endereço cadastrado
- Suporte a **cupons de desconto**
- **Sistema de pontuação de fidelidade** baseado no valor total do pedido
- Histórico pessoal de pedidos com status em tempo real
- Notificações automáticas a cada atualização de status

---

###  Área Operacional

**Gestão de Pedidos** — `ADMIN1`, `ADMIN2`, `ADMIN3`

- Painel do dia com filtro automático por data
- Controle de fluxo de status:

```
PENDENTE → EM_PREPARAÇÃO → SAIU_PARA_ENTREGA → ENTREGUE
                                              ↘ CANCELADO
```

>  Pedidos **ENTREGUE** não podem ser cancelados. Pedidos **CANCELADO** não podem ser reabertos.

- Histórico completo de vendas para auditoria
- Estoque decrementado automaticamente após um pedido
- Estoque restaurado automaticamente ao cancelar um pedido
- Validação de estoque: um pedido só é concluído se houver quantidade disponível tanto na tabela de Associação quanto na tabela de Produto

**Produtos**
- CRUD completo
- Atualização em lote
- Quantidade disponível decrementada a cada venda
- Produto marcado como **indisponível** automaticamente ao esgotar o estoque

**Cardápio e Associação**

O sistema possui duas entidades distintas e relacionadas: `Cardapio` e uma **tabela associativa** que vincula produtos a um cardápio. Isso permite criar cardápios dinâmicos, onde os produtos associados podem ter campos customizados ou herdar os campos originais da entidade `Produto`.

- Criação e gerenciamento de cardápios temáticos *(ex: Cardápio de Verão, Promoção de Terça)*
- Cardápios com datas específicas e disponibilidade controlada

**Administração de Usuários**
- Controle de acesso por **níveis hierárquicos** de operadores
- Gerenciamento da base de clientes
- Pontuação de fidelidade acumulada apenas após status `ENTREGUE`, evitando fraudes

---

## 🏗️ Infraestrutura

| Recurso | Descrição |
|---|---|
| 🔐 Segurança por papéis | Clientes não alteram status de pedidos; operadores não criam pedidos como clientes |
| ⚡ WebSocket | Painel da gerência recebe alertas em tempo real ao chegar novos pedidos |
| 🌱 Database Seeder | Sistema inicializa com dados de teste prontos — clientes, produtos e pedidos |
| 📖 Swagger / OpenAPI | Documentação interativa de todas as rotas em `/swagger-ui.html` |
| 🛡️ Tratamento de Erros | Respostas padronizadas com mensagens claras em vez de stack traces |

---

## 🗺️ Rotas Principais

![Rotas da API](https://github.com/user-attachments/assets/505a863e-1c64-44f1-8d64-e65211469705)

---

##  Como Rodar

```bash
# 1. Clone o repositório
git clone https://github.com/brunofdev/sistema-restaurante-api

# 2. Entre na pasta
cd sistema-restaurante-api

# 3. Sincronize as dependências Maven e rode
./mvnw spring-boot:run
```

> A API sobe com **banco H2 em memória** e dados de teste já carregados automaticamente.  
> Não é necessário configurar nenhum banco de dados externo para testar.

Acesse a documentação interativa em:
```
http://localhost:8080/swagger-ui.html
```

---

##  Testando o Projeto

### 1. Autenticação

O sistema já inicia populado com dados de teste em todos os módulos. O primeiro passo é se autenticar no Swagger — o mesmo endpoint serve tanto clientes quanto operadores:

![Como logar no Swagger](https://github.com/user-attachments/assets/ec53396e-ac22-4a6c-881b-3af180348289)

---

### 2. Fazendo um Pedido

O teste mais interessante é realizar um pedido completo:

![Fazendo um pedido](https://github.com/user-attachments/assets/6dc941fa-f12e-4e43-91d8-6d29edcf60bc)

Em caso de sucesso, o sistema retorna um JSON padronizado:

![Resposta de sucesso](https://github.com/user-attachments/assets/632eb0e6-7632-4987-8040-77dcc67941cd)

---

### 3. Atualizando o Status do Pedido

Administradores devem atualizar o status do pedido pelo painel:

![Atualização de status](https://github.com/user-attachments/assets/5ccb9f59-9c13-441e-a9bf-a01efc9ac369)

---

### 4. Eventos do Fluxo de Pedido

O sistema executa uma série de eventos automáticos via **padrão Observer**, onde o domínio principal dispara os eventos e os demais módulos os escutam.

**Pré-processamento (ao criar o pedido):**
- Validação do estoque disponível
- Validação dos produtos selecionados
- Verificação se os produtos pertencem ao cardápio informado
- Consumação do cupom (se inserido)

**Pós-processamento (ao confirmar o pedido):**
- Subtração da quantidade no módulo Produto
- Subtração da quantidade no módulo Associativo

**Ao atingir status `ENTREGUE`:**
- Cálculo e acréscimo de pontos de fidelidade baseado no valor do pedido

**Ao atingir status `CANCELADO`:**
- Estorno das quantidades subtraídas
- Estorno do cupom utilizado

---

##  Tecnologias

| Categoria | Tecnologias |
|---|---|
| Linguagem & Framework | Java 17, Spring Boot, Spring Security, Spring Data JPA |
| Persistência | Hibernate, H2 (testes), PostgreSQL (produção) |
| Segurança | JWT |
| Comunicação em tempo real | WebSocket (STOMP) |
| Documentação | Swagger / OpenAPI |
| Testes | JUnit 5, Mockito |
| DevOps | Docker, Maven |

---

##  Arquitetura

O projeto segue uma **arquitetura modular por funcionalidade**, onde cada módulo possui suas próprias camadas internas:

```
módulo/
├── api/              → controllers e DTOs
├── aplicacao/        → use cases, mappers e validadores
├── dominio/          → entidades, repositórios (interfaces) e exceções
└── infraestrutura/   → adapters e persistência JPA
```

**Princípios aplicados:**

- **SOLID** — especialmente SRP e DIP, com separação clara entre domínio e infraestrutura
- **Programação para interfaces** — serviços dependem de contratos, não de implementações concretas
- **Padrão Repository + Adapter** — o domínio não conhece JPA; a infraestrutura não conhece as regras de negócio
- **Enums com comportamento** — regras de transição de status encapsuladas no próprio enum `StatusPedido`
- **Padrão Observer** — módulos que se comunicam através de eventos desacoplados

---

## 🧪 Testando o WebSocket

Na raiz do projeto existe um arquivo `TestadorWebSocket.html` — abra-o no navegador para simular alertas em tempo real ao criar pedidos.

---

## 📐 Diagramas

| Arquivo | Descrição |
|---|---|
| `Modelagem do Banco Sistema.drawio` | Modelagem do banco de dados |
| `DIAGRAMA UML - ESTRUTURA CARDAPIO, PRODUTO E CARDAPIOPRODUTO.pdf` | Relacionamento entre cardápios e produtos |
| `UML-PLANT-API.puml` | Diagrama de classes no formato PlantUML |

---

## 📬 Contato

Desenvolvido por **Bruno Fraga** · [github.com/brunofdev](https://github.com/brunofdev)
