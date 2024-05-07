package edu.austral.dissis.common.board;

import edu.austral.dissis.common.piece.Piece;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.util.Map;

public interface Board {
  MapBoard addPieceAt(BoardPosition pos, Piece piece);

  MapBoard removePieceAt(BoardPosition pos);

  Piece pieceAt(BoardPosition pos);

  Map<BoardPosition, Piece> getPiecesAndPositions();

  int getRows();

  int getColumns();
}
