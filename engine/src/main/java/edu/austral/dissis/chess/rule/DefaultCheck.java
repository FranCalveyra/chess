package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.validator.CheckValidator;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.Map.Entry;

public class DefaultCheck implements Check {
  @Override
  public boolean isValidRule(Board context) {
    return check(context, Color.WHITE) || check(context, Color.BLACK);
  }

  protected boolean check(Board context, Color team) {
    CheckValidator validator = new CheckValidator();
    for (Entry<Position, Piece> entry : context.getActivePiecesAndPositions().entrySet()) {
      if (validator.kingInCheck(context, team, entry)) {
        return true;
      }
    }
    return false;
  }
}
