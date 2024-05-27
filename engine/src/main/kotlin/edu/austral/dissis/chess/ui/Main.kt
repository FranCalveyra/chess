package edu.austral.dissis.chess.ui

import edu.austral.dissis.chess.gui.createGameViewFrom
import edu.austral.dissis.common.utils.AuxStaticMethods.setupGame
import edu.austral.dissis.common.utils.enums.GameType
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage

fun main() {
    launch(ChessGameApplication::class.java)
}

class ChessGameApplication : Application() {
//    private val gameEngine = SimpleGameEngine()
    private val setup = setupGame(GameType.SPECIAL_CHESS)
    private val gameEngine = setup.first()
    private val resolver = setup.second()

    companion object {
        const val GAME_TITLE = "Chess"
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = GAME_TITLE

        val root = createGameViewFrom(gameEngine, resolver)

        primaryStage.scene = Scene(root)

        primaryStage.show()
    }
}
