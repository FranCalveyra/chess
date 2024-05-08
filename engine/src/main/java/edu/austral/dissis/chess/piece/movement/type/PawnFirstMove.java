package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.utils.enums.MoveType.VERTICAL;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.rules.AbsColumnDistance;
import edu.austral.dissis.common.piece.movement.restrictions.rules.ClearTile;
import edu.austral.dissis.common.piece.movement.restrictions.rules.NoPieceInPath;
import edu.austral.dissis.common.piece.movement.restrictions.rules.PieceHasNotMoved;
import edu.austral.dissis.common.piece.movement.restrictions.rules.RowDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AndRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.MovementRestrictionValidator;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.GameMove;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;

public class PawnFirstMove implements PieceMovement {

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    MovementRestrictionValidator validator =
        getPawnFirstMoveRestrictions(context.pieceAt(move.from()).getPieceColour());
    return validator.isValidMove(move, context);
  }

  private MovementRestrictionValidator getPawnFirstMoveRestrictions(Color pieceColour) {
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(0));
    int colorBasedRowDistance = pieceColour == Color.BLACK ? -2 : 2;
    MovementRestrictionValidator dy =
        new AndRestrictionValidator(new RowDistance(colorBasedRowDistance));
    MovementRestrictionValidator left = craftLeftValidator(dx, dy);
    return new AndRestrictionValidator(new ClearTile(), left, null);
  }

  private @NotNull MovementRestrictionValidator craftLeftValidator(
      MovementRestrictionValidator dx, MovementRestrictionValidator dy) {
    MovementRestrictionValidator leftBottom = new AndRestrictionValidator(null, dx, dy);
    MovementRestrictionValidator hasNotMoved = new AndRestrictionValidator(new PieceHasNotMoved());

    MovementRestrictionValidator otherLeftBottom =
        new AndRestrictionValidator(null, leftBottom, hasNotMoved);
    MovementRestrictionValidator rightBottom =
        new AndRestrictionValidator(new NoPieceInPath(VERTICAL));

    return new AndRestrictionValidator(null, otherLeftBottom, rightBottom);
  }
}
