package edu.austral.dissis.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.austral.dissis.chess.gui.Move;
import edu.austral.dissis.chess.gui.Position;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.providers.ChessPieceProvider;
import edu.austral.dissis.chess.rules.winconds.CheckMate;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.board.MapBoard;
import edu.austral.dissis.common.rules.winconds.Extinction;
import edu.austral.dissis.common.rules.winconds.NoAvailableMoves;
import edu.austral.dissis.common.rules.winconds.WinCondition;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.online.utils.Initial;
import edu.austral.dissis.online.utils.MovePayload;
import java.awt.Color;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class FillerTest {
  ChessPieceProvider pieceProvider = new ChessPieceProvider();
  Board board =
      new MapBoard(
          Map.of(new BoardPosition(0, 0), pieceProvider.provide(Color.BLACK, ChessPieceType.ROOK)),
          1,
          1);

  @Test
  public void boardToStringTest() {
    assertEquals("[BL R]\n", board.toString());
  }

  @Test
  public void initialTest() {
    Initial initial = new Initial("2");
    assertEquals("2", initial.clientId());
    initial.setClientId("3");
    assertEquals("3", initial.clientId());
  }

  @Test
  public void movePayloadTest() {
    MovePayload payload = new MovePayload("2", new Move(new Position(2, 2), new Position(2, 3)));
    assertEquals("2", payload.id());
    assertEquals(2, payload.move().getFrom().getRow());
    assertEquals(2, payload.move().getFrom().getColumn());
    assertEquals(2, payload.move().getTo().getRow());
    assertEquals(3, payload.move().getTo().getColumn());
  }

  @Test
  public void winConditionTest() {
    WinCondition extinction = new Extinction(Color.WHITE);
    WinCondition noAvailableMoves = new NoAvailableMoves(Color.BLACK);
    assertEquals(Color.WHITE, extinction.getTeam());
    assertEquals(Color.BLACK, noAvailableMoves.getTeam());
    assertEquals(Color.BLACK, new CheckMate(Color.BLACK).getTeam());
    Board testBoard = new MapBoard(Map.of());
    assertTrue(noAvailableMoves.isValidRule(testBoard));
  }

  @Test
  public void voidMethodsTest() {}
}
