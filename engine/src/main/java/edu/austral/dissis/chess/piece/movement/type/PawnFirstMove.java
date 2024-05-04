package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.utils.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.restrictions.*;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class PawnFirstMove implements PieceMovement {

  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    MovementRestrictionValidator validator = getPawnFirstMoveRestrictions(context.pieceAt(move.from()).getPieceColour());
    return validator.isValidMove(move,context);
  }


  private MovementRestrictionValidator getPawnFirstMoveRestrictions(Color pieceColour) {
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(0));
    int colorBasedRowDistance = pieceColour == Color.BLACK ? -2: 2;
    MovementRestrictionValidator dy = new AndRestrictionValidator(new RowDistance(colorBasedRowDistance));
    MovementRestrictionValidator left = craftLeftValidator(dx, dy);
    return new AndRestrictionValidator(new ClearTile(), left, null);
  }

  private @NotNull MovementRestrictionValidator craftLeftValidator(MovementRestrictionValidator dx, MovementRestrictionValidator dy) {
    MovementRestrictionValidator leftBottom = new AndRestrictionValidator(null, dx, dy);
    MovementRestrictionValidator hasNotMoved = new AndRestrictionValidator(new PieceHasNotMoved());

    MovementRestrictionValidator otherLeftBottom = new AndRestrictionValidator(null, leftBottom, hasNotMoved);
    MovementRestrictionValidator rightBottom = new AndRestrictionValidator(new NoPieceInPath(VERTICAL));

      return new AndRestrictionValidator(null,otherLeftBottom,rightBottom);
  }

}
