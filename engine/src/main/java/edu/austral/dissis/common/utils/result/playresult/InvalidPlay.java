package edu.austral.dissis.common.utils.result.playresult;

public class InvalidPlay implements PlayResult {
  private final String reason;

  public InvalidPlay(final String reason) {
    this.reason = reason;
  }

  public String getMessage() {
    return reason;
  }

  @Override
  public String getType() {
    return "InvalidPlay";
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof InvalidPlay;
  }
}
