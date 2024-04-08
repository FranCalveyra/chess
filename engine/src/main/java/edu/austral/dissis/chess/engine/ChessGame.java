package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.rule.BorderGameRule;
import edu.austral.dissis.chess.rule.WinCondition;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import edu.austral.dissis.chess.validator.RuleValidator;
import edu.austral.dissis.chess.validator.WinConditionValidator;
import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChessGame {
  /** Simulates a real Chess Game. */
  private final Board board;

  private final RuleValidator ruleValidator;
  private final WinConditionValidator winConditionValidator;
  private final List<BorderGameRule> rules;

  public ChessGame(Board board, List<BorderGameRule> rules) {
    this.board = board;
    Set<WinCondition> winConditions = filterWinConditions(rules);
    this.ruleValidator = new RuleValidator(rules);
    this.winConditionValidator = new WinConditionValidator(winConditions);
    this.rules = rules;
  }

  public ChessGame startGame() {
    // As per base Chess, we'll be working with BLACKS and WHITES.
    // Whites always make the first move.
    return new ChessGame(
        new Board(
            board.getActivePiecesAndPositions(),
            board.getSelector(),
            board.getRows(),
            board.getColumns(),
            board.getTakenPieces(),
            board.changeTurn(Color.WHITE)),
        rules);
  }

  public void verifyEndGame() throws UnallowedMoveException {
    final Color winner = board.getCurrentTurn() == Color.BLACK ? Color.WHITE : Color.BLACK;
    if (winConditionValidator.isGameWon(board)) {
      System.out.println("Game has been won by: " + winner);
    }
  }

  public Board makeMove(Piece piece, Position newPos)
      throws UnallowedMoveException {
    if (ruleValidator.isAnyActive(board)) {
      return board;
    }
    if (piece.getPieceColour() != board.getCurrentTurn()) {
      return board; // Player who has just moved a piece cannot move another (unless Castling)
    }
    verifyEndGame();
    return board.updatePiecePosition(newPos, piece);
  }

  // Private methods
  private Set<WinCondition> filterWinConditions(List<BorderGameRule> rules) {
    final Set<WinCondition> conditions = new HashSet<>();
    for (BorderGameRule rule : rules) {
      if (rule instanceof WinCondition) {
        conditions.add((WinCondition) rule);
      }
    }
    for (WinCondition condition : conditions) {
      rules.remove(condition);
    }
    return conditions;
  }

  public List<BorderGameRule> getRules() {
    return rules;
  }

  public Board getBoard() {
    return board;
  }
}
