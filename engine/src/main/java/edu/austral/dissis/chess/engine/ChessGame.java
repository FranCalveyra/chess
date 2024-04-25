package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.utils.ResultEnum.BLACK_WIN;
import static edu.austral.dissis.chess.utils.ResultEnum.INVALID_MOVE;
import static edu.austral.dissis.chess.utils.ResultEnum.PIECE_TAKEN;
import static edu.austral.dissis.chess.utils.ResultEnum.VALID_MOVE;
import static edu.austral.dissis.chess.utils.ResultEnum.WHITE_WIN;
import static java.awt.Color.WHITE;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.promoter.Promoter;
import edu.austral.dissis.chess.rule.Check;
import edu.austral.dissis.chess.rule.WinCondition;
import edu.austral.dissis.chess.turn.TurnSelector;
import edu.austral.dissis.chess.utils.GameResult;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.ResultEnum;
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
  private final Promoter promoter;
  private final TurnSelector selector;
  private final Color currentTurn;
  private final int turnNumber;

  public ChessGame(
      Board board,
      List<WinCondition> rules,
      Promoter promoter,
      TurnSelector selector,
      Color currentTurn) {
    // Should be first instance
    this.board = board;
    this.checkConditions = filterCheckConditions(rules);
    this.winConditionValidator = new WinConditionValidator(rules);
    this.rules = rules;
    this.promoter = promoter;
    this.selector = selector;
    this.currentTurn = currentTurn;
    this.turnNumber = 0;
  }

  private ChessGame(
      Board board,
      List<WinCondition> rules,
      List<Check> checkConditions,
      Promoter promoter,
      TurnSelector selector,
      Color currentTurn,
      int turnNumber) {
    // Represents state passage
    this.board = board;
    this.rules = rules;
    this.checkConditions = checkConditions;
    this.winConditionValidator = new WinConditionValidator(rules);
    this.promoter = promoter;
    this.selector = selector;
    this.turnNumber = turnNumber;
    this.currentTurn = currentTurn;
  } // make it public

  public ChessGame startGame() {
    // As per base Chess, we'll be working with BLACKS and WHITES.
    // Whites always make the first move.
    return new ChessGame(
        new Board(
            board.getPiecesAndPositions(),
            board.getRows(),
            board.getColumns(),
            board.getTakenPieces()),
        getRules(),
        checkConditions,
        promoter,
        selector,
        WHITE,
        turnNumber);
  }

  public GameResult makeMove(Position oldPos, Position newPos) throws UnallowedMoveException {
    // If no argument is passed, it promotes to a Queen (BY DEFAULT)
    return makeMove(oldPos, newPos, PieceType.QUEEN);
  }

  private GameResult makeMove(Position oldPos, Position newPos, PieceType typeForPromotion)
      throws UnallowedMoveException {
    // Check winning at the end
    // Do all necessary checks
    if (outOfBoardBounds(oldPos) || outOfBoardBounds(newPos)) {
      return new GameResult(this, INVALID_MOVE);
    }

    Piece pieceToMove = board.pieceAt(oldPos);
    Piece pieceToTake = board.pieceAt(newPos);
    // Want to move a piece that's not there
    if (pieceToMove == null) {
      return new GameResult(this, INVALID_MOVE);
    }
    // Invalid move due to piece rules
    if (!pieceToMove.checkValidMove(oldPos, newPos, board)) {
      return new GameResult(this, INVALID_MOVE);
    }
    // To-return
    ChessGame finalGame;
    Board newBoard;
    if (pieceToTake != null) {
      if (pieceToTake.getPieceColour() == pieceToMove.getPieceColour()) {
        return new GameResult(this, INVALID_MOVE);
      } else {
        newBoard =
            board.removePieceAt(oldPos).removePieceAt(newPos).addPieceAt(newPos, pieceToMove);
        newBoard = promoteIfAble(newBoard, pieceToMove.getPieceColour(), newPos, typeForPromotion);
        finalGame =
            new ChessGame(
                newBoard,
                rules,
                checkConditions,
                promoter,
                selector,
                selector.selectTurn(newBoard, turnNumber + 1),
                turnNumber);
        return new GameResult(finalGame, PIECE_TAKEN);
      }
    } else {
      newBoard =
          board
              .removePieceAt(oldPos)
              .addPieceAt(
                  newPos, pieceToMove.hasNotMoved() ? pieceToMove.changeMoveState() : pieceToMove);
      newBoard = promoteIfAble(newBoard, pieceToMove.getPieceColour(), newPos, typeForPromotion);
      finalGame =
          new ChessGame(
              newBoard,
              rules,
              checkConditions,
              promoter,
              selector,
              selector.selectTurn(newBoard, turnNumber + 1),
              turnNumber);
    }
    // Possible play leaves my own team in check
    if (playIsInCheck(currentTurn, board, oldPos, newPos)) {
      return new GameResult(this, INVALID_MOVE);
    }
    // End of the code
    if (winConditionValidator.isGameWon(board)) {
      ResultEnum winner = currentTurn == WHITE ? BLACK_WIN : WHITE_WIN;
      return new GameResult(finalGame, winner);
    }
    return new GameResult(finalGame, VALID_MOVE);
  }

  // Getters
  public List<WinCondition> getRules() {
    return rules;
  }

  public Board getBoard() {
    return board;
  }

  public Color getCurrentTurn() {
    return currentTurn;
  }

  // Private methods:
  private boolean playIsInCheck(Color currentTurn, Board board, Position oldPos, Position newPos)
      throws UnallowedMoveException {
    Check checkRule = getCheckRuleByTeam(checkConditions, currentTurn);
    Board possiblePlay = board.updatePiecePosition(oldPos, newPos);
    return checkRule.isValidRule(possiblePlay);
  }

  private Check getCheckRuleByTeam(List<Check> checkConditions, Color turn) {
    for (Check check : checkConditions) {
      if (check.getTeam() == turn) {
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
    return checks; // Change to uses of filter and map functions
  }

  private Board promoteIfAble(
      Board newBoard, Color pieceColour, Position newPos, PieceType typeForPromotion) {
    if (promoter.hasToPromote(newBoard, pieceColour) || promoter.canPromote(newPos, newBoard)) {
      return promoter.promote(newPos, typeForPromotion, newBoard);
    }
    return newBoard;
  }

  private boolean outOfBoardBounds(Position pos) {
    return pos.getRow() >= board.getRows()
        || pos.getColumn() >= board.getColumns()
        || pos.getRow() < 0
        || pos.getColumn() < 0;
  }

  public Promoter getPromoter() {
    return promoter;
  }

  public TurnSelector getSelector() {
    return selector;
  }
}
