package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.promoters.Promoter;
import edu.austral.dissis.chess.rules.Check;
import edu.austral.dissis.chess.rules.WinCondition;
import edu.austral.dissis.chess.turn.TurnSelector;
import edu.austral.dissis.chess.utils.MoveExecutor;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.result.ChessMoveResult;
import edu.austral.dissis.chess.utils.result.GameResult;
import edu.austral.dissis.chess.utils.result.GameWon;
import edu.austral.dissis.chess.utils.result.InvalidMove;
import edu.austral.dissis.chess.validators.PreMovementValidator;
import edu.austral.dissis.chess.validators.WinConditionValidator;
import java.awt.Color;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ChessGame {
  /** Simulates a real Chess Game. */
  private final Board board;

  private final WinConditionValidator winConditionValidator;
  private final List<WinCondition> winConditions;
  private final List<Check> checkConditions; // FOUND A USE OUTSIDE GAME
  private final Promoter promoter;
  private final TurnSelector turnSelector;
  private final MoveExecutor executor;
  private final PreMovementValidator preMovementValidator;

  public ChessGame(
      @NotNull Board board,
      @NotNull List<WinCondition> winConditions,
      List<Check> checkConditions,
      Promoter promoter,
      TurnSelector turnSelector,
      PreMovementValidator preMovementValidator) {
    // Should be first instance
    this.board = board;
    this.winConditions = winConditions;
    this.checkConditions = checkConditions;
    this.winConditionValidator = new WinConditionValidator(winConditions);
    this.promoter = promoter;
    this.turnSelector = turnSelector;
    this.preMovementValidator = preMovementValidator;
    this.executor = new MoveExecutor();
  }

  public GameResult makeMove(ChessMove move) {
    // Check winning at the end
    // Do all necessary checks
    // PreMovementRules should be valid
    ChessMoveResult preMovementValidity = preMovementValidator.getMoveValidity(move, this);
    if (preMovementValidity.getClass() == InvalidMove.class) {
      return new GameResult(this, preMovementValidity);
    }
    // Once valid, execute move
    final Piece pieceToMove = board.pieceAt(move.from());
    Pair<Board, ChessMoveResult> result = new Pair<>(board, new InvalidMove("any"));
    final List<ChessMove> playToExecute = pieceToMove.getPlay(move, board);
    for (ChessMove moveToDo : playToExecute) {
      result = executor.executeMove(moveToDo.from(), moveToDo.to(), result.first(), promoter);
    }
    // Declare final variables
    Board finalBoard = result.first();
    TurnSelector nextSelector = turnSelector.changeTurn();
    ChessGame finalGame =
        new ChessGame(
            finalBoard,
            winConditions,
            checkConditions,
            promoter,
            nextSelector,
            preMovementValidator);

    if (winConditionValidator.isGameWon(finalBoard)) {
      Color winner = turnSelector.getCurrentTurn();
      return new GameResult(finalGame, new GameWon(winner));
    }
    // Get the resulting game at last
    return new GameResult(finalGame, result.second()); // Second represents the moveResult
  }

  // Getters
  public List<WinCondition> getWinConditions() {
    return winConditions;
  }

  public Board getBoard() {
    return board;
  }

  public Color getCurrentTurn() {
    return turnSelector.getCurrentTurn();
  }

  public Promoter getPromoter() {
    return promoter;
  }

  public TurnSelector getTurnSelector() {
    return turnSelector;
  }

  public List<Check> getCheckConditions() {
    return checkConditions;
  }

  public PreMovementValidator getPreMovementValidator() {
    return preMovementValidator;
  }

  public MoveExecutor getMoveExecutor() {
    return executor;
  }
}
