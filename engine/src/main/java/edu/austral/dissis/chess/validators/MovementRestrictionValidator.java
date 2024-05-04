package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.move.ChessMove;

public interface MovementRestrictionValidator {
  boolean isValidMove(ChessMove move, Board context);
}
