package edu.austral.dissis.online.utils;

public class Initial {
  private String clientId;

  public Initial(String clientId) {
    this.clientId = clientId;
  }

  public String clientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }
}
