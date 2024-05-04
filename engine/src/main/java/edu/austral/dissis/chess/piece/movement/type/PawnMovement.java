package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.utils.type.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.restrictions.AbsColumnDistance;
import edu.austral.dissis.chess.piece.movement.restrictions.ClearTile;
import edu.austral.dissis.chess.piece.movement.restrictions.MovementRestriction;
import edu.austral.dissis.chess.piece.movement.restrictions.NoPieceInPath;
import edu.austral.dissis.chess.piece.movement.restrictions.RowDistance;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;
import java.awt.Color;

public class PawnMovement implements PieceMovement {
  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    Color team = context.pieceAt(move.from()).getPieceColour();
    MovementRestrictionValidator validator = getPawnRestrictions(team);
    return validator.isValidMove(move, context);
  }

  private MovementRestrictionValidator getPawnRestrictions(Color team) {
    MovementRestriction colorBasedMovement =
        team == Color.BLACK ? new RowDistance(-1) : new RowDistance(1);
    MovementRestrictionValidator dy = new AndRestrictionValidator(colorBasedMovement);
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(0));
    MovementRestrictionValidator movement =
        new AndRestrictionValidator(new NoPieceInPath(VERTICAL), dy, dx);
    return new AndRestrictionValidator(new ClearTile(), movement, null);
  }
}
