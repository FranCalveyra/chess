package edu.austral.dissis.common.piece.movement.restrictions.rules;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.GameMove;

public class ClearTile implements MovementRestriction {
  @Override
  public boolean isValidRestriction(GameMove move, Board context) {
    return context.pieceAt(move.to()) == null;
  }
}
