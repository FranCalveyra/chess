package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.promoters.Promoter;
import edu.austral.dissis.chess.rules.winconds.Check;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.utils.result.ChessGameResult;
import edu.austral.dissis.chess.validators.WinConditionValidator;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.rules.premovement.validators.PreMovementValidator;
import edu.austral.dissis.common.rules.winconds.WinCondition;
import edu.austral.dissis.common.turn.TurnSelector;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.move.MoveExecutor;
import edu.austral.dissis.common.utils.result.GameWon;
import edu.austral.dissis.common.utils.result.InvalidPlay;
import edu.austral.dissis.common.utils.result.PieceTaken;
import edu.austral.dissis.common.utils.result.PlayResult;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ChessGame implements BoardGame {
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

  @Override
  public ChessGameResult makeMove(GameMove move) {
    // Check winning at the end
    // Do all necessary checks
    // PreMovementRules should be valid
    PlayResult preMovementValidity = preMovementValidator.getMoveValidity(move, this);
    if (preMovementValidity.getClass() == InvalidPlay.class) {
      return new ChessGameResult(this, preMovementValidity);
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
    TurnSelector nextSelector = turnSelector.changeTurn(result.second());
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
      return new ChessGameResult(finalGame, new GameWon(winner));
    }

    // Get the resulting game at last
    return new ChessGameResult(finalGame, result.second()); // Second represents the moveResult
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
