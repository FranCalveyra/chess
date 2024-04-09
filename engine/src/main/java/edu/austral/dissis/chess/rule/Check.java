package edu.austral.dissis.chess.rule;

import java.awt.*;

public interface Check extends WinCondition {
    Color getTeam();
}
