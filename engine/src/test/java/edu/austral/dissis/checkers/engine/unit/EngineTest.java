package edu.austral.dissis.checkers.engine.unit;

import static edu.austral.dissis.chess.utils.AuxStaticMethods.getPiecesList;
import static edu.austral.dissis.chess.utils.AuxStaticMethods.getPlayerColor;
import static edu.austral.dissis.chess.utils.AuxStaticMethods.makeMove;
import static edu.austral.dissis.chess.utils.AuxStaticMethods.mapMove;
import static edu.austral.dissis.chess.utils.AuxStaticMethods.moveFromAlgebraic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.dissis.chess.gui.UndoState;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.ui.gameengine.ChessGameEngine;
import edu.austral.dissis.chess.utils.enums.GameType;
import org.junit.jupiter.api.Test;

public class EngineTest {
  ChessGame game = new GameProvider().provide(GameType.DEFAULT_CHESS);
  GameEngine engine = new ChessGameEngine(game);

  @Test
  public void firstMoveShouldBeValid() {
    ChessGame updatedGame = makeMove(game, "a2 -> a4").game();
    MoveResult afterMove = engine.applyMove(move("a2 -> a4"));
    MoveResult beforeMove =
        new NewGameState(
            getPiecesList(game),
            getPlayerColor(game.getCurrentTurn()),
            new UndoState(false, false));
    MoveResult undone =
        new NewGameState(
            getPiecesList(game), getPlayerColor(game.getCurrentTurn()), new UndoState(false, true));
    // Assertions
    assertNotEquals(beforeMove, afterMove);
    assertEquals(
        new NewGameState(
            getPiecesList(updatedGame),
            getPlayerColor(updatedGame.getCurrentTurn()),
            new UndoState(true, false)),
        afterMove);
    assertEquals(undone, engine.undo());
    assertEquals(afterMove, engine.redo());
  }

  private Move move(String fullMove) {
    return mapMove(moveFromAlgebraic(fullMove));
  }
}
