package edu.austral.dissis.chess.promoters;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.providers.PieceProvider;
import edu.austral.dissis.chess.utils.move.ChessPosition;
import java.awt.Color;
import java.util.stream.IntStream;

/** Only promotes Pawns. */
public class StandardPromoter implements Promoter {

  @Override
  public boolean hasToPromote(Board context, Color team) {
    return isAnyPawnPromotable(context, team);
  }

  @Override
  public boolean canPromote(ChessPosition position, Board context) {
    Piece piece = context.pieceAt(position);
    if (piece == null) {
      return false;
    }
    int promoteRow = piece.getPieceColour() == Color.BLACK ? 0 : context.getRows() - 1;
    return piece.getType() == PieceType.PAWN && position.getRow() == promoteRow;
  }

  @Override
  public Board promote(ChessPosition position, PieceType type, Board context) {
    // Fetch initial piece
    Piece initialPiece = context.pieceAt(position);
    // Get the piece to promote
    Piece pieceToPromoteTo = new PieceProvider().provide(initialPiece.getPieceColour(), type);
    Piece promotedPiece =
        new Piece(
            pieceToPromoteTo.getMovements(),
            pieceToPromoteTo.getPieceColour(),
            type,
            initialPiece.getMoveCounter(),
            initialPiece.getId());
    // Update the board
    return context.removePieceAt(position).addPieceAt(position, promotedPiece);
  }

  // Private stuff
  // If there's any pawn promotable in the board (in the last row, promote it)
  private boolean isAnyPawnPromotable(Board context, Color team) {
    // Changes the row to check depending on the analysed color. If WHITE, checks the last row;
    // else, checks the first
    int rowToCheck = team == Color.WHITE ? context.getRows() - 1 : 0;
    return IntStream.range(0, context.getColumns())
        .mapToObj(j -> new ChessPosition(rowToCheck, j))
        .map(context::pieceAt)
        .anyMatch(
            piece ->
                piece != null
                    && piece.getType() == PieceType.PAWN
                    && piece.getPieceColour() == team);
  }
}
