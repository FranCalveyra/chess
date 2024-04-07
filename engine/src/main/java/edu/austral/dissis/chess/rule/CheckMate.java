package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.validator.CheckValidator;
import java.awt.Color;
import java.util.Map;

public class CheckMate implements WinCondition {
  private final Color team;

  public CheckMate(Color team) {
    this.team = team;
  }

  @Override
  public boolean isValidRule(Board context) {
    return checkmate(context, team);
  }

  private boolean checkmate(Board context, Color team) {
    // TODO: Need to check if king has any possible move, or a piece can be moved in order to
    CheckValidator validator = new CheckValidator();
    for (Map.Entry<Position, Piece> entry : context.getActivePiecesAndPositions().entrySet()) {
      if (!validator.kingInCheck(context, team, entry)) {
        return false;
      }
    }
    return true;
  }
}
