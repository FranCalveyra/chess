package edu.austral.dissis.chess.utils.result;

import edu.austral.dissis.common.utils.result.playresult.PlayResult;
import java.awt.Color;

public class CheckState implements PlayResult {
  // Not really used, may as well delete it

  Color team;

  public CheckState(final Color team) {
    this.team = team;
  }

  @Override
  public String getMessage() {
    return "Team: %s".formatted(team) + " is in check";
  }

  @Override
  public String getType() {
    return "CheckState";
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof CheckState)) {
      return false;
    }
    return team.equals(((CheckState) obj).team);
  }
}
