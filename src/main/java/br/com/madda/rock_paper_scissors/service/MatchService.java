package br.com.madda.rock_paper_scissors.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import br.com.madda.rock_paper_scissors.dto.MatchDTO;
import br.com.madda.rock_paper_scissors.dto.ScoreboardDTO;
import br.com.madda.rock_paper_scissors.mapper.MatchMapper;
import br.com.madda.rock_paper_scissors.model.Match;
import br.com.madda.rock_paper_scissors.model.enums.Move;
import br.com.madda.rock_paper_scissors.model.enums.Result;
import br.com.madda.rock_paper_scissors.repository.MatchRepository;

public class MatchService {
  private final MatchRepository repository;
  private final MatchMapper mapper;

  public MatchService(MatchRepository repository, MatchMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public MatchDTO save(Long playerId, Move playerMove, Move computerMove, Result result)
      throws SQLException {
    Match match = new Match(playerId, playerMove, computerMove, result);
    Match matchSaved = repository.save(match);

    return mapper.toDto(matchSaved);
  }

  public List<MatchDTO> findMatches(Long playerId) throws SQLException {
    return repository.findByPlayer(playerId).stream().map(mapper::toDto)
        .collect(Collectors.toList());
  }

  public ScoreboardDTO getScoreboard(Long playerId, String playerName) throws SQLException {
    return repository.getScoreboardByPlayer(playerId, playerName);
  }
}
