package edu.austral.dissis.chess.engine;

import static edu.austral.dissis.chess.utils.MoveResult.BLACK_WIN;
import static edu.austral.dissis.chess.utils.MoveResult.VALID_MOVE;
import static edu.austral.dissis.chess.utils.MoveResult.INVALID_MOVE;
import static edu.austral.dissis.chess.utils.MoveResult.WHITE_WIN;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.promoter.Promoter;
import edu.austral.dissis.chess.utils.MoveResult;
import edu.austral.dissis.chess.winConditions.Check;
import edu.austral.dissis.chess.winConditions.WinCondition;
import edu.austral.dissis.chess.turn.TurnSelector;
import edu.austral.dissis.chess.utils.GameResult;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.validator.WinConditionValidator;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.List;

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

  public ChessGame(
          @NotNull Board board,
          @NotNull List<WinCondition> winConditions,
          List<Check> checkConditions,
          Promoter promoter,
          TurnSelector selector,
          Color currentTurn) {
    // Should be first instance
    this.board = board;
    this.winConditions = winConditions;
    this.checkConditions = checkConditions;
    this.winConditionValidator = new WinConditionValidator(winConditions);
    this.promoter = promoter;
    this.selector = selector;
    this.currentTurn = currentTurn;
    this.turnNumber = 0;
  }

  private ChessGame(
      Board board,
      List<WinCondition> winConditions,
      List<Check> checkConditions,
      Promoter promoter,
      TurnSelector selector,
      Color currentTurn,
      int turnNumber) {
    // Represents state passage
    this.board = board;
    this.winConditions = winConditions;
    this.checkConditions = checkConditions;
    this.winConditionValidator = new WinConditionValidator(winConditions);
    this.promoter = promoter;
    this.selector = selector;
    this.turnNumber = turnNumber;
    this.currentTurn = currentTurn;
  } // make it public

    public static ChessGame createChessGame(
            Board board,
            List<WinCondition> rules,
            List<Check> checkConditions,
            Promoter promoter,
            TurnSelector selector,
            Color currentTurn,
            int turnNumber){
        return new ChessGame(board, rules, checkConditions,promoter, selector, currentTurn, turnNumber);
    }

//    public GameResult makeMove(Position oldPos, Position newPos) {
//    // If no argument is passed, it promotes to a Queen (BY DEFAULT)
//    return makeMove(oldPos, newPos);

  public GameResult makeMove(Position oldPos, Position newPos) {
    // Check winning at the end
    // Do all necessary checks
    // Invalid positions
    if (outOfBoardBounds(oldPos) || outOfBoardBounds(newPos)) {
      return new GameResult(this, INVALID_MOVE);
    }
    // Fetch pieces
    Piece pieceToMove = board.pieceAt(oldPos);
    // Want to move a piece that's not there
    if (pieceToMove == null || pieceToMove.getPieceColour() != currentTurn) {
          return new GameResult(this, INVALID_MOVE);
      }
      // Invalid move due to piece rules
    if (!pieceToMove.isValidMove(oldPos, newPos, board)) {
      return new GameResult(this, INVALID_MOVE);
    }
   Board finalBoard = handleMovement(oldPos,newPos);
    Color nextTurn = selector.selectTurn(turnNumber+1);
    ChessGame finalGame = createChessGame(finalBoard, winConditions, checkConditions,
            promoter, selector, nextTurn,turnNumber+1);
    // End of code
    if (possiblePlayInCheck(currentTurn, finalBoard)) {
      return new GameResult(this, INVALID_MOVE);
    }
    if (winConditionValidator.isGameWon(finalBoard)) { //TODO: check why win conditions change my board
      MoveResult winner = currentTurn == Color.BLACK ? BLACK_WIN : WHITE_WIN; //Hardcoded, need to change
      return new GameResult(finalGame, winner);
    }
    return new GameResult(finalGame, VALID_MOVE);
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
            .findAny().orElse(null);
      assert checkRule != null;
      return checkRule.isValidRule(board);
  }

  private Board promoteIfAble(
      Board board, Position position, Color color) {
    if (promoter.canPromote(position, board) || promoter.hasToPromote(board, color)) {
      return promoter.promote(position, PieceType.QUEEN, board);
    }
    return board;
  }

  public List<Check> getCheckConditions() {
    return checkConditions;
  }

  private Board handleMovement(Position oldPos, Position newPos){
    // Now, move the piece. Take piece in newPos whether exists
    Piece piece = board.pieceAt(oldPos);
    Board newBoard;
    Piece pieceToTake = board.pieceAt(newPos); // Check outside
    if (pieceToTake != null) {
      if (pieceToTake.getPieceColour() == piece.getPieceColour()) {
        return board;
      } else {
        newBoard = board.removePieceAt(newPos).updatePiecePosition(oldPos,newPos);
        newBoard = promoteIfAble(newBoard, newPos,piece.getPieceColour());
        return newBoard;
      }
    } else {
      newBoard = board.removePieceAt(oldPos).addPieceAt(newPos, piece.hasNotMoved() ? piece.changeMoveState() : piece);
      newBoard = promoteIfAble(newBoard, newPos,piece.getPieceColour());
      return newBoard;
    }
  }

  public int getTurnNumber() {
    return turnNumber;
  }
}
