package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.rule.GameRule;
import edu.austral.dissis.chess.rule.WinCondition;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.WinConditionValidator;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ChessGame {
  /**
   * Simulates a real Chess Game
   */
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

  private void setTurn(Board board, Map<Position, Piece> whites) {
    //TODO
  }

  public void endGame() {
    //TODO
//    WinConditionValidator validator = new WinConditionValidator();
//    if (validator.isGameWon(board)) return;
  }

  public void makeMove(Piece piece, Position newPos) {
    if (piece.getPieceColour() != board.getCurrentTurn()) return;
    //TODO
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

}
