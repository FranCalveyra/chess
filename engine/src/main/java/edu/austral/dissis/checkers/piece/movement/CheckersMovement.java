package edu.austral.dissis.checkers.piece.movement;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.GameMove;

public class CheckersMovement implements PieceMovement {
    @Override
    public boolean isValidMove(GameMove move, Board context) {
        return false;
    }
}
