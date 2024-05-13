package edu.austral.dissis.common.rules.premovement.rules;

import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.utils.move.GameMove;

public class PieceAtPosition implements PreMovementRule {
  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    return game.getBoard().pieceAt(move.from()) != null;
  }
}
