package edu.austral.dissis.chess.engine.cases;

import static edu.austral.dissis.common.utils.AuxStaticMethods.makeMove;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.dissis.common.utils.result.gameresult.BoardGameResult;
import edu.austral.dissis.common.utils.result.playresult.PieceTaken;
import edu.austral.dissis.common.utils.result.playresult.ValidPlay;
import org.junit.jupiter.api.Test;

public class CapablancaTest {
  private final BoardGame game = new GameProvider().provide(GameType.CAPABLANCA_CHESS);

  @Test
  public void size() {
    assertEquals(8, game.getBoard().getRows());
    assertEquals(10, game.getBoard().getColumns());
  }

  @Test
  public void archbishop() {
    BoardGameResult result = makeMove(game, "c1 -> d3");
    assertEquals(new ValidPlay(), result.moveResult());
    result = makeMove(result.game(), "c8 -> d6");
    assertEquals(new ValidPlay(), result.moveResult());
    result = makeMove(result.game(), "d3 -> b4");
    assertEquals(new ValidPlay(), result.moveResult());
    result = makeMove(result.game(), "d6 -> b4");
    assertEquals(new PieceTaken(), result.moveResult());
  }

  @Test
  public void chancellor() {
    BoardGameResult result = makeMove(game, "h1 -> g3");
    assertEquals(new ValidPlay(), result.moveResult());
    result = makeMove(result.game(), "h8 -> g6");
    assertEquals(new ValidPlay(), result.moveResult());
    result = makeMove(result.game(), "g3 -> g6");
    assertEquals(new PieceTaken(), result.moveResult());
  }
}
