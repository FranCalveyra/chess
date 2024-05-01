package edu.austral.dissis.chess.piece.movement.restrictions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessMove;

public interface MovementRestriction {
    boolean isValidRule(ChessMove move, Board context);
}
