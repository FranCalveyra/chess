package edu.austral.dissis.chess.ui;

import edu.austral.dissis.chess.gui.CachedImageResolver;
import edu.austral.dissis.chess.gui.DefaultImageResolver;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.utils.type.GameType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static edu.austral.dissis.chess.utils.AuxStaticMethods.setupGame;

public class Capablanca extends Application {
  private final Pair<GameEngine, ImageResolver> setup = setupGame(GameType.CAPABLANCA);
  private final GameEngine gameEngine = setup.first();
  private final ImageResolver imageResolver = setup.second();

  @Override
  public void start(Stage stage) {
    stage.setTitle("Special Chess");
    GameView root = new GameView(gameEngine, imageResolver);
    stage.setScene(new Scene(root));
    stage.show();
  }
}
