package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;
import java.util.Map;

public class Stalemate implements TieRule {
  // Should handle "tie"
  @Override
  public boolean isValidRule(Board context) {
    // TODO
    return bruteForceMovementCheck(context);
  }

  private boolean bruteForceMovementCheck(Board context) {
    for (Map.Entry<Position, Piece> entry : context.getActivePiecesAndPositions().entrySet()) {
      Position actualPosition = entry.getKey();
      Piece piece = entry.getValue();
      if (hasValidMove(actualPosition, piece)) {
        return false;
      }
    }
    return true;
  }

  private boolean hasValidMove(Position position, Piece piece) {
    // TODO
    return false;
  }
}
