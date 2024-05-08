package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.providers.ChessGameProvider;
import edu.austral.dissis.chess.utils.enums.GameType;

public class ClassicTest extends ChessTest {
  public ClassicTest() {
    super(new ChessGameProvider().provide(GameType.DEFAULT));
  }
}
