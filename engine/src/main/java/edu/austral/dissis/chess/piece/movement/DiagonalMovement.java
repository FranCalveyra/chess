package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.utils.MoveType.DIAGONAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.validator.PiecePathValidator;

public class DiagonalMovement implements PieceMovement {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    int deltaX = Math.abs(newPos.getColumn() - oldPos.getColumn());
    int deltaY = Math.abs(newPos.getRow() - oldPos.getRow());
    boolean moveCondition = deltaX == deltaY;
    if (!moveCondition) {
      return false;
    }
    return new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, DIAGONAL);
    // They have to move the same amount in both coordinates.
    // If not, movement should resemble to a Knight movement.
  }
}
