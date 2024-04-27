package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.rules.CheckMate;
import edu.austral.dissis.chess.rules.WinCondition;
import edu.austral.dissis.chess.utils.GameType;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class RuleProvider {
  public List<WinCondition> provide(GameType type) {
    if (type == GameType.DEFAULT) {
      return (List.of(new CheckMate(Color.WHITE), new CheckMate(Color.BLACK)));
    }
    return new ArrayList<>();
  }
}
