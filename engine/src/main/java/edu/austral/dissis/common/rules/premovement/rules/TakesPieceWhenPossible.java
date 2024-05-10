package edu.austral.dissis.common.rules.premovement.rules;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.common.piece.movement.type.TakingMove;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.move.MoveExecutor;
import edu.austral.dissis.common.utils.result.PieceTaken;

import java.util.Objects;

public class TakesPieceWhenPossible implements PreMovementRule{
    @Override
    public boolean isValidRule(GameMove move, ChessGame game) {
        //TODO: implement to fetch all possible attacking moves and obligue player to execute them
        MoveExecutor executor = new MoveExecutor();
        boolean isAttacking = game.getBoard().pieceAt(move.from()).getMovements().stream().anyMatch(movement -> movement instanceof TakingMove && movement.isValidMove(move,game.getBoard()));
        if(isAttacking){
            return Objects.equals(executor.executeMove(move.from(), move.to(), game.getBoard(), game.getPromoter()).second(), new PieceTaken());
        }
        return new PieceValidMove().isValidRule(move,game);
    }
}
