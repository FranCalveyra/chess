package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.utils.type.MoveType.HORIZONTAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.restrictions.AbsColumnDistance;
import edu.austral.dissis.chess.piece.movement.restrictions.AbsRowDistance;
import edu.austral.dissis.chess.piece.movement.restrictions.ClearTile;
import edu.austral.dissis.chess.piece.movement.restrictions.IsAnEnemy;
import edu.austral.dissis.chess.piece.movement.restrictions.NoPieceInPath;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.move.ChessPosition;
import edu.austral.dissis.chess.utils.type.MoveType;
import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;
import edu.austral.dissis.chess.validators.OrRestrictionValidator;
import org.jetbrains.annotations.NotNull;

public class HorizontalMovement implements PieceMovement {
  private final int maxDistance;

  public HorizontalMovement(int maxDistance) {
    this.maxDistance = maxDistance;
  }

  public HorizontalMovement() {
    maxDistance = Integer.MAX_VALUE;
  }

  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    ChessPosition oldPos = move.from();
    ChessPosition newPos = move.to();
    int colDistance =
        maxDistance != Integer.MAX_VALUE
            ? maxDistance
            : Math.abs(newPos.getColumn() - oldPos.getColumn());
    MovementRestrictionValidator validator = getHorizontalRestrictionValidator(colDistance);
    return validator.isValidMove(move, context);
  }

  private MovementRestrictionValidator getHorizontalRestrictionValidator(int distance) {
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(distance));
    MovementRestrictionValidator dy = new AndRestrictionValidator(new AbsRowDistance(0));
    return getBaseValidator(dx, dy, HORIZONTAL);
  }

  @NotNull
  protected static MovementRestrictionValidator getBaseValidator(
      MovementRestrictionValidator dx, MovementRestrictionValidator dy, MoveType moveType) {
    MovementRestrictionValidator noPieceBetween = new AndRestrictionValidator(null, dx, dy);

    MovementRestrictionValidator isEnemy = new AndRestrictionValidator(new IsAnEnemy());

    MovementRestrictionValidator right = new OrRestrictionValidator(new ClearTile(), null, isEnemy);
    return new AndRestrictionValidator(new NoPieceInPath(moveType), noPieceBetween, right);
  }
}
