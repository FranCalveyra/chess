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
import edu.austral.dissis.chess.validators.OrRestrictionValidator;
import edu.austral.dissis.chess.validators.PiecePathValidator;
import java.awt.Color;

public class PawnMovement implements PieceMovement {
  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    Color team = context.pieceAt(move.from()).getPieceColour();
    MovementRestrictionValidator validator = getPawnRestrictions(team);
    return validator.isValidMove(move,context);
  }

  private MovementRestrictionValidator getPawnRestrictions(Color team){
    MovementRestriction colorBasedMovement = team == Color.BLACK ? new RowDistance(-1) : new RowDistance(1);
    MovementRestrictionValidator dy = new AndRestrictionValidator(colorBasedMovement);
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(0));
    MovementRestrictionValidator movement = new AndRestrictionValidator(new NoPieceInPath(VERTICAL),dy,dx);
    return new AndRestrictionValidator(new ClearTile(), movement,null);
  }


}
