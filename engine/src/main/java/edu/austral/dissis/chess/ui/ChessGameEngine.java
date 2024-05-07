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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class ChessGameEngine implements GameEngine {
  private ChessGame game;

  public ChessGameEngine(ChessGame game) {
    this.game = game;
  }

  @NotNull
  @Override
  public MoveResult applyMove(@NotNull Move move) {
    ChessGameResult chessGameResult =
        game.makeMove(new GameMove(mapPos(move.getFrom()), mapPos(move.getTo())));
    game = chessGameResult.game();
    return getMoveResults(chessGameResult);
  }

  @NotNull
  @Override
  public InitialState init() {
    return new InitialState(
        new BoardSize(game.getBoard().getColumns(), game.getBoard().getRows()),
        getPiecesList(game),
        getPlayerColor(game.getCurrentTurn()));
  }

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
    return new NewGameState(getPiecesList(game), getPlayerColor(game.getCurrentTurn()));
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
