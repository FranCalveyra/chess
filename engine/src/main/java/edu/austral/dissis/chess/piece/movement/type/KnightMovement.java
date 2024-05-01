package edu.austral.dissis.chess.piece.movement.type;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.restrictions.AbsColumnDistance;
import edu.austral.dissis.chess.piece.movement.restrictions.AbsRowDistance;
import edu.austral.dissis.chess.piece.movement.restrictions.MovementRestriction;
import edu.austral.dissis.chess.piece.movement.restrictions.NoTeammateAtDestination;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;
import edu.austral.dissis.chess.validators.OrRestrictionValidator;
import edu.austral.dissis.chess.validators.PreMovementValidator;

public class KnightMovement implements PieceMovement {
  MovementRestrictionValidator validator;
  public KnightMovement() {
    this.validator = getKnightRestrictions();
  }

  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    return validator.isValidMove(move,context);
  }


  private MovementRestrictionValidator getKnightRestrictions(){
    MovementRestrictionValidator c1 = new OrRestrictionValidator(new AbsColumnDistance(1),null,null);
    MovementRestrictionValidator c2 = new OrRestrictionValidator(new AbsColumnDistance(2),null,null);
    MovementRestrictionValidator r1 = new OrRestrictionValidator(new AbsRowDistance(1),null,null);
    MovementRestrictionValidator r2 = new OrRestrictionValidator(new AbsRowDistance(2),null,null);

    MovementRestrictionValidator and1 = new AndRestrictionValidator(null, c1,r2);
    MovementRestrictionValidator and2 = new AndRestrictionValidator(null, c2,r1);
    MovementRestrictionValidator left = new OrRestrictionValidator(null, and2, and1);
    MovementRestrictionValidator right = new OrRestrictionValidator(new NoTeammateAtDestination(), null,null);
    return new AndRestrictionValidator(null,left,right);
  }
}
