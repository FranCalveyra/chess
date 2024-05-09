package edu.austral.dissis.chess.ui.gameengine;

import static java.awt.Color.BLACK;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.gui.BoardSize;
import edu.austral.dissis.chess.gui.ChessPiece;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameOver;
import edu.austral.dissis.chess.gui.InitialState;
import edu.austral.dissis.chess.gui.InvalidMove;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.dissis.chess.gui.PlayerColor;
import edu.austral.dissis.chess.gui.Position;
import edu.austral.dissis.chess.gui.UndoState;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.utils.result.CheckState;
import edu.austral.dissis.chess.utils.result.ChessGameResult;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.*;
import edu.austral.dissis.common.utils.result.PlayResult;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;

public class ChessGameEngine implements GameEngine {
  //TODO: TEST FOR COVERAGE TO GO UP
  private ChessGame game;
  private final Stack<ChessGameResult> undo;
  private Stack<ChessGameResult> redo;
  private ChessGameResult currentState;
  private ChessGameResult initialState;

  public ChessGameEngine(ChessGame game) {
    this.game = game;
    undo = new Stack<>();
    redo = new Stack<>();
    currentState = new ChessGameResult(game, new ValidPlay());
  }

  @NotNull
  @Override
  public MoveResult applyMove(@NotNull Move move) {
    undo.push(currentState);
    if (!redo.isEmpty()) {
      redo = new Stack<>();
    }
    currentState = game.makeMove(new GameMove(mapPos(move.getFrom()), mapPos(move.getTo())));
    game = currentState.game();
    return getMoveResults(currentState);
  }

  @NotNull
  @Override
  public InitialState init() {
    initialState = currentState;
    return new InitialState(
        new BoardSize(
            currentState.game().getBoard().getColumns(), currentState.game().getBoard().getRows()),
        getPiecesList(currentState.game()),
        getPlayerColor(currentState.game().getCurrentTurn()));
  }

  @NotNull
  @Override
  public NewGameState undo() {
    if (!undo.isEmpty() && undo.peek() == currentState) {
      undo.pop();
    }
    if (undo.isEmpty()) {
      return new NewGameState(
          getPiecesList(currentState.game()),
          getPlayerColor(currentState.game().getCurrentTurn()),
          new UndoState(false, !redo.isEmpty()));
    }
    if (redo.isEmpty()) {
      redo.push(currentState);
    }
    currentState = undo.peek();
    redo.push(undo.pop());
    NewGameState newGameState = getNewGameState();
    game = currentState.game();
    return newGameState;
  }

  @NotNull
  @Override
  public NewGameState redo() {
    if (redo.isEmpty()) {
      return new NewGameState(
          getPiecesList(currentState.game()),
          getPlayerColor(currentState.game().getCurrentTurn()),
          new UndoState(!undo.isEmpty(), false));
    }
    if (redo.peek() == currentState || redo.peek() == initialState) {
      undo.push(redo.pop());
    }
    if (undo.isEmpty()) {
      undo.push(currentState);
    }
    currentState = redo.peek();
    undo.push(redo.pop());
    NewGameState newGameState = getNewGameState();
    game = currentState.game();
    return newGameState;
  }

  // Private stuff
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
    return new NewGameState(
        getPiecesList(game),
        getPlayerColor(game.getCurrentTurn()),
        new UndoState(!undo.isEmpty(), !redo.isEmpty()));
  }

  private @NotNull List<ChessPiece> getPiecesList(ChessGame game) {
    return game.getBoard().getPiecesAndPositions().values().stream()
        .map(
            piece ->
                new ChessPiece(
                    piece.getId(),
                    getPlayerColor(piece.getPieceColour()),
                    Objects.requireNonNull(getPiecePosition(piece, game.getBoard())),
                    (handleType(piece.getType())).toString().toLowerCase()))
        .toList();
  }

  private PieceType handleType(PieceType type) {
    if (type instanceof ChessPieceType) {
      return type;
    } else {
      if (type == CheckersType.MAN) {
        return ChessPieceType.PAWN;
      } else if (type == CheckersType.KING) {
        return ChessPieceType.QUEEN;
      }
    }
    return null;
  }

  private BoardPosition mapPos(Position position) {
    return new BoardPosition(position.getRow() - 1, position.getColumn() - 1);
  }

  private @NotNull edu.austral.dissis.chess.gui.MoveResult getMoveResults(
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

  private @NotNull NewGameState getNewGameState() {
    return new NewGameState(
        getPiecesList(currentState.game()),
        getPlayerColor(currentState.game().getCurrentTurn()),
        new UndoState(!undo.isEmpty(), !redo.isEmpty()));
  }
}
