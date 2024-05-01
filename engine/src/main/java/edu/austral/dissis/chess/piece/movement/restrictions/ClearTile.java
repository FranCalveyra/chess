package edu.austral.dissis.chess.piece.movement.restrictions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessMove;

public class ClearTile implements MovementRestriction{
    @Override
    public boolean isValidRule(ChessMove move, Board context) {
        return context.pieceAt(move.to()) == null;
    }
}
