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
  private final RuleValidator ruleValidator;
  private final WinConditionValidator winConditionValidator;

  public ChessGame(Board board, List<GameRule> rules) {
    this.board = board;
    this.rules = rules;
    this.winConditions = filterWinConditions(rules);
    this.ruleValidator = new RuleValidator(rules);
    this.winConditionValidator = new WinConditionValidator(winConditions);
  }

  public void startGame() {
    // As per base Chess, we'll be working with BLACKS and WHITES.
    // Whites always make the first move.
    setTurn(board, Color.WHITE);
  }

  public void verifyEndGame() {
    Color winner = board.getCurrentTurn() == Color.BLACK ? Color.WHITE : Color.BLACK;
    if (winConditionValidator.isGameWon(board))
      System.out.println("Game has been won by: " + winner);
  }

  public void makeMove(Piece piece, Position newPos) {
    if (ruleValidator.isAnyNotValid(board)) return;
    if (piece.getPieceColour() == board.getCurrentTurn())
      return; // Player who has just moved a piece cannot move another (unless Castling)
    if (piece.getPieceColour() == Color.BLACK)
      setTurn(board, Color.WHITE); // If blacks just moved a piece, whites may make the next move
    setTurn(board, Color.BLACK);
    board.updatePiecePosition(newPos, piece);
  }

  // Private methods
  //  private Pair<Map<Position, Piece>> activatePlayers() {
  //    Map<Position, Piece> blacks = getTeam(Color.BLACK, board), whites = getTeam(Color.WHITE,
  // board);
  //    return new Pair<>(whites, blacks);
  //  }
  //
  //  private Map<Position, Piece> getTeam(Color color, Board board) {
  //    Map<Position, Piece> team = new HashMap<>();
  //    for (Map.Entry<Position, Piece> entry : board.getActivePiecesAndPositions().entrySet()) {
  //      if (entry.getValue().getPieceColour() == color) team.put(entry.getKey(),
  // entry.getValue());
  //    }
  //    return team;
  //  }
  // Can't find a usage for these methods yet, might as well delete it

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
