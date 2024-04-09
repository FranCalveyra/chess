package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.rule.Check;
import edu.austral.dissis.chess.rule.WinCondition;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import edu.austral.dissis.chess.validator.WinConditionValidator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ChessGame {
  /** Simulates a real Chess Game. */
  private final Board board;

  // Separate responsibilities, put promoter and turn selector in game
  private final WinConditionValidator winConditionValidator;
  private final List<WinCondition> rules;
  private final List<Check> checkConditions;

  public ChessGame(Board board, List<WinCondition> rules) {
    this.board = board;
    this.checkConditions = filterCheckConditions(rules);
    this.winConditionValidator = new WinConditionValidator(rules);
    this.rules = rules;
  }

  private ChessGame(Board board, List<WinCondition> rules, List<Check> checkConditions) {
    this.board = board;
    this.rules = rules;
    this.checkConditions = checkConditions;
    this.winConditionValidator = new WinConditionValidator(rules);
  } // make it public

  public ChessGame startGame() {
    // As per base Chess, we'll be working with BLACKS and WHITES.
    // Whites always make the first move.
    return new ChessGame(
        new Board(
            board.getPiecesAndPositions(),
            board.getSelector(),
            board.getRows(),
            board.getColumns(),
            board.getTakenPieces(),
            board.changeTurn(board.getSelector().selectTurn(board, board.getTurnNumber())),
            board.getPromoter()),
        getRules(),
        checkConditions);
  }

  public ChessGame makeMove(Position oldPos, Position newPos, PieceType typeForPromotion)
      throws UnallowedMoveException {
    if (winConditionValidator.isGameWon(board)) {
      final Color winner =
          board.getCurrentTurn() == Color.BLACK
              ? Color.WHITE
              : Color.BLACK; // Change it for more colors
      System.out.println("Game has been won by: " + winner);
      return this;
    }
    if (board.pieceAt(oldPos) == null) {
      return this;
    }
    if (board.pieceAt(oldPos).getPieceColour() != board.getCurrentTurn()) {
      return this; // Player who has just moved a piece cannot move another (unless Castling)
    }
    // Validate move here, not in Board

    // Validate check
    if (playIsInCheck(board.getCurrentTurn(), board, oldPos, newPos, typeForPromotion)) {
      return this;
    }

    return new ChessGame(
        board.updatePiecePosition(oldPos, newPos, typeForPromotion), rules, checkConditions);
  }

  public ChessGame makeMove(Position oldPos, Position newPos) throws UnallowedMoveException {
    // If no argument is passed, it promotes to a Queen (maybe change later on)
    return makeMove(oldPos, newPos, PieceType.QUEEN);
  }

  public List<WinCondition> getRules() {
    return rules;
  }

  public Board getBoard() {
    return board;
  }

  // Private methods:
  private boolean playIsInCheck(
      Color currentTurn, Board board, Position oldPos, Position newPos, PieceType typeForPromotion)
      throws UnallowedMoveException {
    Check checkRule = getCheckRuleByTeam(checkConditions, currentTurn);
    Board possiblePlay = board.updatePiecePosition(oldPos, newPos, typeForPromotion);
    return checkRule.isValidRule(possiblePlay);
  }

  private Check getCheckRuleByTeam(List<Check> checkConditions, Color currentTurn) {
    for (Check check : checkConditions) {
      if (check.getTeam() == currentTurn) {
        return check;
      }
    }
    throw new NoSuchElementException();
  }

  private List<Check> filterCheckConditions(List<WinCondition> rules) {
    List<Check> checks = new ArrayList<>();
    for (WinCondition condition : rules) {
      if (condition instanceof Check) {
        checks.add((Check) condition);
      }
    }
    for (Check check : checks) {
      rules.remove(check);
    }
    return checks;
  }
}
