package br.com.madda.rock_paper_scissors.mapper;

import br.com.madda.rock_paper_scissors.dto.MatchDTO;
import br.com.madda.rock_paper_scissors.model.Match;
import br.com.madda.rock_paper_scissors.model.enums.Move;
import br.com.madda.rock_paper_scissors.model.enums.Result;

public class MatchMapper {
  public MatchDTO toDto(Match match) {
    if (match == null) {
      return null;
    }

    MatchDTO dto = new MatchDTO();
    dto.setId(match.getId());
    dto.setPlayerId(match.getPlayerId());
    dto.setPlayerMove(match.getPlayerMove().name());
    dto.setComputerMove(match.getComputerMove().name());
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
    match.setPlayerId(dto.getPlayerId());
    match.setPlayerMove(Move.fromString(dto.getPlayerMove()));
    match.setComputerMove(Move.fromString(dto.getComputerMove()));
    match.setResult(Result.valueOf(dto.getResult()));
    match.setCreatedAt(dto.getCreatedAt());

    return match;
  }
}
