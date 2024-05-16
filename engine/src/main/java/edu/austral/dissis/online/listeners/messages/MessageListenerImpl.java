package edu.austral.dissis.online.listeners.messages;

import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class MessageListenerImpl<P> implements MessageListener<P> {
  @Override
  public void handleMessage(@NotNull Message<P> message) {}
}
