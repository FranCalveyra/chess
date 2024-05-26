package edu.austral.dissis.common.ui.gameengine;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getPiecesList;
import static edu.austral.dissis.common.utils.AuxStaticMethods.getPlayerColor;

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
import edu.austral.dissis.common.game.BoardGame;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.gameresult.BoardGameResult;
import edu.austral.dissis.common.utils.result.playresult.GameWon;
import edu.austral.dissis.common.utils.result.playresult.InvalidPlay;
import edu.austral.dissis.common.utils.result.playresult.PieceTaken;
import edu.austral.dissis.common.utils.result.playresult.PlayResult;
import edu.austral.dissis.common.utils.result.playresult.PromotedPiece;
import edu.austral.dissis.common.utils.result.playresult.ValidPlay;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;

public class BoardGameEngine implements GameEngine {
  private BoardGame game;
  private final Stack<BoardGame> undo;
  private final Stack<BoardGame> redo;

  public BoardGameEngine(BoardGame game) {
    this.game = game;
    undo = new Stack<>();
    redo = new Stack<>();
  }

  @NotNull
  @Override
  public MoveResult applyMove(@NotNull Move move) {
    BoardGameResult gameResult =
        game.makeMove(new GameMove(mapPos(move.getFrom()), mapPos(move.getTo())));
    if (gameResult.moveResult().getClass() != InvalidPlay.class) {
      undo.push(game);
      game = gameResult.game();
      redo.removeAllElements();
    }
    return getMoveResult(gameResult);
  }

  @NotNull
  @Override
  public InitialState init() {
    return new InitialState(
        new BoardSize(game.getBoard().getColumns(), game.getBoard().getRows()),
        getPiecesList(game),
        getPlayerColor(game.getCurrentTurn()));
  }

  @NotNull
  @Override
  public NewGameState undo() {
    BoardGame undone = undo.pop();
    redo.push(game);
    game = undone;
    return getNewGameState(undone);
  }

  @NotNull
  @Override
  public NewGameState redo() {
    BoardGame redone = redo.pop();
    undo.push(game);
    game = redone;
    return getNewGameState(redone);
  }

  // Private stuff
  private MoveResult updateGameState(BoardGame game) {
    return new NewGameState(
        getPiecesList(game),
        getPlayerColor(game.getCurrentTurn()),
        new UndoState(!undo.isEmpty(), !redo.isEmpty()));
  }

  private BoardPosition mapPos(Position position) {
    return new BoardPosition(position.getRow() - 1, position.getColumn() - 1);
  }

  private @NotNull MoveResult getMoveResult(BoardGameResult boardGameResult) {
    PlayResult playResult = boardGameResult.moveResult();
    switch (playResult) {
      case GameWon g:
        return new GameOver(getPlayerColor(g.getWinner()));
      case InvalidPlay invalidMove:
        return new InvalidMove(playResult.getMessage());
      case ValidPlay validMove:
        return updateGameState(boardGameResult.game());
      case PieceTaken pieceTaken:
        return updateGameState(boardGameResult.game());
      case PromotedPiece promotedPiece:
        return updateGameState(boardGameResult.game());
      default:
        throw new IllegalStateException();
    }
  }

  private @NotNull NewGameState getNewGameState(BoardGame game) {
    return new NewGameState(
        getPiecesList(game),
        getPlayerColor(game.getCurrentTurn()),
        new UndoState(!undo.isEmpty(), !redo.isEmpty()));
  }
}
