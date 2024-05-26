package edu.austral.dissis.common.rules.premovement.validators;

import edu.austral.dissis.common.game.BoardGame;
import edu.austral.dissis.common.utils.move.GameMove;

public class PieceAtPosition implements PreMovementValidator {
  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    return game.getBoard().pieceAt(move.from()) != null;
  }

  @Override
  public String getFailureMessage() {
    return "No piece at that position";
  }
}
