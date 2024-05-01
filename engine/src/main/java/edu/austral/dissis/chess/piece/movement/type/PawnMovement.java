package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.utils.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.PiecePathValidator;
import java.awt.Color;

public class PawnMovement implements PieceMovement {

  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    //TODO: change to validator
    ChessPosition oldPos = move.from();
    ChessPosition newPos = move.to();
    int deltaY = newPos.getRow() - oldPos.getRow();
    Piece currentPawn = context.pieceAt(oldPos);
    boolean clearTile = context.pieceAt(newPos) == null;
    boolean movementByColor =
        currentPawn.getPieceColour() == Color.BLACK ? deltaY == -1 : deltaY == 1;
    return (oldPos.getColumn() == newPos.getColumn()
        && movementByColor
        && new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, VERTICAL)
        && clearTile);
  }
}
