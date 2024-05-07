package edu.austral.dissis.common.utils.result;

import edu.austral.dissis.common.piece.Piece;

public class PieceTaken implements PlayResult {
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

  @Override
  public boolean equals(Object obj) {
    return obj instanceof PieceTaken;
  }
}
