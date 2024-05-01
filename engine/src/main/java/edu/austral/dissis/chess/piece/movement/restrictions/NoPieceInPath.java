package edu.austral.dissis.chess.piece.movement.restrictions;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.MoveType;
import edu.austral.dissis.chess.validators.PiecePathValidator;

public class NoPieceInPath implements MovementRestriction{
    private final MoveType moveType;
    public NoPieceInPath(MoveType moveType) {
        this.moveType = moveType;
    }

    @Override
    public boolean isValidRule(ChessMove move, Board context) {
        return new PiecePathValidator().isNoPieceBetween(move.from(), move.to(), context, moveType);
    }
}
