package edu.austral.dissis.chess.ui;

import static java.awt.Color.BLACK;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.chess.utils.result.CheckState;
import edu.austral.dissis.chess.utils.result.ChessGameResult;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.*;
import edu.austral.dissis.common.utils.result.PlayResult;

import java.awt.Color;
import java.util.Stack;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

public class ChessGameEngine implements GameEngine {
  private ChessGame game;
  private final Stack<ChessGameResult> undo;
  private Stack<ChessGameResult> redo;
  private ChessGameResult currentState;

  public ChessGameEngine(ChessGame game) {
    this.game = game;
    undo = new Stack<>();
    redo = new Stack<>();
  }

  @NotNull
  @Override
  public MoveResult applyMove(@NotNull Move move) {
    undo.push(currentState);
    if(!redo.isEmpty()){
      redo = new Stack<>();
    }
    currentState = game.makeMove(new GameMove(mapPos(move.getFrom()), mapPos(move.getTo())));
    game = currentState.game();
    return getMoveResults(currentState);
  }

  @NotNull
  @Override
  public InitialState init() {
    currentState = new ChessGameResult(game, new ValidPlay());
    undo.push(currentState);
    return new InitialState(
        new BoardSize(game.getBoard().getColumns(), game.getBoard().getRows()),
        getPiecesList(game),
        getPlayerColor(game.getCurrentTurn()));
  }

  @NotNull
  @Override
  public NewGameState undo() {
    if(undo.isEmpty()){
      return new NewGameState(getPiecesList(game), getPlayerColor(game.getCurrentTurn()), new UndoState(false, !redo.isEmpty()));
    }
    ChessGameResult lastPlay = undo.pop();
    redo.push(lastPlay);
    currentState = lastPlay;
    NewGameState newGameState = new NewGameState(getPiecesList(currentState.game()), getPlayerColor(currentState.game().getCurrentTurn()), new UndoState(!undo.isEmpty(), !redo.isEmpty()));
    game = lastPlay.game();
    return newGameState;
  }
  @NotNull
  @Override
  public NewGameState redo() {
    if(redo.isEmpty()){
      return new NewGameState(getPiecesList(game), getPlayerColor(game.getCurrentTurn()), new UndoState(!undo.isEmpty(), false));
    }
    ChessGameResult lastPlay = redo.pop();
    undo.push(lastPlay);
    currentState = lastPlay;
    NewGameState newGameState = new NewGameState(getPiecesList(currentState.game()), getPlayerColor(currentState.game().getCurrentTurn()), new UndoState(!undo.isEmpty(), !redo.isEmpty()));
    game = lastPlay.game();
    return newGameState;
  }


  //Private stuff
  private static PlayerColor getPlayerColor(Color color) {
    return color == BLACK ? PlayerColor.BLACK : PlayerColor.WHITE;
  }

  private static Position getPiecePosition(Piece piece, Board board) {
    for (Map.Entry<BoardPosition, Piece> entry : board.getPiecesAndPositions().entrySet()) {
      if (entry.getValue().equals(piece)) {
        return new Position(
            entry.getKey().getRow() + 1, entry.getKey().getColumn() + 1); // From zero based
      }
    }
    return null;
  }

  private edu.austral.dissis.chess.gui.MoveResult updateGameState(ChessGame game) {
    return new NewGameState(getPiecesList(game), getPlayerColor(game.getCurrentTurn()), new UndoState(!undo.isEmpty(), !redo.isEmpty()));
  }

  private @NotNull List<ChessPiece> getPiecesList(ChessGame game) {
    return game.getBoard().getPiecesAndPositions().values().stream()
        .map(
            piece ->
                new ChessPiece(
                    piece.getId(),
                    getPlayerColor(piece.getPieceColour()),
                    Objects.requireNonNull(getPiecePosition(piece, game.getBoard())),
                    piece.getType().toString().toLowerCase()))
        .toList();
  }

  private BoardPosition mapPos(Position position) {
    return new BoardPosition(position.getRow() - 1, position.getColumn() - 1);
  }

  private @NotNull edu.austral.dissis.chess.gui.MoveResult getMoveResults(ChessGameResult chessGameResult) {
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
}
