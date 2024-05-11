package edu.austral.dissis.chess.ui.application;

import static edu.austral.dissis.common.utils.AuxStaticMethods.setupGame;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.common.utils.Pair;
import edu.austral.dissis.common.utils.enums.GameType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessApplication extends Application {
  private final Pair<GameEngine, ImageResolver> setup = setupGame(GameType.DEFAULT_CHESS);
  private final GameEngine gameEngine = setup.first();
  private final ImageResolver imageResolver = setup.second();

  @Override
  public void start(Stage stage) {
    stage.setTitle("Chess");
    GameView root = new GameView(gameEngine, imageResolver);
    stage.setScene(new Scene(root));
    stage.show();
  }
}
