package edu.austral.dissis.common.engine;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.promoters.Promoter;
import edu.austral.dissis.common.rules.premovement.validators.PreMovementValidator;
import edu.austral.dissis.common.rules.winconds.WinCondition;
import edu.austral.dissis.common.turn.TurnSelector;
import edu.austral.dissis.common.utils.Pair;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.move.MoveExecutor;
import edu.austral.dissis.common.utils.result.gameresult.BoardGameResult;
import edu.austral.dissis.common.utils.result.playresult.*;
import edu.austral.dissis.common.validators.WinConditionValidator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class BoardGame implements Game {
  /** Simulates a real Chess Game. */
  private final Board board;

  private final WinConditionValidator winConditionValidator;
  private final List<WinCondition> winConditions;
  private final Promoter promoter;
  private final TurnSelector turnSelector;
  private final MoveExecutor executor;
  private final PreMovementValidator preMovementValidator;
  private final Pair<BoardGame, GameMove> previousState;

  public BoardGame(
      @NotNull Board board,
      @NotNull List<WinCondition> winConditions,
      Promoter promoter,
      TurnSelector turnSelector,
      PreMovementValidator preMovementValidator) {
    // Should be first instance
    this.board = board;
    this.winConditions = winConditions;
    this.winConditionValidator = new WinConditionValidator(winConditions);
    this.promoter = promoter;
    this.turnSelector = turnSelector;
    this.preMovementValidator = preMovementValidator;
    this.executor = new MoveExecutor();
    this.previousState = null;
  }

  private BoardGame(
      @NotNull Board board,
      @NotNull List<WinCondition> winConditions,
      Promoter promoter,
      TurnSelector turnSelector,
      PreMovementValidator preMovementValidator,
      Pair<BoardGame, GameMove> previousState) {
    this.board = board;
    this.winConditions = winConditions;
    this.winConditionValidator = new WinConditionValidator(winConditions);
    this.promoter = promoter;
    this.turnSelector = turnSelector;
    this.preMovementValidator = preMovementValidator;
    this.executor = new MoveExecutor();
    this.previousState = previousState;
  }

  @Override
  public BoardGameResult makeMove(GameMove move) {
    // Check winning at the end
    // Do all necessary checks
    // PreMovementRules should be valid
    Pair<BoardGame, GameMove> prev = new Pair<>(this, move);
    PlayResult preMovementValidity = preMovementValidator.getMoveValidity(move, this);
    if (preMovementValidity.getClass() == InvalidPlay.class) {
      return new BoardGameResult(this, preMovementValidity);
    }
    // Once valid, execute move
    final Piece pieceToMove = board.pieceAt(move.from());
    Pair<Board, PlayResult> result = new Pair<>(board, new InvalidPlay("any"));

    final List<GameMove> playToExecute = pieceToMove.getPlay(move, board);
    List<PlayResult> playResults = new ArrayList<>();

    for (GameMove moveToDo : playToExecute) {
      result = executor.executeMove(moveToDo.from(), moveToDo.to(), result.first(), promoter);
      playResults.add(result.second());
    }

    // Declare final variables
    Board finalBoard = result.first();
    // Compound movements hardcode, may need to change
    if (playResults.contains(new PieceTaken())) {
      result = new Pair<>(finalBoard, new PieceTaken());
    }
    // Replaces priority
    if (playResults.contains(new PromotedPiece())) {
      result = new Pair<>(finalBoard, new PromotedPiece());
    }
    TurnSelector nextSelector = turnSelector.changeTurn(result.second());
    BoardGame finalGame =
        new BoardGame(
            finalBoard, winConditions, promoter, nextSelector, preMovementValidator, prev);

    if (winConditionValidator.isGameWon(finalBoard)) {
      Color winner = turnSelector.getCurrentTurn();
      return new BoardGameResult(finalGame, new GameWon(winner));
    }

    // Get the resulting game at last
    return new BoardGameResult(finalGame, result.second()); // Second represents the moveResult
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

  public PreMovementValidator getPreMovementValidator() {
    return preMovementValidator;
  }

  public MoveExecutor getMoveExecutor() {
    return executor;
  }

  public Pair<BoardGame, GameMove> getPreviousState() {
    return previousState;
  }
}
