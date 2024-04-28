package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.awt.Color;
import java.util.Map.Entry;

public class DefaultCheckValidator {

  public boolean isInCheck(Board context, Color team, ChessPosition toPos, Piece piece) {
    if (piece == null || piece.getPieceColour() == team) {
      return false; // Makes no sense to check if the king's in check against
      // your own team or an empty space
    }
    ChessPosition kingChessPosition = getKingPosition(context, team);
      return kingChessPosition != null && piece.isValidMove(toPos, kingChessPosition, context);
  }

  private ChessPosition getKingPosition(Board context, Color team) {
    for (Entry<ChessPosition, Piece> entry : context.getPiecesAndPositions().entrySet()) {
      Piece currentPiece = entry.getValue();
      if (currentPiece == null) {
        continue;
      }
      if (currentPiece.getType() == PieceType.KING && currentPiece.getPieceColour() == team) {
        return entry.getKey();
      }
    }
    return null;
  }
}
