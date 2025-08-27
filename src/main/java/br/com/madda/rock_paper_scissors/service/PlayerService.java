package br.com.madda.rock_paper_scissors.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import br.com.madda.rock_paper_scissors.dto.PlayerDTO;
import br.com.madda.rock_paper_scissors.mapper.PlayerMapper;
import br.com.madda.rock_paper_scissors.model.Player;
import br.com.madda.rock_paper_scissors.repository.PlayerRepository;

public class PlayerService {
  private final PlayerRepository repository;
  private final PlayerMapper mapper;

  public PlayerService(PlayerRepository repository, PlayerMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public PlayerDTO createOrFindPlayer(String name) throws SQLException {
    Optional<Player> player = repository.findByName(name);

    if (player.isPresent()) {
      return mapper.toDto(player.get());
    }

    Player newPlayer = new Player(name);
    Player playerSaved = repository.save(newPlayer);

    return mapper.toDto(playerSaved);
  }

  public List<PlayerDTO> listAllPlayers() throws SQLException {
    return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
  }

  public Optional<PlayerDTO> findById(Long id) throws SQLException {
    return repository.findById(id).map(mapper::toDto);
  }
}
