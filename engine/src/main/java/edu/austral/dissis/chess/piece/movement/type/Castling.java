package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.common.utils.enums.MoveType.HORIZONTAL;

import edu.austral.dissis.chess.rules.winconds.StandardCheck;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.validators.PiecePathValidator;
import java.util.List;
import java.util.stream.IntStream;

public class Castling implements PieceMovement {
  // Only valid whenever king and rooks haven't been moved yet,
  // and move to be done doesn't leave put king in risk.

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    return isCastlingPossible(move.from(), move.to(), context);
  }

  @Override
  public List<GameMove> getMovesToExecute(GameMove move, Board context) {
    int rookColumn = getRookColumn(move, context);
    int resultCol = getResultCol(rookColumn, move.from());
    BoardPosition rookFrom = new BoardPosition(move.from().getRow(), rookColumn);
    BoardPosition rookTo = new BoardPosition(move.from().getRow(), resultCol);
    GameMove rookMove = new GameMove(rookFrom, rookTo);
    return List.of(move, rookMove);
  }

  private int getResultCol(int rookColumn, BoardPosition oldPos) {
    return rookColumn == 0 ? oldPos.getColumn() - 1 : oldPos.getColumn() + 1;
  }

  private int getRookColumn(GameMove move, Board context) {
    return move.to().getColumn() == move.from().getColumn() - 2 ? 0 : context.getColumns() - 1;
  }

  private boolean isCastlingPossible(BoardPosition oldPos, BoardPosition newPos, Board context) {
    Piece king = context.pieceAt(oldPos);
    int rookColumn = getRookColumn(new GameMove(oldPos, newPos), context);
    Piece rook = context.pieceAt(new BoardPosition(newPos.getRow(), rookColumn));
    return situationMeetsCastlingConditions(oldPos, newPos, context, king, rook);
  }

  private boolean situationMeetsCastlingConditions(
      BoardPosition oldPos, BoardPosition newPos, Board context, Piece king, Piece rook) {
    if (king == null
        || rook == null
        || !validGeneralChecks(oldPos, newPos, king, rook)
        || checkOrBlockedPath(oldPos, newPos, context, king)) {
      return false;
    }
    return validateCheckBetween(oldPos, newPos, context);
  }

  private boolean checkOrBlockedPath(
      BoardPosition oldPos, BoardPosition newPos, Board context, Piece king) {
    boolean isInCheckFromStart = new StandardCheck(king.getPieceColour()).isValidRule(context);
    return !(new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, HORIZONTAL))
        || isInCheckFromStart;
  }

  private boolean validCastlingDisplacement(
      BoardPosition oldPos, BoardPosition newPos, int columnDelta) {
    return oldPos.getRow() == newPos.getRow() && columnDelta == 2;
  }

  private boolean neitherHaveMoved(Piece king, Piece rook) {
    return king.hasNotMoved() && rook.hasNotMoved();
  }

  private boolean sameColor(Piece king, Piece rook) {
    return king.getPieceColour() == rook.getPieceColour();
  }

  private boolean validateCheckBetween(BoardPosition oldPos, BoardPosition newPos, Board context) {
    int fromColumn = Math.min(oldPos.getColumn(), newPos.getColumn());
    int toColumn = Math.max(oldPos.getColumn(), newPos.getColumn());
    Piece king = context.pieceAt(oldPos);
    return IntStream.rangeClosed(fromColumn + 1, toColumn)
        .mapToObj(j -> new BoardPosition(oldPos.getRow(), j))
        .map(currentTile -> context.removePieceAt(oldPos).addPieceAt(currentTile, king))
        .noneMatch(
            possibleBoard ->
                new StandardCheck(context.pieceAt(oldPos).getPieceColour())
                    .isValidRule(possibleBoard)); // Functional programming matching
  }

  private boolean validGeneralChecks(
      BoardPosition oldPos, BoardPosition newPos, Piece king, Piece rook) {
    boolean colorCheck = sameColor(king, rook);
    int columnDelta = Math.abs(newPos.getColumn() - oldPos.getColumn());
    boolean displacementCheck = validCastlingDisplacement(oldPos, newPos, columnDelta);
    boolean haveNotMoved = neitherHaveMoved(king, rook);
    return colorCheck && haveNotMoved && displacementCheck;
  }
}
