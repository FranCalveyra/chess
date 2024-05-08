package edu.austral.dissis.checkers.piece.movement.type;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.GameMove;

import static edu.austral.dissis.checkers.piece.movement.type.CheckersNormalMovement.getCheckersMovementValidator;

public class CheckersMovement implements PieceMovement {
    @Override
    public boolean isValidMove(GameMove move, Board context) {
        Piece piece = context.pieceAt(move.from());
        return getCheckersMovementValidator(piece.getPieceColour(), piece.getType()).isValidMove(move,context);
    }
}
