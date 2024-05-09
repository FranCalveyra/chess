package edu.austral.dissis.checkers.piece.movement.type;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.piece.movement.restrictions.rules.*;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AndRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.MovementRestrictionValidator;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.GameMove;
import java.awt.Color;

public class CheckersMovement implements PieceMovement {
  @Override
  public boolean isValidMove(GameMove move, Board context) {
    Piece piece = context.pieceAt(move.from());
    return getCheckersMovementValidator(piece.getPieceColour(), piece.getType())
        .isValidMove(move, context);
  }

  private MovementRestrictionValidator getCheckersMovementValidator(Color team, PieceType type) {
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(1));
    int colorBasedRowDistance = team == Color.BLACK ? -1 : 1;
    MovementRestriction rowRestriction =
        type == CheckersType.MAN ? new RowDistance(colorBasedRowDistance) : new AbsRowDistance(1);
    MovementRestrictionValidator dy = new AndRestrictionValidator(rowRestriction, dx, null);
    return new AndRestrictionValidator(new ClearTile(), dy, null);
  }
}
