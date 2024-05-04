package edu.austral.dissis.chess.utils.move;

import static edu.austral.dissis.chess.utils.move.ChessPosition.toAlgebraic;

public final class ChessMove {
  private final ChessPosition from;
  private final ChessPosition to;

  public ChessMove(ChessPosition from, ChessPosition to) {
    this.from = from;
    this.to = to;
  }

  @Override
  public String toString() {
    return toAlgebraic(from) + " -> " + toAlgebraic(to);
  }

  public ChessPosition from() {
    return from;
  }

  public ChessPosition to() {
    return to;
  }
}
