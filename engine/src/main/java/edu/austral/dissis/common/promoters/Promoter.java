package edu.austral.dissis.common.promoters;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.PieceType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import java.awt.Color;

public interface Promoter {
  boolean hasToPromote(Board context, Color team); // Check the promotion row of a given team

  boolean canPromote(
      BoardPosition position, Board context); // Check if piece at given position is promotable

  Board promote(BoardPosition position, PieceType type, Board context);
}
