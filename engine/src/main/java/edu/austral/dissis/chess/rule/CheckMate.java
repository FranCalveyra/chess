package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;

import java.awt.*;

public class CheckMate implements WinCondition {
  @Override
  public boolean isValidRule(Board context) {
    return kingInCheckMate(context, Color.BLACK) || kingInCheckMate(context, Color.WHITE);
  }

  private boolean kingInCheckMate(Board context, Color team) {
    //TODO
    return true;
  }
}
