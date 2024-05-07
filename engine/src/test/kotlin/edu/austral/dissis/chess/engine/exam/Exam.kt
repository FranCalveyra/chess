package edu.austral.dissis.chess.engine.exam

import edu.austral.dissis.chess.engine.updated.runners.ChessGameRunner
import edu.austral.dissis.chess.providers.ChessGameProvider
import edu.austral.dissis.chess.test.game.GameTester
import edu.austral.dissis.chess.utils.enums.GameType
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

class Exam {
    @TestFactory
    fun `required exam tests`(): Stream<DynamicTest> {
        return GameTester(ChessGameRunner(ChessGameProvider().provide(GameType.DEFAULT))).test()
    }
}
