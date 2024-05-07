package edu.austral.dissis.common.utils.move;

public final class GameMove {
  private final BoardPosition from;
  private final BoardPosition to;

  public GameMove(BoardPosition from, BoardPosition to) {
    this.from = from;
    this.to = to;
  }

  @Override
  public String toString() {
    return BoardPosition.toAlgebraic(from) + " -> " + BoardPosition.toAlgebraic(to);
  }

  public BoardPosition from() {
    return from;
  }

  public BoardPosition to() {
    return to;
  }
}
