package edu.austral.dissis.chess.engine.exam;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.engine.updated.runners.ChessGameRunner;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.test.game.GameTester;
import edu.austral.dissis.chess.utils.GameType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class ChessTestCase {
  // Literal copy from Exam, can be perfectly deleted
  // Setup
  private final ChessGame game = new GameProvider().provide(GameType.DEFAULT);
  private final ChessGameRunner gameRunner = new ChessGameRunner(game);
  private final GameTester gameTester = new GameTester(gameRunner);

  @TestFactory
  public Stream<DynamicTest> examTests() {
    return gameTester.test();
  }

  @TestFactory
  public Stream<DynamicTest> debug() {
    return gameTester.debug("mate_fools.md");
  }
}
