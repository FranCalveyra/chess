package edu.austral.dissis.chess.validators;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.GameMove;

public interface MovementRestrictionValidator {
  boolean isValidMove(GameMove move, Board context);
}
