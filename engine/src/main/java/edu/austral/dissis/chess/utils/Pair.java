package edu.austral.dissis.chess.utils;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Pair) obj;
        return Objects.equals(this.first, that.first) &&
                Objects.equals(this.second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair[" +
                "first=" + first + ", " +
                "second=" + second + ']';
    }
}
