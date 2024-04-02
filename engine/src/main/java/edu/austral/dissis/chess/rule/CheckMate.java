package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.CheckValidator;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;
import java.util.Map;

public class CheckMate implements WinCondition {
  @Override
  public boolean isValidRule(Board context) {
    return checkmate(context, Color.WHITE) || checkmate(context, Color.BLACK);
  }

  private boolean checkmate(Board context, Color team) {
    CheckValidator validator = new CheckValidator();
    for (Map.Entry<Position, Piece> entry : context.getActivePiecesAndPositions().entrySet()) {
      if (!validator.kingInCheck(context, team, entry)) {
        return false;
      }
    }
    return true;
  }
}
