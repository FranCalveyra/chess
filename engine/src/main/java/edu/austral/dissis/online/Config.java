package edu.austral.dissis.online;

import static edu.austral.dissis.common.utils.AuxStaticMethods.setupGame;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.common.utils.enums.GameType;

public class Config {
  // Config
  // Change gameType in order to change game to play
  private static final GameType gameType = GameType.DEFAULT_CHECKERS;
  public static GameEngine engine = setupGame(gameType).first();
}
