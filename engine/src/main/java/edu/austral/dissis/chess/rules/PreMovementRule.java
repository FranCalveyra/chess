package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.utils.ChessMove;

public interface PreMovementRule {
  boolean isValidRule(ChessMove move, ChessGame game);
}
