package edu.austral.dissis.online.listeners.messages;

import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class MoveResultListener implements MessageListener<MoveResult> {
    private final GameView gameView;
    public MoveResultListener(final GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void handleMessage(@NotNull Message<MoveResult> message) {
        gameView.handleMoveResult(message.getPayload());
    }
}
