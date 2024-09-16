package edu.austral.dissis.online.listeners.server;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getClassName;
import static edu.austral.dissis.common.utils.AuxStaticMethods.getPlayerColor;

import edu.austral.dissis.chess.gui.InvalidMove;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.common.ui.gameengine.BoardGameEngine;
import edu.austral.dissis.online.utils.MovePayload;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import edu.austral.ingsis.clientserver.Server;
import java.awt.Color;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class MoveListener implements MessageListener<MovePayload> {
  private final BoardGameEngine engine;
  private Server server;
  private final Map<String, Color> colors;

  public MoveListener(BoardGameEngine engine, Map<String, Color> colors) {
    this.engine = engine;
    this.colors = colors;
  }

  @Override
  public void handleMessage(@NotNull Message<MovePayload> message) {
    if (server == null) {
      return;
    }
    boolean notTurn = !isPlayersTurn(message.getPayload().id());
    boolean notRegisteredPlayer = !colors.containsKey(message.getPayload().id());
    if (notTurn || notRegisteredPlayer) {
      server.broadcast(new Message<>("InvalidMove", new InvalidMove("Not your turn")));
      return;
    }
    MoveResult result = engine.applyMove(message.getPayload().move());
    boolean invalid = result instanceof InvalidMove;
    if (invalid) {
      server.broadcast(new Message<>("InvalidMove", result));
      return;
    }
    String className = getClassName(result);
    server.broadcast(new Message<>(className, result));
  }

  private boolean isPlayersTurn(String id) {
    return engine.getCurrentTurn() == getPlayerColor(colors.get(id));
  }

  public void setServer(Server server) {
    this.server = server;
  }
}
