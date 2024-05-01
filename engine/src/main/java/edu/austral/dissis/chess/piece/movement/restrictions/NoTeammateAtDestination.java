package edu.austral.dissis.chess.piece.movement.restrictions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessMove;

public class NoTeammateAtDestination implements MovementRestriction {
    @Override
    public boolean isValidRule(ChessMove move, Board context) {
        Piece lastPiece = context.pieceAt(move.to());
        return new ClearTile().isValidRule(move,context) || lastPiece.getPieceColour() != context.pieceAt(move.from()).getPieceColour();
    }
}
