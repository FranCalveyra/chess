package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.validators.DefaultCheckValidator;
import java.awt.Color;
import java.util.Objects;

public final class DefaultCheck implements Check {
    private final Color team;

    public DefaultCheck(Color team) {
        this.team = team;
    }

    @Override
    public boolean isValidRule(Board context) {
        return check(context, team);
    }

    private boolean check(Board context, Color team) {
        DefaultCheckValidator validator = new DefaultCheckValidator();
        return context.getPiecesAndPositions().entrySet().stream().anyMatch(entry -> validator.isInCheck(context, team, entry.getKey()));
    }

    @Override
    public Color team() {
        return team;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DefaultCheck) obj;
        return Objects.equals(this.team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team);
    }

    @Override
    public String toString() {
        return "DefaultCheck[" +
                "team=" + team + ']';
    }

}
