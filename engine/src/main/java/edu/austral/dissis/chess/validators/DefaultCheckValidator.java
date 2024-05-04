package edu.austral.dissis.chess.validators;

import static edu.austral.dissis.chess.piece.PieceType.KING;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.awt.Color;
import java.util.Map;

public class DefaultCheckValidator {

  public boolean isInCheck(Board context, Color team, ChessPosition toPos) {
    Piece piece = context.pieceAt(toPos);
    if (notAnEnemy(piece, team)) {
      return false; // Makes no sense to check if the king's in check against
      // your own team or an empty space
    }
    ChessPosition kingPosition = fetchKingPosition(context.getPiecesAndPositions(), team);
    return kingPosition != null && piece.isValidMove(new ChessMove(toPos, kingPosition), context);
  }

  private boolean notAnEnemy(Piece piece, Color team) {
    return piece == null || piece.getPieceColour() == team;
  }

  private ChessPosition fetchKingPosition(Map<ChessPosition, Piece> pieces, Color team) {
    for (Map.Entry<ChessPosition, Piece> entry : pieces.entrySet()) {
      Piece piece = entry.getValue();
      if (piece != null && piece.getPieceColour() == team && piece.getType() == KING) {
        return entry.getKey();
      }
    }
    return null;
  }
}
