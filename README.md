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


## 5. Acessar o Sistema

Após iniciar a aplicação, acessar:

http://localhost:8080
