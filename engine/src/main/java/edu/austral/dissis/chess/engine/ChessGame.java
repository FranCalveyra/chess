package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.rule.Check;
import edu.austral.dissis.chess.rule.GameRule;
import edu.austral.dissis.chess.rule.WinCondition;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import edu.austral.dissis.chess.validator.RuleValidator;
import edu.austral.dissis.chess.validator.WinConditionValidator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChessGame {
  /** Simulates a real Chess Game. */
  // TODO: make everything immutable once the game works
  private final Board board;

  private final RuleValidator ruleValidator;
  private final WinConditionValidator winConditionValidator;
  private final List<Check> checkRules; // TODO: Check later

  public ChessGame(Board board, List<GameRule> rules) {
    this.board = board;
    Set<WinCondition> winConditions = filterWinConditions(rules);
    this.ruleValidator = new RuleValidator(rules);
    this.winConditionValidator = new WinConditionValidator(winConditions);
    this.checkRules = filterChecks(rules);
  }

  public void startGame() {
    // As per base Chess, we'll be working with BLACKS and WHITES.
    // Whites always make the first move.
    board.changeTurn(Color.WHITE);
  }

  public void verifyEndGame() {
    Color winner = board.getCurrentTurn() == Color.BLACK ? Color.WHITE : Color.BLACK;
    if (winConditionValidator.isGameWon(board)) {
      System.out.println("Game has been won by: " + winner);
    }
  }

  public void makeMove(Piece piece, Position newPos) throws UnallowedMoveException {
    if (ruleValidator.isAnyNotValid(board)) {
      return;
    }
    if (piece.getPieceColour() != board.getCurrentTurn()) {
      return; // Player who has just moved a piece cannot move another (unless Castling)
    }
    board.updatePiecePosition(newPos, piece);
    verifyEndGame();
  }

  // Private methods
  private Set<WinCondition> filterWinConditions(List<GameRule> rules) {
    Set<WinCondition> conditions = new HashSet<>();
    for (GameRule rule : rules) {
      if (rule instanceof WinCondition) {
        conditions.add((WinCondition) rule);
      }
    }
    for (WinCondition condition : conditions) {
      rules.remove(condition);
    }
    return conditions;
  }

  private List<Check> filterChecks(List<GameRule> rules) {
    List<Check> checks = new ArrayList<>();
    for (GameRule rule : rules) {
      if (rule instanceof Check) {
        checks.add((Check) rule);
      }
    }
    for (Check check : checks) {
      rules.remove(check);
    }
    return checks;
  }
}
