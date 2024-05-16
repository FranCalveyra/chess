package edu.austral.dissis.common.rules.premovement.validators;

import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.GameMove;

public class TurnRule implements PreMovementValidator {
  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    Piece toMove = game.getBoard().pieceAt(move.from());
    return toMove.getPieceColour() == game.getCurrentTurn();
  }

  @Override
  public String getFailureMessage() {
    return "Not your turn";
  }
}
