package edu.austral.dissis.chess.rules.winconds;

import edu.austral.dissis.chess.validators.DefaultCheckValidator;
import edu.austral.dissis.common.board.Board;
import java.awt.Color;

public class DefaultCheck implements Check {
  private final Color team;

  public DefaultCheck(Color team) {
    this.team = team;
  }

  @Override
  public boolean isValidRule(Board context) {
    return check(context, team);
  }

  @Override
  public Color getTeam() {
    return team;
  }

  private boolean check(Board context, Color team) {
    DefaultCheckValidator validator = new DefaultCheckValidator();
    return context.getPiecesAndPositions().entrySet().stream()
        .anyMatch(entry -> validator.isInCheck(context, team, entry.getKey()));
  }
}
