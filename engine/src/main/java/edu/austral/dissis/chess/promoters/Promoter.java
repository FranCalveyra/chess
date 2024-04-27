package edu.austral.dissis.chess.promoters;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.awt.Color;

public interface Promoter {
  // Put the type to promote in constructor
  boolean hasToPromote(Board context, Color team); // Check the promotion row of a given team

  boolean canPromote(ChessPosition chessPosition, Board context);

  Board promote(ChessPosition chessPosition, PieceType type, Board context);
}
