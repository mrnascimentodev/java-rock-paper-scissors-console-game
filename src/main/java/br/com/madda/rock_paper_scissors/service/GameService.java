package br.com.madda.rock_paper_scissors.service;

import br.com.madda.rock_paper_scissors.model.enums.Move;
import br.com.madda.rock_paper_scissors.model.enums.Result;

public class GameService {
  public Result calculateResult(Move playerMove, Move computerMove) {
    if (playerMove == computerMove) {
      return Result.TIE;
    }

    if (playerMove.win(computerMove)) {
      return Result.VICTORY;
    }

    return Result.DEFEAT;
  }
}
