package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.validators.DefaultCheckValidator;
import java.awt.Color;

public final class DefaultCheck implements Check {
  private final Color team;

  public DefaultCheck(Color team) {
    this.team = team;
  }

  @Override
  public boolean isValidRule(Board context) {
    return check(context, team);
  }

  private boolean check(Board context, Color team) {
    DefaultCheckValidator validator = new DefaultCheckValidator();
    return context.getPiecesAndPositions().entrySet().stream()
        .anyMatch(entry -> validator.isInCheck(context, team, entry.getKey()));
  }

  @Override
  public Color team() {
    return team;
  }
}
