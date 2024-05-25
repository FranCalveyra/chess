package edu.austral.dissis.chess.ui;

import static edu.austral.dissis.chess.gui.AdapterKt.createGameViewFrom;
import static edu.austral.dissis.common.utils.AuxStaticMethods.setupGame;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.common.utils.Pair;
import edu.austral.dissis.common.utils.enums.GameType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CapablancaMain {
  public static void main(String[] args) {
    Application.launch(Capablanca.class);
  }

  public static class Capablanca extends Application {
    private final Pair<GameEngine, ImageResolver> setup = setupGame(GameType.CAPABLANCA_CHESS);
    private final GameEngine gameEngine = setup.first();
    private final ImageResolver imageResolver = setup.second();

    @Override
    public void start(Stage stage) {
      stage.setTitle("Special Chess");
      GameView root = createGameViewFrom(gameEngine, imageResolver);
      stage.setScene(new Scene(root));
      stage.show();
    }
  }
}
