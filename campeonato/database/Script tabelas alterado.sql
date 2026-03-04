CREATE DATABASE IF NOT EXISTS campeonatosesportivosvolei;
USE campeonatosesportivosvolei; 

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS auditoria_resultado;
DROP TABLE IF EXISTS criterio_desempate;
DROP TABLE IF EXISTS set_partida;
DROP TABLE IF EXISTS partida;
DROP TABLE IF EXISTS jogador;
DROP TABLE IF EXISTS equipe;
DROP TABLE IF EXISTS campeonato;
DROP TABLE IF EXISTS usuario;

SET FOREIGN_KEY_CHECKS = 1;


-- =========================
-- TABELA USUARIO
-- =========================
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha_hash VARCHAR(150) NOT NULL,
    perfil ENUM('ADMIN','ORGANIZADOR','ARBITRO','JOGADOR','VISUALIZADOR') NOT NULL
);


-- =========================
-- TABELA CAMPEONATO
-- =========================
CREATE TABLE campeonato (
    id_campeonato INT AUTO_INCREMENT PRIMARY KEY,
    id_organizador INT NOT NULL,
    nome VARCHAR(150) NOT NULL,
    formato ENUM('PONTOS_CORRIDOS','MATA_MATA') NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    ativo BOOLEAN DEFAULT TRUE,
    iniciado BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_campeonato_usuario
        FOREIGN KEY (id_organizador)
        REFERENCES usuario(id_usuario)
        ON DELETE CASCADE
);


-- =========================
-- TABELA EQUIPE
-- =========================
CREATE TABLE equipe (
    id_equipe INT AUTO_INCREMENT PRIMARY KEY,
    id_campeonato INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    sigla VARCHAR(50) NOT NULL,
    cor_primaria VARCHAR(50),
    cor_secundaria VARCHAR(50),

    CONSTRAINT fk_equipe_campeonato
        FOREIGN KEY (id_campeonato)
        REFERENCES campeonato(id_campeonato)
);


-- =========================
-- TABELA JOGADOR
-- =========================
CREATE TABLE jogador (
    id_jogador INT AUTO_INCREMENT PRIMARY KEY,
    id_equipe INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    posicao VARCHAR(100) NOT NULL,
    numero_camisa INT NOT NULL,

    CONSTRAINT fk_jogador_equipe
        FOREIGN KEY (id_equipe)
        REFERENCES equipe(id_equipe)
        ON DELETE CASCADE
);


-- =========================
-- TABELA PARTIDA
-- =========================
CREATE TABLE partida (
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    id_campeonato INT NOT NULL,
    id_equipe_mandante INT NOT NULL,
    id_equipe_visitante INT NOT NULL,
    data_hora DATETIME,
    local VARCHAR(150),
    resultado_final VARCHAR(10),
    finalizada BOOLEAN NOT NULL DEFAULT FALSE,
    numero_rodada INT,

    CONSTRAINT fk_partida_campeonato
        FOREIGN KEY (id_campeonato)
        REFERENCES campeonato(id_campeonato)
        ON DELETE CASCADE,

    CONSTRAINT fk_partida_mandante
        FOREIGN KEY (id_equipe_mandante)
        REFERENCES equipe(id_equipe),

    CONSTRAINT fk_partida_visitante
        FOREIGN KEY (id_equipe_visitante)
        REFERENCES equipe(id_equipe)
);


-- =========================
-- TABELA SET_PARTIDA
-- =========================
CREATE TABLE set_partida (
    id_set INT AUTO_INCREMENT PRIMARY KEY,
    id_partida INT NOT NULL,
    numero_set INT NOT NULL,
    pontos_mandante INT NOT NULL,
    pontos_visitante INT NOT NULL,

    CONSTRAINT uq_set UNIQUE (id_partida, numero_set),

    CONSTRAINT fk_set_partida
        FOREIGN KEY (id_partida)
        REFERENCES partida(id_partida)
        ON DELETE CASCADE
);


-- =========================
-- TABELA CRITERIO_DESEMPATE
-- =========================
CREATE TABLE criterio_desempate (
    id_criterio INT AUTO_INCREMENT PRIMARY KEY,
    id_campeonato INT NOT NULL,
    ordem INT NOT NULL,
    criterio VARCHAR(100) NOT NULL,

    CONSTRAINT fk_criterio_campeonato
        FOREIGN KEY (id_campeonato)
        REFERENCES campeonato(id_campeonato)
        ON DELETE CASCADE
);


-- =========================
-- TABELA AUDITORIA_RESULTADO
-- =========================
CREATE TABLE auditoria_resultado (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    id_partida INT NOT NULL,
    id_usuario INT NOT NULL,
    data_alteracao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    justificativa VARCHAR(255) NOT NULL,
    dados_anteriores TEXT,

    CONSTRAINT fk_auditoria_partida
        FOREIGN KEY (id_partida)
        REFERENCES partida(id_partida),

    CONSTRAINT fk_auditoria_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);