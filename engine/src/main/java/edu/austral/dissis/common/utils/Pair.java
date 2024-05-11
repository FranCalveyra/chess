package edu.austral.dissis.common.utils;

public final class Pair<T, W> {
  private final T first;
  private final W second;

  public Pair(T first, W second) {
    this.first = first;
    this.second = second;
  }

  public T first() {
    return first;
  }

  public W second() {
    return second;
  }
}
