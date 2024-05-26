package edu.austral.dissis.online.listeners.client;

import edu.austral.dissis.online.utils.Initial;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class ClientIdListener implements MessageListener<String> {
  private final Initial initial;

  public ClientIdListener(Initial initial) {
    this.initial = initial;
  }

  @Override
  public void handleMessage(@NotNull Message<String> message) {
    initial.setClientId(message.getPayload());
    System.out.println("Initial's clientID: " + initial.clientId());
  }
}
