package edu.austral.dissis.chess.utils;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import java.awt.Color;
import java.util.Map;

public class CheckValidator {

  public boolean kingInCheck(Board context, Color team, Map.Entry<Position, Piece> entry) {
    if (entry.getValue().getPieceColour() == team) {
      return false; // Makes no sense to check if the king's in check against your own team
    }
    Map.Entry<Position, Piece> kingEntry = getKing(context, team);
    Piece pieceThatChecks = entry.getValue();
    assert kingEntry != null;
    return pieceThatChecks.checkValidMove(entry.getKey(), kingEntry.getKey(), context);
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
