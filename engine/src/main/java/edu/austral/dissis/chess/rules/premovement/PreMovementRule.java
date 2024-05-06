package edu.austral.dissis.chess.rules.premovement;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.utils.move.ChessMove;

public interface PreMovementRule {
  boolean isValidRule(ChessMove move, ChessGame game);

  String getStringErrorRepresentation();
}
