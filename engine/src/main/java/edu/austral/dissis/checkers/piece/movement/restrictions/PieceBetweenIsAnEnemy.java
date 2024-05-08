package edu.austral.dissis.checkers.piece.movement.restrictions;

import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.MovementRestriction;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class PieceBetweenIsAnEnemy implements MovementRestriction {
    @Override
    public boolean isValidRestriction(GameMove move, Board context) {
        BoardPosition between = getPosBetween(move);
        return context.pieceAt(between).getPieceColour() != context.pieceAt(move.from()).getPieceColour();
    }

    private BoardPosition getPosBetween(GameMove move) {
        int rowDelta = move.to().getRow() - move.from().getRow();
        int colDelta = move.to().getColumn() - move.from().getColumn();
        return lastPos(move.to(), colDelta, rowDelta);
    }

    private BoardPosition lastPos(
            BoardPosition to,int deltaX, int deltaY) {
        if (deltaX > 0) {
            if (deltaY > 0) {
                return getEnemyPos(to, new Pair<>(1, 1));
            } else {
                return getEnemyPos(to, new Pair<>(-1, 1));
            }
        } else {
            if (deltaY > 0) {
                return getEnemyPos(to, new Pair<>(1, -1));
            } else {
                return getEnemyPos(to, new Pair<>(-1, -1));
            }
        }
    }

    private BoardPosition getEnemyPos(BoardPosition to,Pair<Integer, Integer> vector) {
        return new BoardPosition(to.getRow()+vector.first(), to.getColumn() + vector.second());
    }

}
