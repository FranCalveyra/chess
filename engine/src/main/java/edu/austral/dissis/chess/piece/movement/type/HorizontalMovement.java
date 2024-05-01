package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.utils.MoveType.HORIZONTAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.PiecePathValidator;

public class HorizontalMovement implements PieceMovement {
  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    //TODO: change to validator
    ChessPosition oldPos = move.from();
    ChessPosition newPos = move.to();
    boolean validMove =
        (oldPos.getColumn() != newPos.getColumn()) && (oldPos.getRow() == newPos.getRow());
    if (!validMove) {
      return false;
    }
    boolean isNotTeammate =
        context.pieceAt(newPos) == null
            || context.pieceAt(oldPos).getPieceColour() != context.pieceAt(newPos).getPieceColour();
    return new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, HORIZONTAL)
        && isNotTeammate;
  }
}
