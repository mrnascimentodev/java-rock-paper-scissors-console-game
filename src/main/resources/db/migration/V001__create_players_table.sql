-- Migration: Criar tabela de jogadores
-- Versão: 001
-- Data: 2025-08-28

CREATE TABLE IF NOT EXISTS players (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para otimização
CREATE INDEX idx_players_name ON players(name);
CREATE INDEX idx_players_created_at ON players(created_at);

-- Comentários nas colunas
COMMENT ON TABLE players IS 'Tabela de jogadores do jogo Pedra, Papel e Tesoura';
COMMENT ON COLUMN players.id IS 'Identificador único do jogador';
COMMENT ON COLUMN players.name IS 'Nome do jogador (único no sistema)';
COMMENT ON COLUMN players.created_at IS 'Data e hora de criação do registro';
COMMENT ON COLUMN players.updated_at IS 'Data e hora da última atualização';
