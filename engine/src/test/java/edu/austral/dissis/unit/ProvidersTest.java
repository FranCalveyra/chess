package edu.austral.dissis.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.checkers.providers.CheckersPieceMapProvider;
import edu.austral.dissis.checkers.providers.CheckersPieceProvider;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ProvidersTest {
  CheckersPieceProvider pieceProvider = new CheckersPieceProvider();
  CheckersPieceMapProvider mapProvider = new CheckersPieceMapProvider();

  @Test
  public void shouldGetTwoMen() {
    Piece man1 = pieceProvider.provideCheckersPiece(Color.RED, CheckersType.MAN);
    Piece man2 = pieceProvider.provideCheckersPiece(Color.BLACK, CheckersType.MAN);
    assertEquals(man1.getType(), man2.getType());
    assertNotEquals(man1.getPieceColour(), man2.getPieceColour());
    chessPieceTypeDoNotWork();
  }

  @Test
  public void shouldGetTwoKings() {
    Piece king1 = pieceProvider.provideCheckersPiece(Color.RED, CheckersType.KING);
    Piece king2 = pieceProvider.provideCheckersPiece(Color.BLACK, CheckersType.KING);
    assertEquals(king1.getType(), king2.getType());
    assertNotEquals(king1.getPieceColour(), king2.getPieceColour());
    chessPieceTypeDoNotWork();
  }

  @Test
  public void shouldGetDefaultMap() {
    Map<BoardPosition, Piece> defaultMap = mapProvider.provide(GameType.DEFAULT_CHECKERS, 8, 8);
    assertEquals(24, defaultMap.size());
  }

  // Private stuff
  private void chessPieceTypeDoNotWork() {
    for (ChessPieceType type : ChessPieceType.values()) {
      assertThrows(
          IllegalStateException.class, () -> pieceProvider.provideCheckersPiece(Color.WHITE, type));
      assertThrows(
          IllegalStateException.class, () -> pieceProvider.provideCheckersPiece(Color.BLACK, type));
    }
  }
}
