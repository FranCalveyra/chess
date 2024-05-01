package edu.austral.dissis.chess.piece.movement.type;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.restrictions.*;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;

import java.awt.Color;

public class PawnTaking implements PieceMovement {

  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    //TODO: change to validator
    Color team = context.pieceAt(move.from()).getPieceColour();
    MovementRestrictionValidator validator = getValidator(team);
    return validator.isValidMove(move,context);
  }

  private MovementRestrictionValidator getValidator(Color team){
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(1));
    int colorBasedDisplacement = team == Color.BLACK ? -1: 1;
    MovementRestrictionValidator dy = new AndRestrictionValidator(new RowDistance(colorBasedDisplacement));
    return new AndRestrictionValidator(new IsAnEnemy(), dx, dy);

  }
}
