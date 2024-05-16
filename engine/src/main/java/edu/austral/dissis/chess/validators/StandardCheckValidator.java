package edu.austral.dissis.chess.validators;

import static edu.austral.dissis.chess.piece.movement.type.ChessPieceType.KING;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import java.awt.Color;
import java.util.Map;

public class StandardCheckValidator {

  public boolean isInCheck(Board context, Color team, BoardPosition toPos) {
    Piece piece = context.pieceAt(toPos);
    if (notAnEnemy(piece, team)) {
      return false; // Makes no sense to check if the king's in check against
      // your own team or an empty space
    }
    BoardPosition kingPosition = fetchKingPosition(context.getPiecesAndPositions(), team);
    return kingPosition != null && piece.isValidMove(new GameMove(toPos, kingPosition), context);
  }

  private boolean notAnEnemy(Piece piece, Color team) {
    return piece == null || piece.getPieceColour() == team;
  }

  private BoardPosition fetchKingPosition(Map<BoardPosition, Piece> pieces, Color team) {
    for (Map.Entry<BoardPosition, Piece> entry : pieces.entrySet()) {
      Piece piece = entry.getValue();
      if (piece != null && piece.getPieceColour() == team && piece.getType() == KING) {
        return entry.getKey();
      }
    }
    return null;
  }
}
