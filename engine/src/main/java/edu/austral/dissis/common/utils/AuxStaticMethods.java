package edu.austral.dissis.common.utils;

import static edu.austral.dissis.common.utils.move.BoardPosition.fromAlgebraic;
import static java.awt.Color.BLACK;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.austral.dissis.checkers.piece.movement.CheckersType;
import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.chess.piece.movement.type.ChessPieceType;
import edu.austral.dissis.chess.providers.GameProvider;
import edu.austral.dissis.chess.ui.gameengine.BoardGameEngine;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.piece.movement.type.TakingMovement;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.gameresult.BoardGameResult;
import edu.austral.dissis.online.listeners.client.ClientConnectionListenerImpl;
import edu.austral.dissis.online.listeners.messages.InitialStateListener;
import edu.austral.dissis.online.listeners.server.ServerConnectionListenerImpl;
import edu.austral.ingsis.clientserver.Client;
import edu.austral.ingsis.clientserver.Server;
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder;
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder;
import edu.austral.ingsis.clientserver.serialization.json.JsonDeserializer;
import edu.austral.ingsis.clientserver.serialization.json.JsonSerializer;
import java.awt.Color;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

  public static List<GameMove> getAttackingMoves(
      BoardPosition position, PieceMovement takingMove, Board board) {
    List<GameMove> attackingMoves = new ArrayList<>();
    List<BoardPosition> positions = takingMove.getPossiblePositions(position, board);
    for (BoardPosition possiblePos : positions) {
      attackingMoves.addAll(
          takingMove.getMovesToExecute(new GameMove(position, possiblePos), board));
    }
    return attackingMoves.stream().distinct().toList();
  }

  public static @Nullable PieceMovement getTakingMove(Piece piece) {
    return piece.getMovements().stream()
        .filter(movement -> movement instanceof TakingMovement)
        .findFirst()
        .orElse(null);
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
    final GameEngine gameEngine = new BoardGameEngine(new GameProvider().provide(type));
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

  public static Map<BoardPosition, Piece> getPiecesByColor(Board context, Color team) {
    Map<BoardPosition, Piece> map = context.getPiecesAndPositions();
    return map.entrySet().stream()
        .filter(entry -> entry.getValue() != null && entry.getValue().getPieceColour() == team)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public static Client buildClient(GameView gameView) {

    final Client client =
        new NettyClientBuilder(new JsonDeserializer(), new JsonSerializer())
            .withAddress(new InetSocketAddress("localhost", 8020))
            .withConnectionListener(new ClientConnectionListenerImpl())
                .addMessageListener("InitialState", new TypeReference<>(){}, new InitialStateListener(gameView))
            .build();
    return client;
  }

  public static Server buildServer( Map<String, Color> teamColor) {
    final Server server =
        new NettyServerBuilder(new JsonDeserializer(), new JsonSerializer())
            .withPort(8020)
            .withConnectionListener(new ServerConnectionListenerImpl(teamColor))
                .build();
    return server;
  }
}
