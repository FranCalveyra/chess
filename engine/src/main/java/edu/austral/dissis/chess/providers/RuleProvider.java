package edu.austral.dissis.chess.providers;

import edu.austral.dissis.chess.rules.AvoidFriendlyFire;
import edu.austral.dissis.chess.rules.CheckMate;
import edu.austral.dissis.chess.rules.InsideBoardBounds;
import edu.austral.dissis.chess.rules.MoveNotIntoCheck;
import edu.austral.dissis.chess.rules.PieceAtPosition;
import edu.austral.dissis.chess.rules.PieceValidMove;
import edu.austral.dissis.chess.rules.TurnRule;
import edu.austral.dissis.chess.rules.WinCondition;
import edu.austral.dissis.chess.utils.type.GameType;
import edu.austral.dissis.chess.validators.AndTreePreMovementValidator;
import edu.austral.dissis.chess.validators.PreMovementValidator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class RuleProvider {
  public List<WinCondition> provideWinConditions(GameType type) {
    if (type == GameType.DEFAULT) {
      return (List.of(new CheckMate(Color.WHITE), new CheckMate(Color.BLACK)));
    }
    return new ArrayList<>();
  }

  public PreMovementValidator providePreMovementValidator(GameType type) {
    if (type != GameType.DEFAULT) {
      return null;
    }
    AndTreePreMovementValidator moveInside = new AndTreePreMovementValidator(new InsideBoardBounds());
    AndTreePreMovementValidator pieceAt = new AndTreePreMovementValidator(new PieceAtPosition(), moveInside,null);
    AndTreePreMovementValidator moveAllowed = new AndTreePreMovementValidator(new PieceValidMove());
    AndTreePreMovementValidator noFriendlyFire = new AndTreePreMovementValidator(new AvoidFriendlyFire(), moveAllowed,new AndTreePreMovementValidator(new TurnRule()));
    return new AndTreePreMovementValidator(new MoveNotIntoCheck(), pieceAt, noFriendlyFire);

  }
}
