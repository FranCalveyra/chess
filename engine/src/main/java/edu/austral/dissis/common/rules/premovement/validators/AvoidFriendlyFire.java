package edu.austral.dissis.common.rules.premovement.validators;

import edu.austral.dissis.common.game.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.GameMove;

public class AvoidFriendlyFire implements PreMovementValidator {
  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    Piece pieceToMove = game.getBoard().pieceAt(move.from());
    Piece pieceAtDestination = game.getBoard().pieceAt(move.to());
    return pieceAtDestination == null
        || pieceToMove.getPieceColour() != pieceAtDestination.getPieceColour();
  }

  @Override
  public String getFailureMessage() {
    return "Teammate there";
  }
}
