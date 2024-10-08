package edu.austral.dissis.common.utils.result.playresult;

import java.awt.Color;

public class GameWon implements PlayResult {
  private final Color team;

  public GameWon(final Color team) {
    this.team = team;
  }

  public Color getWinner() {
    return team;
  }

  public String getMessage() {
    String strTeam = team == Color.WHITE ? "white" : "black";
    return "Game won by " + strTeam;
  }

  @Override
  public String getType() {
    return "GameWon";
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GameWon)) {
      return false;
    }
    return team.equals(((GameWon) obj).team);
  }
}
