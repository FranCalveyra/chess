package edu.austral.dissis.chess.utils;

import static edu.austral.dissis.chess.utils.ChessPosition.fromAlgebraic;
import static edu.austral.dissis.chess.utils.ChessPosition.toAlgebraic;

public record ChessMove(ChessPosition from, ChessPosition to) {
    @Override
    public String toString() {
        return toAlgebraic(from) + " -> " + toAlgebraic(to);
    }
}
