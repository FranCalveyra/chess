package edu.austral.dissis.unit;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getPiecesList;
import static edu.austral.dissis.common.utils.AuxStaticMethods.getPlayerColor;
import static edu.austral.dissis.common.utils.AuxStaticMethods.makeMove;
import static edu.austral.dissis.common.utils.AuxStaticMethods.mapMove;
import static edu.austral.dissis.common.utils.AuxStaticMethods.moveFromAlgebraic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.dissis.chess.gui.UndoState;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.common.ui.gameengine.BoardGameEngine;
import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.utils.enums.GameType;
import org.junit.jupiter.api.Test;

public class EngineTest {
  BoardGame game = new GameProvider().provide(GameType.DEFAULT_CHESS);
  GameEngine engine = new BoardGameEngine(game);

  @Test
  public void firstMoveShouldBeValid() {
    BoardGame updatedGame = makeMove(game, "a2 -> a4").game();
    MoveResult afterMove = engine.applyMove(mapMove(moveFromAlgebraic("a2 -> a4")));
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
}
