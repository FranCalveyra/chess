package edu.austral.dissis.chess.engine.cases;

import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.utils.enums.GameType;

public class ClassicTest extends ChessTest {
  public ClassicTest() {
    super(new GameProvider().provide(GameType.DEFAULT_CHESS));
  }
}
