USE campeonatosesportivosvolei;

-- =====================
-- USUARIOS
-- =====================

INSERT INTO usuario (nome, email, senha_hash, perfil)
VALUES 
('Administrador', 'admin@volei.com', '123456', 'ADMIN'),
('Carlos Organizador', 'carlos@volei.com', '123456', 'ORGANIZADOR'),
('Mariana Arbitra', 'mariana@volei.com', '123456', 'ARBITRO'),
('Visitante Sistema', 'visitante@volei.com', '123456', 'VISUALIZADOR');


-- =====================
-- CAMPEONATO
-- =====================

INSERT INTO campeonato
(id_organizador, nome, formato, data_inicio, data_fim, ativo, iniciado)
VALUES
(2, 'Copa InterUFRA 2025', 'PONTOS_CORRIDOS', '2025-12-01', '2025-12-14', TRUE, TRUE);


-- =====================
-- EQUIPES
-- =====================

INSERT INTO equipe
(id_campeonato, nome, sigla, cor_primaria, cor_secundaria)
VALUES
(1, 'Tigres', 'TIG', 'Azul', 'Branco'),
(1, 'Falcons', 'FAL', 'Preto', 'Dourado');


-- =====================
-- JOGADORES
-- =====================

INSERT INTO jogador
(id_equipe, nome, posicao, numero_camisa)
VALUES
(1, 'Joao Silva', 'Levantador', 10),
(1, 'Diego Souza', 'Ponteiro', 7),
(2, 'Rafael Gomes', 'Central', 5),
(2, 'Lucas Pereira', 'Oposto', 9);


-- =====================
-- PARTIDA
-- =====================

INSERT INTO partida
(id_campeonato, id_equipe_mandante, id_equipe_visitante, data_hora, local, resultado_final, finalizada, numero_rodada)
VALUES
(1, 1, 2, '2025-12-05 15:00:00', 'Quadra A', '2x1', TRUE, 1);


-- =====================
-- SETS DA PARTIDA
-- =====================

INSERT INTO set_partida
(id_partida, numero_set, pontos_mandante, pontos_visitante)
VALUES
(1, 1, 25, 21),
(1, 2, 22, 25),
(1, 3, 15, 12);


-- =====================
-- CRITERIOS DE DESEMPATE
-- =====================

INSERT INTO criterio_desempate
(id_campeonato, ordem, criterio)
VALUES
(1, 1, 'SALDO_SETS'),
(1, 2, 'SETS_PRO');


-- =====================
-- AUDITORIA
-- =====================

INSERT INTO auditoria_resultado
(id_partida, id_usuario, justificativa, dados_anteriores)
VALUES
(1, 3, 'Correcao de resultado', 'Resultado anterior: 3x1');