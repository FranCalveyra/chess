package edu.austral.dissis.chess.rule.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

import java.awt.Color;

public class PawnFirstMoveRule implements PieceMovementRule {
  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    //Need pawn original position. Get if from default
    Piece piece = context.getActivePiecesAndPositions().get(oldPos);
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    boolean horizontalMove = oldX == newX;
    boolean verticalMove = Math.abs(newY - oldY) == 2;
    boolean originalPos = piece.getPieceColour() == Color.BLACK ?
      oldPos.getRow() == 6 : oldPos.getRow() == 1;
    return horizontalMove && verticalMove && originalPos;
  }
}
