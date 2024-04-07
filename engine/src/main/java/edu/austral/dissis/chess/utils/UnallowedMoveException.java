package edu.austral.dissis.chess.utils;

public class UnallowedMoveException extends Exception {
  private final String message;

  public UnallowedMoveException(String message) {
    super(message);
    this.message = message;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj.getClass() != UnallowedMoveException.class) {
      return false;
    }
    return message.equals(((UnallowedMoveException) obj).message);
  }
}
