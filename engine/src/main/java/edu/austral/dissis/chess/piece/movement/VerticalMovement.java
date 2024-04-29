package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.utils.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.PiecePathValidator;

public class VerticalMovement implements PieceMovement {
  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    boolean isNotTeammate =
        context.pieceAt(newPos) == null
            || context.pieceAt(oldPos).getPieceColour() != context.pieceAt(newPos).getPieceColour();
    return oldPos.getColumn() == newPos.getColumn()
        && oldPos.getRow() != newPos.getRow()
        && new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, VERTICAL)
        && isNotTeammate;
  }
}
