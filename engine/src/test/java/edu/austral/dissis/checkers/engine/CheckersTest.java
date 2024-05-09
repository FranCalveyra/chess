package edu.austral.dissis.checkers.engine;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.utils.enums.GameType;
import edu.austral.dissis.chess.utils.result.ChessGameResult;
import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.GameResult;
import org.junit.jupiter.api.Test;

import static edu.austral.dissis.chess.utils.AuxStaticMethods.makeMove;

public class CheckersTest {
  // TODO: IMPLEMENT
    private ChessGame game = new GameProvider().provide(GameType.DEFAULT_CHECKERS);
    @Test
    public void initialMovement() {
        System.out.println(game.getBoard());
        GameResult result = game.makeMove(new GameMove(BoardPosition.fromAlgebraic("a3"), BoardPosition.fromAlgebraic("b4")));
        game = (ChessGame) result.game();
        System.out.println(result.moveResult());
    }
}
