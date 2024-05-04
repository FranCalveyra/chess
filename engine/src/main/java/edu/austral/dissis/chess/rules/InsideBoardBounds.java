package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.move.ChessPosition;

public class InsideBoardBounds implements PreMovementRule {
  @Override
  public boolean isValidRule(ChessMove move, ChessGame game) {
    Board board = game.getBoard();
    return insideBoardBounds(board, move.from()) && insideBoardBounds(board, move.to());
  }

  @Override
  public String getStringErrorRepresentation() {
    return "Should be inside board bounds";
  }

  private boolean insideBoardBounds(Board board, ChessPosition pos) {
    int i = pos.getRow();
    int j = pos.getColumn();
    return i < board.getRows() && i >= 0 && j < board.getColumns() && j >= 0;
  }
}
