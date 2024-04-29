package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.promoters.Promoter;
import edu.austral.dissis.chess.rules.Check;
import edu.austral.dissis.chess.rules.PreMovementRule;
import edu.austral.dissis.chess.rules.WinCondition;
import edu.austral.dissis.chess.selectors.TurnSelector;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessMoveResult;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.utils.GameResult;
import edu.austral.dissis.chess.utils.MoveExecutor;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.validators.PreMovementValidator;
import edu.austral.dissis.chess.validators.WinConditionValidator;
import java.awt.Color;
import java.util.List;
import org.jetbrains.annotations.NotNull;

import static edu.austral.dissis.chess.utils.ChessMoveResult.*;

public class ChessGame {
  /** Simulates a real Chess Game. */
  private final Board board;

  private final WinConditionValidator winConditionValidator;
  private final List<WinCondition> winConditions;
  private final List<Check> checkConditions;
  private final Promoter promoter;
  private final TurnSelector selector;
  private final Color currentTurn;
  private final int turnNumber;
  private final MoveExecutor executor;
  private final PreMovementValidator preMovementValidator;

  public ChessGame(
          @NotNull Board board,
          @NotNull List<WinCondition> winConditions,
          List<Check> checkConditions,
          Promoter promoter,
          TurnSelector selector,
          Color currentTurn,PreMovementValidator preMovementValidator) {
    // Should be first instance
    this.board = board;
    this.winConditions = winConditions;
    this.checkConditions = checkConditions;
    this.winConditionValidator = new WinConditionValidator(winConditions);
    this.promoter = promoter;
    this.selector = selector;
    this.currentTurn = currentTurn;
      this.preMovementValidator = preMovementValidator;
      this.turnNumber = 0;
    executor = new MoveExecutor();
  }

  private ChessGame(
          Board board,
          List<WinCondition> winConditions,
          List<Check> checkConditions,
          Promoter promoter,
          TurnSelector selector,
          Color currentTurn,
          int turnNumber, PreMovementValidator preMovementValidator) {
    // Represents state passage
    this.board = board;
    this.winConditions = winConditions;
    this.checkConditions = checkConditions;
    this.winConditionValidator = new WinConditionValidator(winConditions);
    this.promoter = promoter;
    this.selector = selector;
    this.turnNumber = turnNumber;
    this.currentTurn = currentTurn;
      this.preMovementValidator = preMovementValidator;
      executor = new MoveExecutor();
  }

  public static ChessGame createChessGame(
      Board board,
      List<WinCondition> rules,
      List<Check> checkConditions,
      Promoter promoter,
      TurnSelector selector,
      Color currentTurn,
      int turnNumber, PreMovementValidator preMovementValidator) {
    return new ChessGame(
        board, rules, checkConditions, promoter, selector, currentTurn, turnNumber,preMovementValidator );
  }

  public GameResult makeMove(ChessMove move) {
    return makeMove(move.from(), move.to());
  }

  private GameResult makeMove(ChessPosition oldPos, ChessPosition newPos) {
    // Check winning at the end
    // Do all necessary checks
    // PreMovementRules should be valid
    if(preMovementValidator.getMoveValidity(new ChessMove(oldPos,newPos), this) == INVALID_MOVE){
      return new GameResult(this, INVALID_MOVE);
    }
    Piece pieceToMove = board.pieceAt(oldPos);
    Pair<Board, ChessMoveResult> resultPair = new Pair<>(board, INVALID_MOVE);
    final List<ChessMove> playToExecute = pieceToMove.getPlay(oldPos, newPos, board);
    for (ChessMove move : playToExecute) {
      resultPair = executor.executeMove(move.from(), move.to(), resultPair.first(), promoter);
    }
    // Execute move
    Board finalBoard = resultPair.first();
    Color nextTurn = selector.selectTurn(turnNumber + 1);
    ChessGame finalGame =
        createChessGame(
            finalBoard,
            winConditions,
            checkConditions,
            promoter,
            selector,
            nextTurn,
            turnNumber + 1, preMovementValidator);

    // If this play makes your own team in check, it shouldn't be executed

    // If play
    if (winConditionValidator.isGameWon(finalBoard)) {
      ChessMoveResult winner =
          currentTurn == Color.BLACK ? BLACK_WIN : WHITE_WIN; // Hardcoded, need to change
      return new GameResult(finalGame, winner);
    }
    // Get the resulting game at last
    return new GameResult(finalGame, resultPair.second()); // Second represents the moveResult
  }

  // Getters
  public List<WinCondition> getWinConditions() {
    return winConditions;
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

  public List<Check> getCheckConditions() {
    return checkConditions;
  }

  public int getTurnNumber() {
    return turnNumber;
  }


  public PreMovementValidator getPreMovementValidator() {
    return preMovementValidator;
  }

  public MoveExecutor getMoveExecutor() {
    return executor;
  }

  // Private methods
  private boolean outOfBoardBounds(ChessPosition pos) {
    int i = pos.getRow();
    int j = pos.getColumn();
    return i >= board.getRows() || i < 0 || j >= board.getColumns() || j < 0;
  }

  private boolean possiblePlayInCheck(Color currentTurn, Board board) {
    Check checkRule =
        checkConditions.stream()
            .filter(rule -> rule.team() == currentTurn)
            .findAny()
            .orElse(null);
    assert checkRule != null;
    return checkRule.isValidRule(board);
  }
}
