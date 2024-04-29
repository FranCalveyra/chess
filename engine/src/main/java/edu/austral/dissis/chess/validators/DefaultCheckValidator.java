package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.awt.Color;

public class DefaultCheckValidator {

  public boolean isInCheck(Board context, Color team, ChessPosition toPos) {
    Piece piece = context.pieceAt(toPos);
    if (notAnEnemy(piece, team)) {
      return false; // Makes no sense to check if the king's in check against
      // your own team or an empty space
    }
    ChessPosition kingPosition = context.getKingPosition(team);
    return kingPosition != null && piece.isValidMove(toPos, kingPosition, context);
  }

  private boolean notAnEnemy(Piece piece, Color team) {
    return piece == null || piece.getPieceColour() == team;
  }
}
