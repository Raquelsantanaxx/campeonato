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

## 👥 Equipe do Projeto

| Responsabilidade | Membro(s) |
| :--- | :--- |
| **Gerência de Projeto** | Raquel Santana de Souza |
| **DBA (Banco de Dados)** | Danilo Morais de Azevedo e Raquel Santana de Souza |
| **Back-end** | Raquel Santana de Souza |
| **Front-end** | Raquel Santana de Souza |
| **UX (Interface)** | Kallel Garcez Torres e Luani Vanderlea Piedade Pereira e |
| **Documentação** | Caian Aiury da Cunha Carvalho |

## Requisitos
O levantamento de requisitos e primeiras definições do sistema podem ser consultados no seguinte documento:
[Requisitos V.2.pdf](https://github.com/user-attachments/files/25697719/Requisitos.V.2.pdf)

## Scripts do banco - Banco De Dados

### Banco de Dados
O sistema utiliza **MySQL** como banco de dados relacional.

**Nome do banco:**
campeonatosesportivosvolei


Na pasta `database` estão disponíveis os scripts necessários:

- `database/script tabelas alterado.sql` → criação das tabelas  
- `database/script popular banco.sql ` → dados de exemplo (opcional)

---

**2. Criar o banco de dados**
No MySQL execute o script: script tabelas alterado. Isso irá criar todas as tabelas necessárias para o sistema.
Opcionalmente, você pode executar.

**3. Configurar acesso ao banco**
Verifique o arquivo: src/main/resources/application.yml

<pre><code class="language-yaml">```
spring:
datasource:
url: jdbc:mysql://localhost:3306/campeonatosesportivosvolei
username: root
password: root


[Script tabelas alterado.sql](https://github.com/user-attachments/files/25751950/Script.tabelas.alterado.sql)
[script popular banco v2.sql](https://github.com/user-attachments/files/25751960/script.popular.banco.v2.sql)
