package br.com.madda.rock_paper_scissors.mapper;

import br.com.madda.rock_paper_scissors.dto.PlayerDTO;
import br.com.madda.rock_paper_scissors.entity.Player;

public class PlayerMapper {
  public PlayerDTO toDTO(Player player) {
    if (player == null) {
      return null;
    }

    return new PlayerDTO(player.getId(), player.getName(), player.getCreatedAt(),
        player.getUpdatedAt());
  }

  public Player toEntity(PlayerDTO dto) {
    if (dto == null) {
      return null;
    }

    Player player = new Player();
    player.setId(dto.getId());
    player.setName(dto.getName());
    player.setCreatedAt(dto.getCreatedAt());
    player.setUpdatedAt(dto.getUpdatedAt());

    return player;
  }
}
