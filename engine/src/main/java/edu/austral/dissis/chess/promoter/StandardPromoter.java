package edu.austral.dissis.chess.promoter;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.provider.PieceProvider;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;

public class StandardPromoter implements Promoter {

  @Override
  public boolean hasToPromote(Board context, Color team) {
    return isAnyPawnPromotable(context, team);
  }

  @Override
  public boolean canPromote(Position position, Board context) {
    Piece piece = context.pieceAt(position);
    if (piece == null) {
      return false;
    }
    int promoteRow = piece.getPieceColour() == Color.BLACK ? 0 : context.getRows() - 1;
    return piece.getType() == PieceType.PAWN && position.getRow() == promoteRow;
  }

  @Override
  public Board promote(Position position, PieceType type, Board context) {
    Piece initialPiece = context.pieceAt(position);
    Piece piece = new PieceProvider().get(initialPiece.getPieceColour(), type);
    Piece actualPiece =
        new Piece(piece.getMovements(), piece.getPieceColour(), type, !initialPiece.hasNotMoved());
    return context.removePieceAt(position).addPieceAt(position, actualPiece);
  }

  // Private stuff
  private boolean isAnyPawnPromotable(Board context, Color team) {
    int rowToCheck = team == Color.WHITE ? context.getRows() - 1 : 0;
    for (int j = 0; j < context.getColumns(); j++) {
      Piece pieceAt = context.pieceAt(new Position(rowToCheck, j));
      if (pieceAt != null
          && pieceAt.getType() == PieceType.PAWN
          && pieceAt.getPieceColour() == team) {
        return true;
      }
    }
    return false;
  }
}
