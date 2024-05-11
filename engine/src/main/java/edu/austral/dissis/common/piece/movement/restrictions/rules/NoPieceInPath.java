package edu.austral.dissis.common.piece.movement.restrictions.rules;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.enums.MoveType;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.validators.PiecePathValidator;

public class NoPieceInPath implements MovementRestriction {
  private final MoveType moveType;
  private final PiecePathValidator pathValidator;

  public NoPieceInPath(MoveType moveType) {
    this.moveType = moveType;
    this.pathValidator = new PiecePathValidator();
  }

  @Override
  public boolean isValidRestriction(GameMove move, Board context) {
    return pathValidator.isNoPieceBetween(move.from(), move.to(), context, moveType);
  }
}
