package edu.austral.dissis.common.piece.movement.restrictions;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.GameMove;

public interface MovementRestriction {
  boolean isValidRestriction(GameMove move, Board context);
}
