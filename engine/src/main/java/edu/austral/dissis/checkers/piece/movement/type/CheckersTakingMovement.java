package edu.austral.dissis.checkers.piece.movement.type;

import static edu.austral.dissis.chess.utils.AuxStaticMethods.getPosBetween;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.piece.movement.restrictions.rules.AbsColumnDistance;
import edu.austral.dissis.common.piece.movement.restrictions.rules.AbsRowDistance;
import edu.austral.dissis.common.piece.movement.restrictions.rules.ClearTile;
import edu.austral.dissis.common.piece.movement.restrictions.rules.MovementRestriction;
import edu.austral.dissis.common.piece.movement.restrictions.rules.PieceBetweenIsAnEnemy;
import edu.austral.dissis.common.piece.movement.restrictions.rules.RowDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AndRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.MovementRestrictionValidator;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import java.awt.Color;
import java.util.List;

public class CheckersTakingMovement implements PieceMovement {
  // TODO: obligue the player to take the piece in front of him
  @Override
  public boolean isValidMove(GameMove move, Board context) {
    Piece piece = context.pieceAt(move.from());
    final MovementRestrictionValidator validator =
        getCheckersMovementValidator(piece.getPieceColour(), piece.getType());
    return validator.isValidMove(move, context);
  }

  private MovementRestrictionValidator getCheckersMovementValidator(Color team, PieceType type) {
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(2));
    int colorBasedRowDistance = team == Color.BLACK ? -2 : 2;
    MovementRestriction rowRestriction =
        type == CheckersType.MAN ? new RowDistance(colorBasedRowDistance) : new AbsRowDistance(2);
    MovementRestrictionValidator dy = new AndRestrictionValidator(rowRestriction, dx, null);
    MovementRestrictionValidator enemyBetween =
        new AndRestrictionValidator(new PieceBetweenIsAnEnemy(), dy, null);
    return new AndRestrictionValidator(new ClearTile(), enemyBetween, null);
  }

  @Override
  public List<GameMove> getMovesToExecute(GameMove move, Board context) {
    BoardPosition between = getPosBetween(move);
    return List.of(new GameMove(move.from(), between), new GameMove(between, move.to()));
  }
}
