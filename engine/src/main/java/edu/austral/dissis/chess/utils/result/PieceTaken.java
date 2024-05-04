package edu.austral.dissis.chess.utils.result;

import edu.austral.dissis.chess.piece.Piece;

public class PieceTaken implements ChessMoveResult {
  private final Piece pieceTaken;

  public PieceTaken(Piece pieceTaken) {
    this.pieceTaken = pieceTaken;
  }

  @Override
  public String getMessage() {
    return "Piece taken: " + pieceTaken;
  }

  @Override
  public String getType() {
    return "PieceTaken";
  }
}
