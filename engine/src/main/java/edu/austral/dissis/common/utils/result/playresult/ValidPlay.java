package edu.austral.dissis.common.utils.result.playresult;

public class ValidPlay implements PlayResult {
  @Override
  public String getMessage() {
    return "Valid Move";
  }

  @Override
  public String getType() {
    return "ValidPlay";
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof ValidPlay;
  }
}
