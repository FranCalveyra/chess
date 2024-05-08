package edu.austral.dissis.chess.piece.movement.type;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.rules.AbsColumnDistance;
import edu.austral.dissis.common.piece.movement.restrictions.rules.IsAnEnemy;
import edu.austral.dissis.common.piece.movement.restrictions.rules.RowDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AndRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.MovementRestrictionValidator;
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
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(1));
    int colorBasedDisplacement = team == Color.BLACK ? -1 : 1;
    MovementRestrictionValidator dy =
        new AndRestrictionValidator(new RowDistance(colorBasedDisplacement));
    return new AndRestrictionValidator(new IsAnEnemy(), dx, dy);
  }
}
