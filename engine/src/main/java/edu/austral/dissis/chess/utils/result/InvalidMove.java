package edu.austral.dissis.chess.utils.result;

public final class InvalidMove implements ChessMoveResult {
  private final String reason;

  public InvalidMove(final String reason) {
    this.reason = reason;
  }

  public String getMessage() {
    return "Invalid move due to: " + reason;
  }

  @Override
  public String getType() {
    return "InvalidMove";
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof InvalidMove;
  }
}
