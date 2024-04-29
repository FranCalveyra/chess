package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.utils.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.PiecePathValidator;

public class PawnFirstMove implements PieceMovement {
  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    Piece piece = context.getPiecesAndPositions().get(oldPos);
    boolean notHorizontalMove = oldPos.getColumn() == newPos.getColumn();
    boolean verticalMove = Math.abs(newPos.getRow() - oldPos.getRow())  == 2;
    Piece lastPiece = context.pieceAt(newPos);
    boolean clearTile = lastPiece == null;
    return notHorizontalMove
        && verticalMove
        && !piece.hasMoved()
        && new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, VERTICAL)
        && clearTile;
  }
}
