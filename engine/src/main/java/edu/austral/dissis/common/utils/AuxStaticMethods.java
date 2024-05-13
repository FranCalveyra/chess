package edu.austral.dissis.common.utils;

import static edu.austral.dissis.common.utils.move.BoardPosition.fromAlgebraic;
import static java.awt.Color.BLACK;

import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.chess.engine.BoardGame;
import edu.austral.dissis.chess.gui.CachedImageResolver;
import edu.austral.dissis.chess.gui.ChessPiece;
import edu.austral.dissis.chess.gui.DefaultImageResolver;
import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.dissis.chess.gui.PlayerColor;
import edu.austral.dissis.chess.gui.Position;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.ui.gameengine.ChessGameEngine;
import edu.austral.dissis.chess.utils.result.BoardGameResult;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class AuxStaticMethods {
  // Move
  public static GameMove moveFromAlgebraic(String fullMove) {
    String from = fullMove.substring(0, 2);
    String to = fullMove.substring(6);
    return new GameMove(fromAlgebraic(from), fromAlgebraic(to));
  }

  public static Move mapMove(GameMove move) {
    return new Move(mapPos(move.from()), mapPos(move.to()));
  }

  private static Position mapPos(BoardPosition pos) {
    return new Position(pos.getRow() + 1, pos.getColumn() + 1);
  }

  public static BoardGameResult makeMove(BoardGame game, String move) {
    return game.makeMove(moveFromAlgebraic(move));
  }

  // Game Engine
  public static @NotNull List<ChessPiece> getPiecesList(BoardGame game) {
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

  public static PlayerColor getPlayerColor(Color color) {
    return color == BLACK ? PlayerColor.BLACK : PlayerColor.WHITE;
  }

  private static PieceType handleType(PieceType type) {
    if (type instanceof ChessPieceType) {
      return type;
    } else {
      if (type == CheckersType.MAN) {
        return ChessPieceType.PAWN;
      } else if (type == CheckersType.KING) {
        return ChessPieceType.QUEEN;
      }
    }
    throw new IllegalArgumentException("No piece with that type");
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

  public static Pair<GameEngine, ImageResolver> setupGame(GameType type) {
    final GameEngine gameEngine = new ChessGameEngine(new GameProvider().provide(type));
    final ImageResolver imageResolver = new CachedImageResolver(new DefaultImageResolver());
    return new Pair<>(gameEngine, imageResolver);
  }

  // Other stuff
  public static BoardPosition getPosBetween(GameMove move) {
    int rowDelta = move.to().getRow() - move.from().getRow();
    int colDelta = move.to().getColumn() - move.from().getColumn();
    return lastPos(move.from(), colDelta, rowDelta);
  }

  private static BoardPosition lastPos(BoardPosition pos, int deltaX, int deltaY) {
    if (deltaX > 0) {
      if (deltaY > 0) {
        return getEnemyPos(pos, new Pair<>(1, 1));
      } else {
        return getEnemyPos(pos, new Pair<>(-1, 1));
      }
    } else {
      if (deltaY > 0) {
        return getEnemyPos(pos, new Pair<>(1, -1));
      } else {
        return getEnemyPos(pos, new Pair<>(-1, -1));
      }
    }
  }

  private static BoardPosition getEnemyPos(BoardPosition pos, Pair<Integer, Integer> vector) {
    return new BoardPosition(pos.getRow() + vector.first(), pos.getColumn() + vector.second());
  }
}
