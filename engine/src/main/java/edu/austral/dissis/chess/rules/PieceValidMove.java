package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessMoveResult;
import edu.austral.dissis.chess.utils.GameResult;
import edu.austral.dissis.chess.utils.Pair;

import java.util.List;

import static edu.austral.dissis.chess.utils.ChessMoveResult.VALID_MOVE;

public class PieceValidMove implements PreMovementRule{
    @Override
    public boolean isValidRule(ChessMove move, ChessGame game) {
        Piece pieceToMove = game.getBoard().pieceAt(move.from());
        final List<ChessMove> playToExecute = pieceToMove.getPlay(move.from(),move.to(), game.getBoard());
        // No moves available
        return !playToExecute.isEmpty();
    }
}
