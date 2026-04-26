<div align="center">

# 🍽️ Sistema Restaurante — API REST

**Backend completo para gestão de restaurantes, desenvolvido com Java 17 e Spring Boot.**  
Projeto educacional com foco em boas práticas de engenharia de software, arquitetura limpa e segurança.

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

Este projeto simula o **backend completo de um sistema de restaurante**, cobrindo desde a vitrine pública de cardápios até o painel operacional da cozinha com alertas em tempo real. Foi construído com atenção especial a princípios de design como **SOLID**, coesão de classes, separação entre domínio e infraestrutura, e segurança por níveis de acesso.

---

##  Funcionalidades Completas

### 🔓 Acesso Público (sem autenticação)

- Visualização de **cardápios ativos** com seus produtos e preços
- Acesso aos **produtos disponíveis** por cardápio
- Cadastro de **novos clientes** (`POST /cliente/cadastro`)
- Cadastro de **novos operadores** (`POST /operador/cadastro`)

---

### 🔐 Autenticação

- Login de **clientes** via CPF ou username com geração de token **JWT** (`POST /api/auth/cliente-login`)
- Login de **operadores** via username com geração de token **JWT** (`POST /api/auth/operador-login`)
- Todos os tokens carregam as roles do usuário para controle de acesso por nível hierárquico

---

### 👤 Área do Cliente (`ROLE_USER`)

**Pedidos**
- Criar pedido com múltiplos itens, cada item com quantidade e observação individual (`POST /pedido/criar-pedido`)
- Adicionar **endereço alternativo de entrega** no pedido, ou utilizar o endereço cadastrado
- Aplicar **cupom de desconto** no pedido
- Consultar histórico completo de pedidos com paginação (`GET /pedido/obter-pedidos-do-cliente`)
- Acompanhar status dos pedidos **em tempo real** via WebSocket — push automático em `/topico/pedido/{id}` a cada mudança de status

**Perfil**
- Visualizar e atualizar dados do próprio cadastro
- Gerenciar endereços cadastrados

**Fidelidade**
- Consultar saldo atual de **pontos de fidelidade**
- Pontos acumulados automaticamente com base no valor total de pedidos com status `ENTREGUE`

---

### ⚙️ Área Operacional — Gestão de Pedidos

**Níveis de acesso:** `ADMIN1`, `ADMIN2`, `ADMIN3`

**Painel do Dia**
- Listar todos os pedidos do dia corrente com paginação (`GET /pedido/obter-pedidos-do-dia`)
- Receber **alertas em tempo real** via WebSocket (`/topico/admin-pedidos`) quando novos pedidos chegam
- Painel com ordenação automática por prioridade de status

**Fluxo de Status dos Pedidos**

```
PENDENTE → EM_PREPARAÇÃO → SAIU_PARA_ENTREGA → ENTREGUE
                                              ↘ CANCELADO
```

> ⚠️ Pedidos com status `ENTREGUE` **não podem ser cancelados**.  
> ⚠️ Pedidos com status `CANCELADO` **não podem ser reabertos**.  
> As regras de transição ficam encapsuladas diretamente no enum `StatusPedido`.

- Atualizar status de um pedido (`PATCH /pedido/{id}/status`)
- Histórico completo de todos os pedidos para auditoria

**Automações disparadas pelo fluxo de status (via Padrão Observer):**

| Evento | Ações automáticas |
|---|---|
| Pedido criado | Valida estoque, valida produtos, verifica associação ao cardápio, consome cupom |
| Pedido confirmado | Subtrai quantidade em `Produto` e em `CardapioProduto` |
| Status → `ENTREGUE` | Calcula e acrescenta pontos de fidelidade ao cliente |
| Status → `CANCELADO` | Estorna quantidades em estoque, estorna cupom utilizado |

---

### 📦 Módulo de Produtos (`ADMIN`)

- Listar todos os produtos com paginação e filtros
- Buscar produto por ID
- **Criar produto** com nome, descrição, preço, disponibilidade e quantidade em estoque (`POST /produto`)
- **Atualizar produto** individualmente (`PUT /produto/{id}`)
- **Atualização em lote** de múltiplos produtos de uma só vez (`PUT /produto/lote`)
- **Deletar produto** (`DELETE /produto/{id}`)
- Quantidade disponível é **subtraída automaticamente** a cada venda
- Produto marcado como **indisponível automaticamente** ao atingir zero unidades

---

### 🗓️ Módulo de Cardápios (`ADMIN`)

