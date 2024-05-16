package edu.austral.dissis.common.rules.premovement.validators;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class InsideBoardBounds implements PreMovementValidator {
  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    Board board = game.getBoard();
    return insideBoardBounds(board, move.from()) && insideBoardBounds(board, move.to());
  }

  @Override
  public String getFailureMessage() {
    return "Out of board bounds";
  }

  private boolean insideBoardBounds(Board board, BoardPosition pos) {
    int i = pos.getRow();
    int j = pos.getColumn();
    return i < board.getRows() && i >= 0 && j < board.getColumns() && j >= 0;
  }
}
