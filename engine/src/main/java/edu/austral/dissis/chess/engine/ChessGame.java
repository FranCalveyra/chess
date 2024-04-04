package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.rule.GameRule;
import edu.austral.dissis.chess.rule.WinCondition;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.RuleValidator;
import edu.austral.dissis.chess.utils.WinConditionValidator;
import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChessGame {
  /** Simulates a real Chess Game. */
  private final Board board;

  private final RuleValidator ruleValidator;
  private final WinConditionValidator winConditionValidator;

  public ChessGame(Board board, List<GameRule> rules) {
    this.board = board;
    Set<WinCondition> winConditions = filterWinConditions(rules);
    this.ruleValidator = new RuleValidator(rules);
    System.out.println(rules);
    this.winConditionValidator = new WinConditionValidator(winConditions);
  }

  public void startGame() {
    // As per base Chess, we'll be working with BLACKS and WHITES.
    // Whites always make the first move.
    setTurn(board, Color.WHITE);
  }

  public void verifyEndGame() {
    Color winner = board.getCurrentTurn() == Color.BLACK ? Color.WHITE : Color.BLACK;
    if (winConditionValidator.isGameWon(board)) {
      System.out.println("Game has been won by: " + winner);
    }
  }

  public void makeMove(Piece piece, Position newPos) {
    if (ruleValidator.isAnyNotValid(board)) {
      return;
    }
    if (piece.getPieceColour() != board.getCurrentTurn()) {
      return; // Player who has just moved a piece cannot move another (unless Castling)
    }
    if(piece.getPieceColour() == Color.BLACK) {
      setTurn(board, Color.WHITE);
    }
    else {
      setTurn(board, Color.BLACK); // If blacks just moved a piece, whites may make the next move
    }
    board.updatePiecePosition(newPos, piece);
    verifyEndGame();
  }

  private Set<WinCondition> filterWinConditions(List<GameRule> rules) {
    Set<WinCondition> conditions = new HashSet<>();
    for (GameRule rule : rules) {
      if (rule instanceof WinCondition) {
        conditions.add((WinCondition) rule);
      }
    }
    for(WinCondition condition: conditions){
      rules.remove(condition);
    }
    return conditions;
  }

  private void setTurn(Board board, Color team) {
    board.changeTurn(team);
  }
}
