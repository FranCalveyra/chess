package edu.austral.dissis.common.turn;

import edu.austral.dissis.common.utils.result.playresult.PlayResult;
import java.awt.Color;

public interface TurnSelector {
  Color getCurrentTurn();

  TurnSelector changeTurn(PlayResult result);
}
