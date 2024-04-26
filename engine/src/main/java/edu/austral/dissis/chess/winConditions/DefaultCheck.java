package edu.austral.dissis.chess.winConditions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.validator.DefaultCheckValidator;
import java.awt.Color;
import java.util.Map.Entry;

public class DefaultCheck implements Check {
  private final Color team;

  public DefaultCheck(Color team) {
    this.team = team;
  }

  @Override
  public boolean isValidRule(Board context) {
    return check(context, team);
  }

  protected boolean check(Board context, Color team) {
    DefaultCheckValidator validator = new DefaultCheckValidator();
    for (Entry<Position, Piece> entry : context.getPiecesAndPositions().entrySet()) {
      if (validator.isInCheck(context, team, entry.getKey(), entry.getValue())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Color getTeam() {
    return team;
  }
}