- Listar todos os cardápios (ativos e inativos) com paginação
- Listar apenas cardápios **ativos** (rota pública)
- Buscar cardápio por ID
- **Criar cardápio** com nome, descrição, datas de início e fim, e flag de disponibilidade (`POST /cardapio`)
- **Atualizar cardápio** (`PUT /cardapio/{id}`)
- **Deletar cardápio** (`DELETE /cardapio/{id}`)
- Suporte a cardápios temáticos com **datas específicas de vigência** *(ex: Cardápio de Verão, Promoção de Terça)*
- Múltiplos cardápios podem coexistir, com controle de disponibilidade independente

---

### 🔗 Módulo de Associação Cardápio-Produto (`CardapioProduto`)

O sistema utiliza uma **tabela associativa** distinta da entidade `Produto`, o que permite criar cardápios dinâmicos com campos customizados por associação.

- Associar um produto a um cardápio (`POST /cardapio-produto`)
- Listar todas as associações de um cardápio específico
- Buscar associação por ID
- **Campos customizáveis por associação:** preço promocional e quantidade disponível no contexto do cardápio (independente do estoque global do produto)
- Atualizar dados de uma associação (`PUT /cardapio-produto/{id}`)
- Remover associação (`DELETE /cardapio-produto/{id}`)
- Validação: ao criar um pedido, o sistema verifica se os produtos selecionados **realmente pertencem ao cardápio informado**

---

### 🎟️ Módulo de Cupons (`ADMIN`)

- Criar cupons de desconto com código, valor/percentual e data de validade
- Listar cupons disponíveis
- Atualizar e remover cupons
- Cupom é **consumido automaticamente** ao ser aplicado a um pedido
- Cupom é **estornado automaticamente** se o pedido for cancelado

---

### 👥 Módulo de Clientes (`ADMIN`)

- Listar todos os clientes com paginação
- Buscar cliente por ID ou CPF
- Atualizar dados cadastrais de um cliente
- Remover cliente
- Visualizar **saldo de pontos de fidelidade** de qualquer cliente
- Consultar histórico de pedidos de um cliente específico

---

### 🛠️ Módulo de Operadores (`ADMIN`)

- Listar todos os operadores com paginação
- Buscar operador por ID
- Criar, atualizar e remover operadores
- Controle de **níveis hierárquicos** (`ADMIN1`, `ADMIN2`, `ADMIN3`) com permissões distintas por nível
- Segregação de acesso: operadores não conseguem criar pedidos como clientes

---

### 📡 WebSocket — Tempo Real

O sistema usa WebSocket com protocolo **STOMP** (via SockJS) para comunicação em tempo real:

| Tópico | Descrição | Consumidor |
|---|---|---|
| `/topico/admin-pedidos` | Alerta ao chegar um novo pedido | Painel da cozinha / gerência |
| `/topico/pedido/{id}` | Atualização de status de um pedido específico | App do cliente |

- O painel administrativo é notificado **imediatamente** ao entrar um novo pedido
- O cliente recebe **push automático** cada vez que o operador avança o status do seu pedido
- O arquivo `TestadorWebSocket.html` na raiz do projeto simula todo o fluxo em dois painéis simultâneos no navegador

---

### 🌱 Database Seeder

O sistema inicializa automaticamente com dados de teste em todos os módulos:

- Clientes pré-cadastrados com endereço
- Produtos e cardápios já associados
- Operadores com diferentes níveis de acesso
- Pedidos em vários estados do fluxo

Nenhuma configuração de banco externo é necessária para rodar localmente — o **H2 in-memory** é usado por padrão.

---

### 🛡️ Segurança e Controle de Acesso

- Autenticação **stateless** via JWT em todas as rotas protegidas
- Segregação estrita de papéis: clientes não alteram status de pedidos, operadores não criam pedidos como clientes
- **Bean Validation** em todos os DTOs de entrada com mensagens de erro descritivas
- Respostas de erro com **estrutura uniforme** — sem stack traces expostos ao consumidor
- Dependências transitivas com vulnerabilidades corrigidas via pinning de versão no `pom.xml` (Tomcat, PostgreSQL, AssertJ, Jackson)

---

## 🏗️ Infraestrutura

| Recurso | Descrição |
|---|---|
| 🔐 Segurança por papéis | JWT + Spring Security com roles hierárquicas |
| ⚡ WebSocket STOMP | Alertas e atualizações de status em tempo real |
| 🌱 Database Seeder | Dados de teste carregados na inicialização |
| 📖 Swagger / OpenAPI | Documentação interativa em `/swagger-ui.html` |
| 🛡️ Tratamento de erros | Respostas padronizadas com mensagens claras |
| 🐳 Docker | Dockerfile multi-stage + docker-compose com PostgreSQL |
| 📐 Lombok | Redução de boilerplate em entidades e DTOs |
| 🧪 Instancio | Geração de objetos aleatórios para testes |

---

## 🗺️ Rotas Principais

