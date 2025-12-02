# 📦 API REST com Spring Boot

Este projeto é uma API RESTful desenvolvida em **Java 17** com **Spring Boot**, voltada para fins educacionais, com foco em aplicar boas práticas de desenvolvimento backend moderno.

## 🎯 Objetivos

- Aplicar princípios SOLID e Clean Code.
- Utilizar arquitetura MVC com foco em coesão e separação de responsabilidades.
- Implementar organização baseada em funcionalidades (_Feature-Based Structure_).
- Realizar persistência de dados com Spring Data JPA e Hibernate.
- Integrar com banco de dados relacional (MySQL).
- Criar testes automatizados com JUnit 5 e Mockito.
- Documentar a API com Swagger (OpenAPI).
- Em breve: autenticação com JWT.

## 🧱 Estrutura do Projeto

- **Organização por Funcionalidades**: cada funcionalidade da aplicação possui sua própria estrutura interna (controller, service, repository, etc.).
- **Padrão MVC**: separação clara entre as camadas de Model, View (API/DTOs), e Controller.
- **Princípios de Design**:
    - SRP (Single Responsibility Principle)
    - SOLID
    - Clean Architecture (de forma simplificada)

## ⚙️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **Hibernate**
- **Swagger/OpenAPI**
- **Postgree**
- **JUnit 5**
- **Mockito**
- **Maven**
- **JWT**
- **Docker**

## Funcionalidades principais (Já implementadas):  

- **Crud de Produtos,**
- **Crud de Cardapios,**
- **Associa produtos a Cardapios,**
- **Cria e atualiza pedidos dinamicamente com WebSocket,**
- **Todas as Rotas protegidas por Regras.**
- **Autenticação via JWT.**
    




