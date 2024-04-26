package edu.austral.dissis.chess.winConditions;

import java.awt.Color;

public interface Check extends WinCondition {
  Color getTeam();
}
