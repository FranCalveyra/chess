package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.rule.GameRule;
import edu.austral.dissis.chess.utils.Position;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessGame {
  private final Board board;
  private final List<GameRule> rules;
  private boolean isEnded;

  public ChessGame(Board board, List<GameRule> rules) {
    this.board = board;
    this.rules = rules;
  }

  public void startGame() {
    Pair<Map<Position, Piece>> teams = activatePlayers();
    Map<Position, Piece> whites = teams.getFirst(), blacks = teams.getSecond();
    setTurn(board, whites);
  }

  private void setTurn(Board board, Map<Position, Piece> whites) {}

  public void endGame() {}

  public void makeMove(Piece piece, Position newPos, Color currentPlayer) {
    if (piece.getPieceColour() != currentPlayer) return;
  }

  // Private
  private Pair<Map<Position, Piece>> activatePlayers() {
    Map<Position, Piece> blacks = getTeam(Color.BLACK, board), whites = getTeam(Color.WHITE, board);
    return new Pair<>(whites, blacks);
  }

  private Map<Position, Piece> getTeam(Color color, Board board) {
    Map<Position, Piece> team = new HashMap<>();
    for (Map.Entry<Position, Piece> entry : board.getActivePiecesAndPositions().entrySet()) {
      if (entry.getValue().getPieceColour() == color) team.put(entry.getKey(), entry.getValue());
    }
    ;
    return team;
  }

  private class Pair<T> {
    private final T first;
    private final T second;

    Pair(T first, T second) {
      this.first = first;
      this.second = second;
    }

    public T getFirst() {
      return first;
    }

    public T getSecond() {
      return second;
    }
  }
}
