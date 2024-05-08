package edu.austral.dissis.common.rules.premovement.rules;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.common.utils.move.GameMove;

public class PieceAtPosition implements PreMovementRule {
  @Override
  public boolean isValidRule(GameMove move, ChessGame game) {
    return game.getBoard().pieceAt(move.from()) != null;
  }
}
