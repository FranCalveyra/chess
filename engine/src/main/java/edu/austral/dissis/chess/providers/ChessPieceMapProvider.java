package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.utils.GameType;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ChessPieceMapProvider {
  public Map<ChessPosition, Piece> provide(GameType type) {
    Map<ChessPosition, Piece> result = new HashMap<>();
    PieceProvider provider = new PieceProvider();
    if (type != GameType.DEFAULT) {
      return null;
    }
    // Pawns
    for (int j = 0; j < 8; j++) {
      result.put(new ChessPosition(1, j), provider.provide(Color.WHITE, PieceType.PAWN));
      result.put(new ChessPosition(6, j), provider.provide(Color.BLACK, PieceType.PAWN));
    }
    // Rooks
    result.put(new ChessPosition(0, 0), provider.provide(Color.WHITE, PieceType.ROOK));
    result.put(new ChessPosition(0, 7), provider.provide(Color.WHITE, PieceType.ROOK));
    result.put(new ChessPosition(7, 7), provider.provide(Color.BLACK, PieceType.ROOK));
    result.put(new ChessPosition(7, 0), provider.provide(Color.BLACK, PieceType.ROOK));
    // Knights
    result.put(new ChessPosition(0, 1), provider.provide(Color.WHITE, PieceType.KNIGHT));
    result.put(new ChessPosition(0, 6), provider.provide(Color.WHITE, PieceType.KNIGHT));
    result.put(new ChessPosition(7, 1), provider.provide(Color.BLACK, PieceType.KNIGHT));
    result.put(new ChessPosition(7, 6), provider.provide(Color.BLACK, PieceType.KNIGHT));
    // Bishops
    result.put(new ChessPosition(0, 2), provider.provide(Color.WHITE, PieceType.BISHOP));
    result.put(new ChessPosition(0, 5), provider.provide(Color.WHITE, PieceType.BISHOP));
    result.put(new ChessPosition(7, 2), provider.provide(Color.BLACK, PieceType.BISHOP));
    result.put(new ChessPosition(7, 5), provider.provide(Color.BLACK, PieceType.BISHOP));
    // King and Queen
    result.put(new ChessPosition(0, 3), provider.provide(Color.WHITE, PieceType.QUEEN));
    result.put(new ChessPosition(0, 4), provider.provide(Color.WHITE, PieceType.KING));
    result.put(new ChessPosition(7, 3), provider.provide(Color.BLACK, PieceType.QUEEN));
    result.put(new ChessPosition(7, 4), provider.provide(Color.BLACK, PieceType.KING));
    return result;
  }
}
