package edu.austral.dissis.common.turn;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.playresult.PlayResult;
import java.awt.Color;

public interface TurnSelector {
  Color getCurrentTurn();

  TurnSelector changeTurn(GameMove lastMove, Board board, PlayResult result);
}
