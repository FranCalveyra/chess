package edu.austral.dissis.common.rules.premovement;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class InsideBoardBounds implements PreMovementRule {
  @Override
  public boolean isValidRule(GameMove move, ChessGame game) {
    Board board = game.getBoard();
    return insideBoardBounds(board, move.from()) && insideBoardBounds(board, move.to());
  }

  @Override
  public String getStringErrorRepresentation() {
    return "Should be inside board bounds";
  }

  private boolean insideBoardBounds(Board board, BoardPosition pos) {
    int i = pos.getRow();
    int j = pos.getColumn();
    return i < board.getRows() && i >= 0 && j < board.getColumns() && j >= 0;
  }
}
