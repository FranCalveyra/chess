package edu.austral.dissis.chess.ui;

import edu.austral.dissis.chess.gui.CachedImageResolver;
import edu.austral.dissis.chess.gui.DefaultImageResolver;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.utils.type.GameType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpecialCaseToShow extends Application {
  private final GameEngine gameEngine =
      new ChessGameEngine(new GameProvider().provide(GameType.SPECIAL));
  private final ImageResolver imageResolver = new CachedImageResolver(new DefaultImageResolver());

  @Override
  public void start(Stage stage) {
    stage.setTitle("Chess");
    GameView root = new GameView(gameEngine, imageResolver);
    stage.setScene(new Scene(root));
    stage.show();
  }
}
