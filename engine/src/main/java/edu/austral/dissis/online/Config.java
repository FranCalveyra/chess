package edu.austral.dissis.online;

import static edu.austral.dissis.common.utils.AuxStaticMethods.setupGame;

import edu.austral.dissis.common.ui.gameengine.BoardGameEngine;
import edu.austral.dissis.common.utils.enums.GameType;

public class Config {
  // Config
  // Change gameType in order to change game to play
  private static final GameType gameType = GameType.DEFAULT_CHESS;
  public static BoardGameEngine engine = (BoardGameEngine) setupGame(gameType).first();
}
