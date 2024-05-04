package edu.austral.dissis.chess.piece.movement.restrictions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessMove;

public class IsAnEnemy implements MovementRestriction {
    @Override
    public boolean isValidRestriction(ChessMove move, Board context) {
        Piece lastPiece = context.pieceAt(move.to());
        return lastPiece != null && lastPiece.getPieceColour() != context.pieceAt(move.from()).getPieceColour();
    }
}
