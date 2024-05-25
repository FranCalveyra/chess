package edu.austral.dissis.online.listeners.server;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class MoveListener implements MessageListener<Move> {
    private final GameEngine engine;
    public MoveListener(GameEngine engine) {
    this.engine = engine;
    }

    @Override
    public void handleMessage(@NotNull Message<Move> message) {
        engine.applyMove(message.getPayload());
    }
}
