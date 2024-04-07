package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;

import java.awt.*;

public class PawnMovement implements PieceMovement {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    int deltaY = newY - oldY;
    Piece currentPawn = context.pieceAt(oldPos);
    boolean movementByColor = currentPawn.getPieceColour() == Color.BLACK ? deltaY == -1 : deltaY == 1;
    return (oldX == newX
            && (movementByColor
                || new PawnFirstMove().isValidMove(oldPos, newPos, context)))
        && isNotPieceBetween(oldPos, newPos, context);
  }
}
