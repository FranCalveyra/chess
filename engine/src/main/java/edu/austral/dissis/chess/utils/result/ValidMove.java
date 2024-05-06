package edu.austral.dissis.chess.utils.result;

public class ValidMove implements ChessMoveResult {
  @Override
  public String getMessage() {
    return "Valid Move";
  }

  @Override
  public String getType() {
    return "ValidMove";
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof ValidMove;
  }
}
