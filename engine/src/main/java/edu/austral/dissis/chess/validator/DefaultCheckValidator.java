package edu.austral.dissis.chess.validator;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.Map.Entry;

public class DefaultCheckValidator {

  public boolean kingInCheck(Board context, Color team, Position toPos, Piece piece) {
    if (piece == null || piece.getPieceColour() == team) {
      return false; // Makes no sense to check if the king's in check against
      // your own team or an empty space
    }
    Position kingPosition = getKingPosition(context, team);
    return kingPosition != null && piece.checkValidMove(toPos, kingPosition, context);
  }

  private Position getKingPosition(Board context, Color team) {
    for (Entry<Position, Piece> entry : context.getActivePiecesAndPositions().entrySet()) {
      Piece currentPiece = entry.getValue();
      if (currentPiece.getType() == PieceType.KING && currentPiece.getPieceColour() == team) {
        return entry.getKey();
      }
    }
    return null;
  }
}
