package edu.austral.dissis.chess.engine.exam

import edu.austral.dissis.chess.engine.updated.runners.ChessGameRunner
import edu.austral.dissis.chess.providers.GameProvider
import edu.austral.dissis.chess.test.game.GameTester
import edu.austral.dissis.common.ui.gameengine.BoardGameEngine
import edu.austral.dissis.common.utils.enums.GameType
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

class Exam {
    @TestFactory
    fun `required exam tests`(): Stream<DynamicTest> {
        return GameTester(ChessGameRunner(BoardGameEngine(GameProvider().provide(GameType.DEFAULT_CHESS)))).test()
    }
}
