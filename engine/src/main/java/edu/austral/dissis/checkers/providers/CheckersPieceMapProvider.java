package edu.austral.dissis.checkers.providers;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CheckersPieceMapProvider {
  public Map<BoardPosition, Piece> provide(GameType type, int rows, int columns) {
    Map<BoardPosition, Piece> pieces = new HashMap<>();
    CheckersPieceProvider provider = new CheckersPieceProvider();
    // TODO: change
    if (type != GameType.DEFAULT_CHECKERS) {
      return null;
    }
    for (int j = 0; j < columns; j++) {
      if (j % 2 == 0) {
        pieces.put(
            new BoardPosition(rows - 2, j),
            provider.provideCheckersPiece(Color.BLACK, CheckersType.MAN));
        pieces.put(
            new BoardPosition(0, j), provider.provideCheckersPiece(Color.RED, CheckersType.MAN));
        pieces.put(
            new BoardPosition(2, j), provider.provideCheckersPiece(Color.RED, CheckersType.MAN));
      } else {
        pieces.put(
            new BoardPosition(1, j), provider.provideCheckersPiece(Color.RED, CheckersType.MAN));
        pieces.put(
            new BoardPosition(rows - 1, j),
            provider.provideCheckersPiece(Color.BLACK, CheckersType.MAN));
        pieces.put(
            new BoardPosition(rows - 3, j),
            provider.provideCheckersPiece(Color.BLACK, CheckersType.MAN));
      }
    }
    return pieces;
  }
}
