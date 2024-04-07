package edu.austral.dissis.chess.provider;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.GameType;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ChessPieceMapProvider {
  public Map<Position, Piece> provide(GameType type) {
    Map<Position, Piece> result = new HashMap<>();
    PieceProvider provider = new PieceProvider();
    if (type != GameType.DEFAULT) {
      return null;
    }
    // Pawns
    for (int j = 0; j < 8; j++) {
      result.put(new Position(1, j), provider.get(Color.WHITE, PieceType.PAWN));
      result.put(new Position(6, j), provider.get(Color.BLACK, PieceType.PAWN));
    }
    // Rooks
    result.put(new Position(0, 0), provider.get(Color.WHITE, PieceType.ROOK));
    result.put(new Position(0, 7), provider.get(Color.WHITE, PieceType.ROOK));
    result.put(new Position(7, 7), provider.get(Color.BLACK, PieceType.ROOK));
    result.put(new Position(7, 0), provider.get(Color.BLACK, PieceType.ROOK));
    // Knights
    result.put(new Position(0, 1), provider.get(Color.WHITE, PieceType.KNIGHT));
    result.put(new Position(0, 6), provider.get(Color.WHITE, PieceType.KNIGHT));
    result.put(new Position(7, 1), provider.get(Color.BLACK, PieceType.KNIGHT));
    result.put(new Position(7, 6), provider.get(Color.BLACK, PieceType.KNIGHT));
    // Bishops
    result.put(new Position(0, 2), provider.get(Color.WHITE, PieceType.BISHOP));
    result.put(new Position(0, 5), provider.get(Color.WHITE, PieceType.BISHOP));
    result.put(new Position(7, 2), provider.get(Color.BLACK, PieceType.BISHOP));
    result.put(new Position(7, 5), provider.get(Color.BLACK, PieceType.BISHOP));
    // King and Queen
    result.put(new Position(0, 3), provider.get(Color.WHITE, PieceType.QUEEN));
    result.put(new Position(0, 4), provider.get(Color.WHITE, PieceType.KING));
    result.put(new Position(7, 3), provider.get(Color.BLACK, PieceType.QUEEN));
    result.put(new Position(7, 4), provider.get(Color.BLACK, PieceType.KING));
    return result;
  }
}
