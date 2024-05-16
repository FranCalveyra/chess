package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.common.utils.enums.MoveType.VERTICAL;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AbsColumnDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AndRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.ClearTile;
import edu.austral.dissis.common.piece.movement.restrictions.validators.MovementRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.NoPieceInPath;
import edu.austral.dissis.common.piece.movement.restrictions.validators.RowDistance;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.GameMove;
import java.awt.Color;

public class PawnMovement implements PieceMovement {
  @Override
  public boolean isValidMove(GameMove move, Board context) {
    Color team = context.pieceAt(move.from()).getPieceColour();
    MovementRestrictionValidator validator = getPawnRestrictions(team);
    return validator.isValidMove(move, context);
  }

  private MovementRestrictionValidator getPawnRestrictions(Color team) {
    MovementRestrictionValidator dy =
        team == Color.BLACK ? new RowDistance(-1) : new RowDistance(1);
    MovementRestrictionValidator dx = new AbsColumnDistance(0);
    MovementRestrictionValidator movement = new AndRestrictionValidator(dy, dx);
    MovementRestrictionValidator moveAndPath =
        new AndRestrictionValidator(movement, new NoPieceInPath(VERTICAL));
    return new AndRestrictionValidator(moveAndPath, new ClearTile());
  }
}
