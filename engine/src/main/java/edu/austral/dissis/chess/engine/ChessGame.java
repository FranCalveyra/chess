package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.rule.GameRule;
import edu.austral.dissis.chess.rule.WinCondition;
import edu.austral.dissis.chess.utils.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ChessGame {
  /** Simulates a real Chess Game */
  private final Board board;

  private final List<GameRule> rules;
  private final Set<WinCondition> winConditions;

  public ChessGame(Board board, List<GameRule> rules) {
    this.board = board;
    this.rules = rules;
    this.winConditions = filterWinConditions(rules);
  }

  public void startGame() {
    Pair<Map<Position, Piece>> teams = activatePlayers();
    Map<Position, Piece> whites = teams.getFirst(), blacks = teams.getSecond();
    setTurn(board, Color.WHITE);
  }

  public void endGame() {
    // TODO
    WinConditionValidator validator = new WinConditionValidator(winConditions);
    if (validator.isGameWon(board))
      System.out.println("Game has been won by: " + board.getCurrentTurn());
  }

  public void makeMove(Piece piece, Position newPos) {
    RuleValidator validator = new RuleValidator(rules);
    if (validator.isAnyNotValid(board)) return;
    if (piece.getPieceColour() == board.getCurrentTurn())
      return; // Player who has just moved a piece cannot move another (unless
    if (piece.getPieceColour() == Color.BLACK)
      setTurn(board, Color.WHITE); // As per base Chess, we'll be working with BLACK and WHITE
    setTurn(board, Color.BLACK);
    board.updatePiecePosition(newPos, piece);
  }

  // Private methods
  private Pair<Map<Position, Piece>> activatePlayers() {
    Map<Position, Piece> blacks = getTeam(Color.BLACK, board), whites = getTeam(Color.WHITE, board);
    return new Pair<>(whites, blacks);
  }

  private Map<Position, Piece> getTeam(Color color, Board board) {
    Map<Position, Piece> team = new HashMap<>();
    for (Map.Entry<Position, Piece> entry : board.getActivePiecesAndPositions().entrySet()) {
      if (entry.getValue().getPieceColour() == color) team.put(entry.getKey(), entry.getValue());
    }
    return team;
  }

  private Set<WinCondition> filterWinConditions(List<GameRule> rules) {
    Set<WinCondition> conditions = new HashSet<>();
    for (GameRule rule : rules) {
      if (rule instanceof WinCondition) {
        conditions.add((WinCondition) rule);
        rules.remove(rule);
      }
    }
    return conditions;
  }

  private void setTurn(Board board, Color team) {
    board.changeTurn(team);
  }
}
