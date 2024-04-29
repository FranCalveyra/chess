package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.utils.ChessMove;

public class PieceAtPosition implements PreMovementRule {
  @Override
  public boolean isValidRule(ChessMove move, ChessGame game) {
    return game.getBoard().pieceAt(move.from()) != null;
  }
}
