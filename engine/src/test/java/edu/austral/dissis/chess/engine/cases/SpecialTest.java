package edu.austral.dissis.chess.engine.cases;

import static edu.austral.dissis.common.utils.AuxStaticMethods.makeMove;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.dissis.chess.engine.BoardGame;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.utils.result.BoardGameResult;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.dissis.common.utils.result.PieceTaken;
import edu.austral.dissis.common.utils.result.ValidPlay;
import java.awt.Color;
import org.junit.jupiter.api.Test;

public class SpecialTest extends CapablancaTest {

  private final BoardGame game = new GameProvider().provide(GameType.SPECIAL_CHESS);

  @Test
  public void size() {
    assertEquals(9, game.getBoard().getRows());
    assertEquals(12, game.getBoard().getColumns());
  }

  @Test
  public void archbishop() {
    assertEquals(Color.WHITE, game.getCurrentTurn());
    BoardGameResult result = makeMove(game, "c1 -> d3");
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(Color.BLACK, result.game().getCurrentTurn());

    result = makeMove(result.game(), "c9 -> d7");
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(Color.BLACK, result.game().getCurrentTurn());

    result = makeMove(result.game(), "c8 -> c6");
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(Color.WHITE, result.game().getCurrentTurn());

    result = makeMove(result.game(), "d3 -> b5");
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(Color.WHITE, result.game().getCurrentTurn());

    result = makeMove(result.game(), "b5 -> c6");
    assertEquals(Color.WHITE, result.game().getCurrentTurn());
    assertEquals(new PieceTaken(), result.moveResult());
  }

  @Test
  public void chancellor() {
    assertEquals(Color.WHITE, game.getCurrentTurn());
    BoardGameResult result = makeMove(game, "j1 -> i3");
    assertEquals(Color.BLACK, result.game().getCurrentTurn());
    assertEquals(new ValidPlay(), result.moveResult());

    result = makeMove(result.game(), "j9 -> i7");
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(Color.BLACK, result.game().getCurrentTurn());

    result = makeMove(result.game(), "i7 -> i5");
    assertEquals(new ValidPlay(), result.moveResult());
    assertEquals(Color.WHITE, result.game().getCurrentTurn());

    result = makeMove(result.game(), "i3 -> i5");
    assertEquals(Color.WHITE, result.game().getCurrentTurn());
    assertEquals(new PieceTaken(), result.moveResult());
  }
}
