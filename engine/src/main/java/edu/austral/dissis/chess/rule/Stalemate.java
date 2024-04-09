package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import java.awt.Color;
import java.util.Map;

public class Stalemate implements WinCondition {
  // Should handle "tie"
  @Override
  public boolean isValidRule(Board context) throws UnallowedMoveException {
    return bruteForceMovementCheck(context, context.getCurrentTurn());
  }

  private boolean bruteForceMovementCheck(Board context, Color team) throws UnallowedMoveException {
    for (Map.Entry<Position, Piece> entry : context.getPiecesAndPositions().entrySet()) {
      Position actualPosition = entry.getKey();
      Piece piece = entry.getValue();
      if (hasValidMove(actualPosition, piece, context, team)) {
        return false;
      }
    }
    return true;
  }

  private boolean hasValidMove(Position position, Piece piece, Board context, Color team)
      throws UnallowedMoveException {
    if (piece.getPieceColour() != team) {
      return false;
    }
    return !piece.getMoveSet(position, context).isEmpty();
  }
}
