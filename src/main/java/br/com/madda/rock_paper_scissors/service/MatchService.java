package br.com.madda.rock_paper_scissors.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import br.com.madda.rock_paper_scissors.dto.MatchDTO;
import br.com.madda.rock_paper_scissors.dto.ScoreboardDTO;
import br.com.madda.rock_paper_scissors.entity.Match;
import br.com.madda.rock_paper_scissors.entity.Player;
import br.com.madda.rock_paper_scissors.entity.enums.Move;
import br.com.madda.rock_paper_scissors.entity.enums.Result;
import br.com.madda.rock_paper_scissors.mapper.MatchMapper;
import br.com.madda.rock_paper_scissors.repository.MatchRepository;

public class MatchService {
  private final MatchRepository repository;
  private final MatchMapper mapper;

  public MatchService(MatchRepository repository, MatchMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public MatchDTO save(Player player, Move playerMove, Move computerMove, Result result)
      throws SQLException {
    Match match = new Match(player, playerMove, computerMove, result);
    Match matchSaved = repository.save(match);

    return mapper.toDTO(matchSaved);
  }

  public List<MatchDTO> findMatches(Player player) throws SQLException {
    return repository.findByPlayer(player).stream().map(mapper::toDTO).collect(Collectors.toList());
  }

  public ScoreboardDTO getScoreboard(Player player) throws SQLException {
    return repository.getScoreboardByPlayer(player);
  }
}
