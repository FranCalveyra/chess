package edu.austral.dissis.chess.ui;

import edu.austral.dissis.chess.gui.CachedImageResolver;
import edu.austral.dissis.chess.gui.DefaultImageResolver;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.chess.gui.SimpleGameEngine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessApplication extends Application {
  private final GameEngine gameEngine = new SimpleGameEngine();
  private final ImageResolver imageResolver = new CachedImageResolver(new DefaultImageResolver());

  @Override
  public void start(Stage stage) {
    stage.setTitle("Chess");
    stage.setResizable(false);
    GameView rootView = new GameView(gameEngine, imageResolver);
    Scene scene = new Scene(rootView);
    stage.setScene(scene);
    stage.show();
  }
}
