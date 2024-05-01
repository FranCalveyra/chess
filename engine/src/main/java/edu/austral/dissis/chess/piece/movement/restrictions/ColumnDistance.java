package edu.austral.dissis.chess.piece.movement.restrictions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;

public class ColumnDistance implements MovementRestriction {
    private final int columnDistance;
    public ColumnDistance(int columnDistance) {
        this.columnDistance = columnDistance;
    }

    @Override
    public boolean isValidRule(ChessMove move, Board context) {
        ChessPosition oldPos = move.from();
        ChessPosition newPos = move.to();
        return newPos.getColumn()-oldPos.getColumn() == columnDistance;
    }
}
