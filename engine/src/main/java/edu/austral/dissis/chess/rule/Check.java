package edu.austral.dissis.chess.rule;

import java.awt.Color;

public interface Check extends WinCondition {
  Color getTeam();
}
