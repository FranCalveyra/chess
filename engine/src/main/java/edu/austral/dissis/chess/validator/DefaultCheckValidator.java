package edu.austral.dissis.chess.validator;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.Map;

public class DefaultCheckValidator {

  public boolean kingInCheck(Board context, Color team, Position toPos, Piece piece) {
    if (piece == null) return false;
    if (piece.getPieceColour() == team) {
      return false; // Makes no sense to check if the king's in check against your own team
    }
    Map.Entry<Position, Piece> kingEntry = getKing(context, team);
    assert kingEntry != null;
    return piece.checkValidMove(toPos, kingEntry.getKey(), context);
  }

  private Map.Entry<Position, Piece> getKing(Board context, Color team) {
    for (Map.Entry<Position, Piece> entry : context.getActivePiecesAndPositions().entrySet()) {
      Piece currentPiece = entry.getValue();
      if (currentPiece.getType() == PieceType.KING && currentPiece.getPieceColour() == team) {
        return entry;
      }
    }
    return null;
  }
}
