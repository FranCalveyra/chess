package edu.austral.dissis.chess.rules;

import java.awt.Color;

public interface Check extends WinCondition {
  Color team();
}
