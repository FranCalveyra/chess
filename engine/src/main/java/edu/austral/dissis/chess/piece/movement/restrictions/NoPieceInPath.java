package edu.austral.dissis.chess.piece.movement.restrictions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.MoveType;
import edu.austral.dissis.chess.validators.PiecePathValidator;

public class NoPieceInPath implements MovementRestriction {
  private final MoveType moveType;
  private final PiecePathValidator pathValidator;

  public NoPieceInPath(MoveType moveType) {
    this.moveType = moveType;
    this.pathValidator = new PiecePathValidator();
  }

  @Override
  public boolean isValidRestriction(ChessMove move, Board context) {
    return pathValidator.isNoPieceBetween(move.from(), move.to(), context, moveType);
  }
}
