package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.result.ChessMoveResult;

public interface PreMovementValidator {
  ChessMoveResult getMoveValidity(ChessMove move, ChessGame game);
}
