package edu.austral.dissis.chess.ui;

import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.utils.GameType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessApplication extends Application {
  private final GameEngine gameEngine = new ChessGameEngine(new GameProvider().provide(GameType.DEFAULT));
  private final ImageResolver imageResolver = new CachedImageResolver(new DefaultImageResolver());

    @Override
  public void start(Stage stage){
    try{
      stage.setTitle("Chess");
      GameView root = new GameView(gameEngine, imageResolver);
      stage.setScene(new Scene(root));
      stage.show();
    }
    catch(Exception e){
      System.out.println("Error: " + e);
    }
  }

}
