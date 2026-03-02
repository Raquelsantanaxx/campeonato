🏐 Sistema de Gestão de Campeonatos de Vôlei

Projeto acadêmico desenvolvido com Java 17 + Spring Boot, com foco no gerenciamento de campeonatos amadores de voleibol.

📌 Objetivo

O sistema permite:

✅ Cadastro e login de usuários

✅ Cadastro de campeonatos

✅ Cadastro de equipes

✅ Cadastro de jogadores

✅ Geração automática de partidas (pontos corridos)

✅ Registro de resultados por sets

✅ Cálculo automático de classificação

🚀 Como Executar o Projeto
🔧 1. Pré-requisitos

Antes de executar, é necessário ter instalado:

Java 17

Maven

MySQL 8+

Verifique o Java com:

java -version
🗄 2. Criar o Banco de Dados

No MySQL, execute:

CREATE DATABASE campeonatosesportivosvolei;
⚙️ 3. Configurar o application.properties

No arquivo:

src/main/resources/application.properties

Configure com seu usuário e senha:

spring.datasource.url=jdbc:mysql://localhost:3306/campeonatosesportivosvolei
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
▶️ 4. Executar a Aplicação
✔ Pela IDE (Recomendado)

Abrir o projeto no IntelliJ.

Aguardar o Maven baixar as dependências.

Executar:

GestaoCampeonatoApplication.java
✔ Pelo Terminal

Dentro da pasta do projeto:

mvn spring-boot:run
🌐 5. Acessar o Sistema

Após iniciar a aplicação, acessar:

http://localhost:8080
🧱 Tecnologias Utilizadas
Tecnologia	Função
Java 17	Linguagem principal
Spring Boot 3.3.5	Framework
Spring Data JPA	Persistência
Hibernate	ORM
Spring Security	Autenticação
Thymeleaf	Renderização HTML
MySQL	Banco de dados
Maven	Gerenciador de dependências
Tomcat Embedded	Servidor interno
🔐 Regras Importantes do Sistema

Apenas o organizador pode gerenciar equipes e jogadores.

Uma equipe não pode ser excluída caso possua partidas vinculadas.

A classificação é calculada automaticamente com base nos resultados.

O sistema utiliza arquitetura em camadas (Controller, Service, Repository).

🎓 Finalidade Acadêmica

Este projeto foi desenvolvido como atividade acadêmica, aplicando conceitos de:

Arquitetura em camadas

Segurança com Spring Security

Integridade referencial

Regras de negócio

Cálculo de classificação esportiva

👩‍💻 Desenvolvido por

Raquel Santana
