package edu.austral.dissis.common.utils.result;

public class PieceTaken implements PlayResult {
  @Override
  public String getMessage() {
    return "Piece taken";
  }

  @Override
  public String getType() {
    return "PieceTaken";
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof PieceTaken;
  }
}
