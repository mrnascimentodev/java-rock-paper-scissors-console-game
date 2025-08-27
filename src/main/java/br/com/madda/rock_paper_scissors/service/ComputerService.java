package br.com.madda.rock_paper_scissors.service;

import java.util.Random;
import br.com.madda.rock_paper_scissors.model.enums.Move;

public class ComputerService {
  private final Random random;

  public ComputerService() {
    this.random = new Random();
  }

  public Move makeMove() {
    Move[] moves = Move.values();
    return moves[random.nextInt(moves.length)];
  }
}
