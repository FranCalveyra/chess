package edu.austral.dissis.online.listeners.server;

import edu.austral.dissis.chess.gui.*;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

public class SimpleEventListener implements GameEventListener {
    private final GameEngine gameEngine;
    private final GameView gameView;

    public SimpleEventListener(GameEngine gameEngine, GameView root) {
        this.gameEngine = gameEngine;
        this.gameView = root;
    }

    @Override
    public void handleMove(@NotNull Move move) {
        MoveResult result = gameEngine.applyMove(move);
        Platform.runLater(()->gameView.handleMoveResult(result));

    }

    @Override
    public void handleUndo() {
        NewGameState undoResult = gameEngine.undo();
        Platform.runLater(()->gameView.handleMoveResult(undoResult));
    }

    @Override
    public void handleRedo() {
        NewGameState redoResult = gameEngine.redo();
        Platform.runLater(()->gameView.handleMoveResult(redoResult));
    }
}
