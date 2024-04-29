package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessMove;

public class TurnRule implements PreMovementRule{
    @Override
    public boolean isValidRule(ChessMove move, ChessGame game) {
        Piece toMove = game.getBoard().pieceAt(move.from());
        return toMove.getPieceColour() == game.getCurrentTurn();
    }
}
