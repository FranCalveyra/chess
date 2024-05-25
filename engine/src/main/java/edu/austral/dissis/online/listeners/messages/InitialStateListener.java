package edu.austral.dissis.online.listeners.messages;

import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.InitialState;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

public class InitialStateListener implements MessageListener<InitialState> {
    private final GameView gameView;
    public InitialStateListener(final GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void handleMessage(@NotNull Message<InitialState> message) {
        Platform.runLater(()->gameView.handleInitialState(message.getPayload()));
    }
}