![Rotas da API](https://github.com/user-attachments/assets/505a863e-1c64-44f1-8d64-e65211469705)

---

## ⚙️ Tecnologias

| Categoria | Tecnologias |
|---|---|
| Linguagem & Framework | Java 17, Spring Boot 3.4.5, Spring Security 6.4, Spring Data JPA |
| Persistência | Hibernate, H2 (testes/dev), PostgreSQL (produção) |
| Segurança | JWT — jjwt 0.12.5, Spring Security |
| Comunicação em tempo real | WebSocket, STOMP, SockJS |
| Documentação | Swagger / SpringDoc OpenAPI 2.7 |
| Testes | JUnit 5, Mockito, Spring Security Test, Instancio |
| Build & DevOps | Maven, Docker, Docker Compose |
| Utilitários | Lombok, Bean Validation |

---

## 🏛️ Arquitetura

O projeto segue uma **arquitetura modular por funcionalidade**, onde cada módulo possui suas próprias camadas internas:

```
módulo/
├── api/              → controllers e DTOs
├── aplicacao/        → use cases, mappers, factories e validadores
├── dominio/          → entidades, repositórios (interfaces) e exceções
└── infraestrutura/   → adapters e persistência JPA
```

**Módulos implementados:**

```
src/
├── auth/
├── cardapio/
├── cardapioproduto/
├── cliente/
├── cupom/
├── operador/
├── pedido/
├── produto/
└── websocket/
```

**Princípios aplicados:**

- **SOLID** — especialmente SRP e DIP, com separação clara entre domínio e infraestrutura
- **Programação para interfaces** — serviços dependem de contratos, não de implementações concretas
- **Padrão Repository + Adapter** — o domínio não conhece JPA; a infraestrutura não conhece as regras de negócio
- **Enums com comportamento** — regras de transição de status encapsuladas no enum `StatusPedido`
- **Padrão Observer** — módulos desacoplados que reagem a eventos do domínio (estoque, pontos, cupom)
- **Padrão Factory + Mapper** — criação e conversão de entidades encapsuladas em classes dedicadas
- **Padrão Validator** — validações de negócio separadas das validações de entrada (Bean Validation)

---

## 🚀 Como Rodar

### Opção 1 — H2 em memória (mais rápido para testar)

```bash
# 1. Clone o repositório
git clone https://github.com/brunofdev/sistema-restaurante-api

# 2. Entre na pasta
cd sistema-restaurante-api

# 3. Rode com Maven
./mvnw spring-boot:run
```

> A API sobe com **banco H2 em memória** e dados de teste carregados automaticamente.  
> Nenhum banco de dados externo precisa ser configurado.

### Opção 2 — Docker Compose com PostgreSQL

```bash
# Sobe a API + banco PostgreSQL juntos
docker-compose up --build
```

> O `docker-compose.yml` configura automaticamente o banco `restaurante_db` e conecta os serviços.

Acesse a documentação interativa em:
```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Testando o Projeto

### 1. Autenticação

O sistema já inicia populado com dados de teste em todos os módulos. O primeiro passo é se autenticar no Swagger — o mesmo endpoint serve tanto clientes quanto operadores:

![Como logar no Swagger](https://github.com/user-attachments/assets/ec53396e-ac22-4a6c-881b-3af180348289)

---

### 2. Fazendo um Pedido

O teste mais interessante é realizar um pedido completo:

![Fazendo um pedido](https://github.com/user-attachments/assets/6dc941fa-f12e-4e43-91d8-6d29edcf60bc)

Em caso de sucesso, o sistema retorna um JSON no formato padrão do sistema:

![Resposta de sucesso](https://github.com/user-attachments/assets/632eb0e6-7632-4987-8040-77dcc67941cd)

---

### 3. Atualizando o Status do Pedido

Administradores devem atualizar o status do pedido pelo painel:

![Atualização de status](https://github.com/user-attachments/assets/5ccb9f59-9c13-441e-a9bf-a01efc9ac369)

---

### 4. Testando o WebSocket

Na raiz do projeto existe o arquivo `TestadorWebSocket.html`. Abra-o no navegador para simular o sistema completo em dois painéis — o painel do cliente e o painel da cozinha — com alertas e atualizações de status em tempo real.

---

## 📐 Diagramas

| Arquivo | Descrição |
|---|---|
| `Modelagem do Banco Sistema.drawio` | Modelagem completa do banco de dados |
| `DIAGRAMA UML - ESTRUTURA CARDAPIO, PRODUTO E CARDAPIOPRODUTO.pdf` | Relacionamento entre cardápios, produtos e a tabela associativa |
| `UML-PLANT-API.puml` | Diagrama de classes completo no formato PlantUML |

---

## 📬 Contato

Desenvolvido por **Bruno Fraga** · [github.com/brunofdev](https://github.com/brunofdev)
