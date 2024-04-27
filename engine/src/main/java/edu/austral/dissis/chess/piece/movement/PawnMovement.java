package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.utils.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validator.PiecePathValidator;
import java.awt.Color;

public class PawnMovement implements PieceMovement {

  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    int deltaY = newY - oldY;
    Piece currentPawn = context.pieceAt(oldPos);
    boolean movementByColor =
        currentPawn.getPieceColour() == Color.BLACK ? deltaY == -1 : deltaY == 1;
    return (oldX == newX
            && (movementByColor || new PawnFirstMove().isValidMove(oldPos, newPos, context)))
        && new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, VERTICAL);
  }
}
