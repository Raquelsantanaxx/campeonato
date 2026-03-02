# BoraVolêi Sistema de Gestão de Campeonatos de Vôlei
Projeto desenvolvido para a disciplina Desenvolvimento Web, do 6º semestre do curso de Licenciatura em Ciência da Computação da Universidade Federal Rural da Amazônia (UFRA) - Campus Belém.

O sistema tem como objetivo gerenciar campeonatos  inicialmente na modalidade de Vôlei, permitindo o cadastro de campeonatos, equipes, partidas e sets

**Java 17 + Spring Boot**, com foco no gerenciamento de campeonatos amadores de voleibol.

## O que o sistema faz
O sistema permite realizar as seguintes operações:
* **Cadastro e login de usuários**.
* **Cadastro de campeonatos**.
* **Cadastro de equipes**.
* **Cadastro de jogadores**.
* **Geração automática de partidas** (modelo de pontos corridos).
* **Registro de resultados por sets** (ex: 3x2).
* **Cálculo automático de classificação**.

## Como Executar o Projeto

### 🔧 1. Pré-requisitos
Antes de executar, é necessário ter instalado:
* **Java 17**.
* **Maven**.
* **MySQL 8+**.

## 🗄 2. Criar o Banco de Dados

No MySQL, execute:

CREATE DATABASE campeonatosesportivosvolei;

# Configurar o application.properties

No arquivo:
src/main/resources/application.properties

coloque seu usuario e senha - recomendo suar o root root 
spring.datasource.url=jdbc:mysql://localhost:3306/campeonatosesportivosvolei
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


## Acessar o Sistema

Após iniciar a aplicação, acessar:

http://localhost:8080

## Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias

| Tecnologia | Função |
| :--- | :--- |
| **Java 17** | Linguagem de programação principal |
| **Spring Boot 3.3.5** | Framework para desenvolvimento da aplicação |
| **Spring Data JPA** | Abstração para persistência de dados |
| **Hibernate** | Framework ORM (Mapeamento Objeto-Relacional) |
| **Spring Security** | Implementação de autenticação e autorização |
| **Thymeleaf** | Motor de template para renderização do HTML |
| **MySQL** | Sistema de gerenciamento de banco de dados relacional |
| **Maven** | Gerenciador de dependências e automação de build |
| **Tomcat Embedded** | Servidor de aplicação interno para execução local |


## Equipe de Desenvolvimento
Gerência de Projeto :
RAQUEL SANTANA DE SOUZA
DBA :
DANILO MORAIS DE AZEVEDO
RAQUEL SANTANA DE SOUZA
Back-end
RAQUEL SANTANA DE SOUZA
Front-end 
RAQUEL SANTANA DE SOUZA
UX :
KALLEL GARCEZ TORRES
LUANI VANDERLEA PIEDADE PEREIRA
Documentações 
CAIAN AIURY DA CUNHA CARVALHO
