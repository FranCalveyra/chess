package edu.austral.dissis.chess.ui.gameengine;

import static edu.austral.dissis.chess.utils.AuxStaticMethods.getPiecesList;
import static edu.austral.dissis.chess.utils.AuxStaticMethods.getPlayerColor;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.gui.BoardSize;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameOver;
import edu.austral.dissis.chess.gui.InitialState;
import edu.austral.dissis.chess.gui.InvalidMove;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.dissis.chess.gui.Position;
import edu.austral.dissis.chess.gui.UndoState;
import edu.austral.dissis.chess.utils.result.CheckState;
import edu.austral.dissis.chess.utils.result.ChessGameResult;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.GameWon;
import edu.austral.dissis.common.utils.result.InvalidPlay;
import edu.austral.dissis.common.utils.result.PieceTaken;
import edu.austral.dissis.common.utils.result.PlayResult;
import edu.austral.dissis.common.utils.result.ValidPlay;

import java.util.Objects;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;

public class ChessGameEngine implements GameEngine {
  private ChessGame game;
  private final Stack<ChessGame> undo;
  private final Stack<ChessGame> redo;

  public ChessGameEngine(ChessGame game) {
    this.game = game;
    undo = new Stack<>();
    redo = new Stack<>();
  }

  @NotNull
  @Override
  public MoveResult applyMove(@NotNull Move move) {
    ChessGameResult gameResult = game.makeMove(new GameMove(mapPos(move.getFrom()), mapPos(move.getTo())));
    if(gameResult.moveResult().getClass() != InvalidPlay.class) {
      undo.push(game);
      game = gameResult.game();
      redo.removeAllElements();
      return getMoveResult(gameResult);
    }
    return new InvalidMove(gameResult.moveResult().getMessage());
  }

  @NotNull
  @Override
  public InitialState init(){
    return new InitialState(
        new BoardSize(
            game.getBoard().getColumns(), game.getBoard().getRows()),
        getPiecesList(game),
        getPlayerColor(game.getCurrentTurn()));
  }

  @NotNull
  @Override
  public NewGameState undo() {
    //TODO: fix, doesn't work
//    if (!undo.isEmpty() && undo.peek() == currentState) {
//      undo.pop();
//    }
//    if (undo.isEmpty()) {
//      return new NewGameState(
//          getPiecesList(currentState.game()),
//          getPlayerColor(currentState.game().getCurrentTurn()),
//          new UndoState(false, !redo.isEmpty()));
//    }
//    if (redo.isEmpty()) {
//      redo.push(currentState);
//    }
    ChessGame undone = undo.pop();
    redo.push(game);
    game = undone;
    return getNewGameState(undone);
  }

  @NotNull
  @Override
  public NewGameState redo() {
    //TODO: fix, doesn't work
//    if (redo.isEmpty()) {
//      return new NewGameState(
//          getPiecesList(currentState.game()),
//          getPlayerColor(currentState.game().getCurrentTurn()),
//          new UndoState(!undo.isEmpty(), false));
//    }
//    if (redo.peek() == currentState || redo.peek() == initialState) {
//      undo.push(redo.pop());
//    }
//    if (undo.isEmpty()) {
//      undo.push(currentState);
//    }
    ChessGame redone = redo.pop();
    undo.push(game);
    game = redone;
    return getNewGameState(redone);
  }

  // Private stuff
  private MoveResult updateGameState(ChessGame game) {
    return new NewGameState(
        getPiecesList(game),
        getPlayerColor(game.getCurrentTurn()),
        new UndoState(!undo.isEmpty(), !redo.isEmpty()));
  }

  private BoardPosition mapPos(Position position) {
    return new BoardPosition(position.getRow() - 1, position.getColumn() - 1);
  }

  private @NotNull MoveResult getMoveResult(
      ChessGameResult chessGameResult) {
    PlayResult playResult = chessGameResult.moveResult();
    switch (playResult) {
      case GameWon g:
        return new GameOver(getPlayerColor(g.getWinner()));
      case InvalidPlay invalidMove:
        return new InvalidMove(playResult.getMessage());
      case ValidPlay validMove:
        return updateGameState(chessGameResult.game());
      case PieceTaken pieceTaken:
        return updateGameState(chessGameResult.game());
      case CheckState checkState:
        return updateGameState(chessGameResult.game());
      default:
        throw new IllegalStateException();
    }
  }

  private @NotNull NewGameState getNewGameState(ChessGame game) {
    return new NewGameState(
        getPiecesList(game),
        getPlayerColor(game.getCurrentTurn()),
        new UndoState(!undo.isEmpty(), !redo.isEmpty()));
  }
}
