package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.piece.PieceType.QUEEN;
import static edu.austral.dissis.chess.utils.ResultEnum.BLACK_WIN;
import static edu.austral.dissis.chess.utils.ResultEnum.INVALID_MOVE;
import static edu.austral.dissis.chess.utils.ResultEnum.PIECE_TAKEN;
import static edu.austral.dissis.chess.utils.ResultEnum.VALID_MOVE;
import static edu.austral.dissis.chess.utils.ResultEnum.WHITE_WIN;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.promoter.Promoter;
import edu.austral.dissis.chess.rule.Check;
import edu.austral.dissis.chess.rule.WinCondition;
import edu.austral.dissis.chess.turn.TurnSelector;
import edu.austral.dissis.chess.utils.GameResult;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.ResultEnum;
import edu.austral.dissis.chess.validator.WinConditionValidator;
import java.awt.Color;
import java.util.List;

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

  public GameResult makeMove(Position oldPos, Position newPos) {
    // If no argument is passed, it promotes to a Queen (BY DEFAULT)
    return makeMove(oldPos, newPos, QUEEN);
  }

  private GameResult makeMove(Position oldPos, Position newPos, PieceType typeForPromotion) {
    // Check winning at the end
    // Do all necessary checks
    // Invalid positions
    if (outOfBoardBounds(oldPos) || outOfBoardBounds(newPos)) {
      return new GameResult(this, INVALID_MOVE);
    }
    // Fetch pieces
    Piece pieceToMove = board.pieceAt(oldPos);
    Piece pieceToTake = board.pieceAt(newPos);
    // Want to move a piece that's not there
    if (pieceToMove == null) {
      return new GameResult(this, INVALID_MOVE);
    }
    // Invalid move due to piece rules
    if (!pieceToMove.isValidMove(oldPos, newPos, board)) {
      return new GameResult(this, INVALID_MOVE);
    }
    ChessGame finalGame;
    Board finalBoard;
    // There is a piece at the desired position
    if (pieceToTake != null) {
      if (pieceToTake.getPieceColour() == pieceToMove.getPieceColour()) { // Is of my own team
        return new GameResult(this, INVALID_MOVE);
      }
      finalBoard =
          board
              .removePieceAt(oldPos)
              .removePieceAt(newPos)
              .addPieceAt(
                  newPos, pieceToMove.hasNotMoved() ? pieceToMove.changeMoveState() : pieceToMove);
      finalBoard =
          promoteIfAble(
              finalBoard, newPos, finalBoard.pieceAt(newPos).getPieceColour(), typeForPromotion);
      finalGame =
          new ChessGame(
              finalBoard,
              rules,
              checkConditions,
              promoter,
              selector,
              selector.selectTurn(finalBoard, turnNumber + 1),
              turnNumber + 1);
      return new GameResult(finalGame, PIECE_TAKEN);
    }
    finalBoard =
        board
            .removePieceAt(oldPos)
            .addPieceAt(
                newPos, pieceToMove.hasNotMoved() ? pieceToMove.changeMoveState() : pieceToMove);
    finalBoard =
        promoteIfAble(
            finalBoard, newPos, finalBoard.pieceAt(newPos).getPieceColour(), typeForPromotion);
    finalGame =
        new ChessGame(
            finalBoard,
            rules,
            checkConditions,
            promoter,
            selector,
            selector.selectTurn(finalBoard, turnNumber + 1),
            turnNumber + 1);
    // End of code
    if (possiblePlayInCheck(currentTurn, finalBoard)) {
      return new GameResult(this, INVALID_MOVE);
    }
    if (winConditionValidator.isGameWon(finalBoard)) {
      ResultEnum winner = currentTurn == Color.BLACK ? BLACK_WIN : WHITE_WIN;
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

  public Promoter getPromoter() {
    return promoter;
  }

  public TurnSelector getSelector() {
    return selector;
  }

  // Private methods
  private boolean outOfBoardBounds(Position pos) {
    int i = pos.getRow();
    int j = pos.getColumn();
    return i >= board.getRows() || i < 0 || j >= board.getColumns() || j < 0;
  }

  private boolean possiblePlayInCheck(Color currentTurn, Board board) {
    Check checkRule =
        checkConditions.stream()
            .filter(rule -> rule.getTeam() == currentTurn)
            .findAny()
            .orElse(null);
    assert checkRule != null;
    return checkRule.isValidRule(board);
  }

  private List<Check> filterCheckConditions(List<WinCondition> rules) {
    List<Check> checks =
        rules.stream().filter(rule -> rule instanceof Check).map(rule -> (Check) rule).toList();
    rules.removeIf(item -> item instanceof Check);
    return checks;
  }

  private Board promoteIfAble(
      Board board, Position position, Color color, PieceType typeForPromotion) {
    if (promoter.canPromote(position, board) || promoter.hasToPromote(board, color)) {
      return promoter.promote(position, typeForPromotion, board);
    }
    return board;
  }
}
