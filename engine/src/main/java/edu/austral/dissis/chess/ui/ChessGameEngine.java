package edu.austral.dissis.chess.ui;

import static java.awt.Color.BLACK;

import edu.austral.dissis.chess.engine.Board;
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
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.move.ChessPosition;
import edu.austral.dissis.chess.utils.result.CheckState;
import edu.austral.dissis.chess.utils.result.ChessMoveResult;
import edu.austral.dissis.chess.utils.result.GameResult;
import edu.austral.dissis.chess.utils.result.GameWon;
import edu.austral.dissis.chess.utils.result.PieceTaken;
import edu.austral.dissis.chess.utils.result.ValidMove;
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
    GameResult gameResult =
        game.makeMove(new ChessMove(mapPos(move.getFrom()), mapPos(move.getTo())));
    game = gameResult.game();
    return getMoveResults(gameResult);
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
    for (Map.Entry<ChessPosition, Piece> entry : board.getPiecesAndPositions().entrySet()) {
      if (entry.getValue().equals(piece)) {
        return new Position(
            entry.getKey().getRow() + 1, entry.getKey().getColumn() + 1); // From zero based
      }
    }
    return null;
  }

  private MoveResult updateGameState(ChessGame game) {
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

  private ChessPosition mapPos(Position position) {
    return new ChessPosition(position.getRow() - 1, position.getColumn() - 1);
  }

  private @NotNull MoveResult getMoveResults(GameResult gameResult) {
    ChessMoveResult moveResult = gameResult.moveResult();
    switch (moveResult) {
      case GameWon g:
        return new GameOver(getPlayerColor(g.getWinner()));
      case edu.austral.dissis.chess.utils.result.InvalidMove invalidMove:
        return new InvalidMove(moveResult.getMessage());
      case ValidMove validMove:
        return updateGameState(gameResult.game());
      case PieceTaken pieceTaken:
        return updateGameState(gameResult.game());
      case CheckState checkState:
        return updateGameState(gameResult.game());
      default:
        throw new IllegalStateException();
    }
  }
}
