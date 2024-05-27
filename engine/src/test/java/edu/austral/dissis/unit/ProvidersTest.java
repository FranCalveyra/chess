package edu.austral.dissis.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.checkers.providers.CheckersPieceMapProvider;
import edu.austral.dissis.checkers.providers.CheckersPieceProvider;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.providers.ChessPieceMapProvider;
import edu.austral.dissis.chess.providers.ChessPieceProvider;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ProvidersTest {
  CheckersPieceProvider pieceProvider = new CheckersPieceProvider();
  CheckersPieceMapProvider mapProvider = new CheckersPieceMapProvider();
  ChessPieceProvider chessPieceProvider = new ChessPieceProvider();

  @Test
  public void shouldGetTwoMen() {
    Piece man1 = pieceProvider.provideCheckersPiece(Color.RED, CheckersType.MAN);
    Piece man2 = pieceProvider.provideCheckersPiece(Color.BLACK, CheckersType.MAN);
    assertEquals(man1.getType(), man2.getType());
    assertNotEquals(man1.getPieceColour(), man2.getPieceColour());
    assertChessPieceTypeDoNotWork();
  }

  @Test
  public void shouldGetTwoKings() {
    Piece king1 = pieceProvider.provideCheckersPiece(Color.RED, CheckersType.KING);
    Piece king2 = pieceProvider.provideCheckersPiece(Color.BLACK, CheckersType.KING);
    assertEquals(king1.getType(), king2.getType());
    assertNotEquals(king1.getPieceColour(), king2.getPieceColour());
    assertChessPieceTypeDoNotWork();
  }

  @Test
  public void shouldGetDefaultMap() {
    Map<BoardPosition, Piece> defaultMap = mapProvider.provide(GameType.DEFAULT_CHECKERS, 8, 8);
    assertEquals(24, defaultMap.size());
    Map<BoardPosition, Piece> nullMap = mapProvider.provide(GameType.DEFAULT_CHESS, 8, 8);
    assertNull(nullMap);
  }

  @Test
  public void shouldGetSpecialChessMap() {
    Map<BoardPosition, Piece> specialMap =
        new ChessPieceMapProvider().provide(GameType.SPECIAL_CHESS, 9, 12);
    assertEquals(44, specialMap.size());

    Piece blackChancellor = chessPieceProvider.provide(Color.BLACK, ChessPieceType.CHANCELLOR);
    assertTrue(isPieceLikeThisPresent(blackChancellor, specialMap));

    Piece blackArchbishop = chessPieceProvider.provide(Color.BLACK, ChessPieceType.ARCHBISHOP);
    assertTrue(isPieceLikeThisPresent(blackArchbishop, specialMap));

    Piece whiteChancellor = chessPieceProvider.provide(Color.WHITE, ChessPieceType.CHANCELLOR);
    assertTrue(isPieceLikeThisPresent(whiteChancellor, specialMap));

    Piece whiteArchbishop = chessPieceProvider.provide(Color.WHITE, ChessPieceType.ARCHBISHOP);
    assertTrue(isPieceLikeThisPresent(whiteArchbishop, specialMap));
  }

  @Test
  public void canNotGetCheckersPiecesFromChessPieceProvider() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          chessPieceProvider.provide(Color.BLACK, CheckersType.KING);
        });
  }

  // Private stuff
  private void assertChessPieceTypeDoNotWork() {
    for (ChessPieceType type : ChessPieceType.values()) {
      assertThrows(
          IllegalStateException.class, () -> pieceProvider.provideCheckersPiece(Color.WHITE, type));
      assertThrows(
          IllegalStateException.class, () -> pieceProvider.provideCheckersPiece(Color.BLACK, type));
    }
  }

  private boolean isPieceLikeThisPresent(Piece piece, Map<BoardPosition, Piece> map) {
    return map.values().stream()
        .anyMatch(
            mapPiece ->
                mapPiece.getPieceColour().equals(piece.getPieceColour())
                    && mapPiece.getType().equals(piece.getType()));
  }
}
