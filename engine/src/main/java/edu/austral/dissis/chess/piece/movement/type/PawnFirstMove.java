package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.utils.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.restrictions.*;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;
import edu.austral.dissis.chess.validators.PiecePathValidator;
import org.jetbrains.annotations.NotNull;

public class PawnFirstMove implements PieceMovement {

  private MovementRestrictionValidator validator;
  public PawnFirstMove(){
    this.validator = getPawnFirstMoveRestrictions();
  }

  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    return validator.isValidMove(move,context);
  }


  private MovementRestrictionValidator getPawnFirstMoveRestrictions() {
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(0));
    MovementRestrictionValidator dy = new AndRestrictionValidator(new AbsRowDistance(2));
    MovementRestrictionValidator left = craftLeftValidator(dx, dy);
    MovementRestrictionValidator right = new AndRestrictionValidator(new ClearTile());
    return new AndRestrictionValidator(null, left, right);
  }

  private @NotNull MovementRestrictionValidator craftLeftValidator(MovementRestrictionValidator dx, MovementRestrictionValidator dy) {
    MovementRestrictionValidator leftBottom = new AndRestrictionValidator(null, dx, dy);
    MovementRestrictionValidator hasNotMoved = new AndRestrictionValidator(new PieceHasNotMoved());

    MovementRestrictionValidator otherLeftBottom = new AndRestrictionValidator(null, leftBottom, hasNotMoved);
    MovementRestrictionValidator rightBottom = new AndRestrictionValidator(new NoPieceInPath(VERTICAL));

      return new AndRestrictionValidator(null,otherLeftBottom,rightBottom);
  }

}
