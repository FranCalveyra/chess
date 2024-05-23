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
import edu.austral.dissis.common.utils.result.playresult.GameWon;
import edu.austral.dissis.common.utils.result.playresult.InvalidPlay;
import edu.austral.dissis.common.utils.result.playresult.PieceTaken;
import edu.austral.dissis.common.utils.result.playresult.PlayResult;
import edu.austral.dissis.common.utils.result.playresult.PromotedPiece;
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
  }

  @Override
  public BoardGameResult makeMove(GameMove move) {
    // Check winning at the end
    // Do all necessary checks
    // PreMovementRules should be valid
    if (!preMovementValidator.isValidRule(move, this)) {
      return new BoardGameResult(this, new InvalidPlay(preMovementValidator.getFailureMessage()));
    }
    // Once valid, execute move
    final Piece pieceToMove = board.pieceAt(move.from());
    Pair<Board, PlayResult> result = new Pair<>(board, new InvalidPlay(""));

    final List<GameMove> playToExecute = pieceToMove.getPlay(move, board);
    List<PlayResult> playResults = new ArrayList<>();

    for (GameMove moveToDo : playToExecute) {
      result = executor.executeMove(moveToDo.from(), moveToDo.to(), result.first(), promoter);
      playResults.add(result.second());
    }

    // Declare final variables
    Board finalBoard = result.first();
    // Compound movements hardcode, may need to change for a priority handling
    if (playResults.contains(new PieceTaken())) {
      result = new Pair<>(finalBoard, new PieceTaken());
    }
    // Replaces priority
    if (playResults.contains(new PromotedPiece())) {
      result = new Pair<>(finalBoard, new PromotedPiece());
    }
    // Stuff to return
    TurnSelector nextSelector = turnSelector.changeTurn(move, finalBoard, result.second());
    BoardGame finalGame =
        new BoardGame(finalBoard, winConditions, promoter, nextSelector, preMovementValidator);

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
}
