-- Migration: Criar tabela de partidas
-- Versão: 002
-- Data: 2025-08-28

CREATE TABLE IF NOT EXISTS matches (
  id BIGSERIAL PRIMARY KEY,
  player_id BIGINT NOT NULL,
  player_play VARCHAR(20) NOT NULL CHECK (player_play IN ('ROCK', 'PAPER', 'SCISSOR')),
  computer_play VARCHAR(20) NOT NULL CHECK (computer_play IN ('ROCK', 'PAPER', 'SCISSOR')),
  result VARCHAR(20) NOT NULL CHECK (result IN ('VICTORY', 'DEFEAT', 'TIE')),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  -- Chave estrangeira
  CONSTRAINT fk_matches_player FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);

-- Índices para otimização de consultas
CREATE INDEX idx_matches_player_id ON matches(player_id);
CREATE INDEX idx_matches_result ON matches(result);
CREATE INDEX idx_matches_created_at ON matches(created_at);
CREATE INDEX idx_matches_player_result ON matches(player_id, result);

-- Comentários
COMMENT ON TABLE matches IS 'Tabela de partidas do jogo Pedra, Papel e Tesoura';
COMMENT ON COLUMN matches.id IS 'Identificador único da partida';
COMMENT ON COLUMN matches.player_id IS 'Referência ao jogador';
COMMENT ON COLUMN matches.player_play IS 'Jogada do jogador (ROCK, PAPER, SCISSOR)';
COMMENT ON COLUMN matches.computer_play IS 'Jogada do computador';
COMMENT ON COLUMN matches.result IS 'Resultado da partida (VICTORY, DEFEAT, TIE)';
COMMENT ON COLUMN matches.created_at IS 'Data e hora da partida';
