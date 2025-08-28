package br.com.madda.rock_paper_scissors.mapper;

import br.com.madda.rock_paper_scissors.dto.MatchDTO;
import br.com.madda.rock_paper_scissors.entity.Match;
import br.com.madda.rock_paper_scissors.entity.enums.Move;
import br.com.madda.rock_paper_scissors.entity.enums.Result;

public class MatchMapper {
  public MatchDTO toDTO(Match match) {
    if (match == null) {
      return null;
    }

    MatchDTO dto = new MatchDTO();
    dto.setId(match.getId());
    dto.setPlayer(match.getPlayer());
    dto.setPlayerPlay(match.getPlayerPlay().name());
    dto.setComputerPlay(match.getComputerPlay().name());
    dto.setResult(match.getResult().name());
    dto.setCreatedAt(match.getCreatedAt());

    return dto;
  }

  public Match toEntity(MatchDTO dto) {
    if (dto == null) {
      return null;
    }

    Match match = new Match();
    match.setId(dto.getId());
    match.setPlayer(dto.getPlayer());
    match.setPlayerPlay(Move.fromString(dto.getPlayerPlay()));
    match.setComputerPlay(Move.fromString(dto.getComputerPlay()));
    match.setResult(Result.valueOf(dto.getResult()));
    match.setCreatedAt(dto.getCreatedAt());

    return match;
  }
}
