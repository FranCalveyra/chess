package edu.austral.dissis.chess.piece.movement.type;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AbsColumnDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AndRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.IsAnEnemy;
import edu.austral.dissis.common.piece.movement.restrictions.validators.MovementRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.RowDistance;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.GameMove;
import java.awt.Color;

public class PawnTaking implements PieceMovement {

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    Color team = context.pieceAt(move.from()).getPieceColour();
    MovementRestrictionValidator validator = getValidator(team);
    return validator.isValidMove(move, context);
  }

  private MovementRestrictionValidator getValidator(Color team) {
    MovementRestrictionValidator dx = new AbsColumnDistance(1);
    int colorBasedDisplacement = team == Color.BLACK ? -1 : 1;
    MovementRestrictionValidator dy = new RowDistance(colorBasedDisplacement);
    MovementRestrictionValidator movement = new AndRestrictionValidator(dx, dy);
    return new AndRestrictionValidator(movement, new IsAnEnemy());
  }
}
