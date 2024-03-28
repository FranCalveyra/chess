package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;

import java.awt.*;

public class TurnRule implements GameRule{

  @Override
  public boolean isValidRule(Board context) {
    return false;
  }
  public Color getCurrentTurn(Board context){
    return Color.BLACK;
  }
}
