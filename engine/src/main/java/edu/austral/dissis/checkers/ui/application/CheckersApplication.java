package edu.austral.dissis.checkers.ui.application;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.utils.enums.GameType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static edu.austral.dissis.chess.utils.AuxStaticMethods.setupGame;

public class CheckersApplication extends Application {
    private final Pair<GameEngine, ImageResolver> setup = setupGame(GameType.DEFAULT_CHECKERS);
    private final GameEngine gameEngine = setup.first();
    private final ImageResolver imageResolver = setup.second();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Checkers");
        GameView root = new GameView(gameEngine, imageResolver);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
