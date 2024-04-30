package edu.austral.dissis.chess.utils;

import static edu.austral.dissis.chess.utils.ChessPosition.toAlgebraic;

import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (ChessMove) obj;
    return Objects.equals(this.from, that.from) && Objects.equals(this.to, that.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to);
  }
}
