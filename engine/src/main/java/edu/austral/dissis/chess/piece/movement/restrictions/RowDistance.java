package edu.austral.dissis.chess.piece.movement.restrictions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;

public class RowDistance implements MovementRestriction {
    private final int rowDistance;
    public RowDistance(int rowDistance) {
        this.rowDistance = rowDistance;
    }

    @Override
    public boolean isValidRestriction(ChessMove move, Board context) {
        ChessPosition oldPos = move.from();
        ChessPosition newPos = move.to();
        return newPos.getRow()-oldPos.getRow() == rowDistance;
    }
}
