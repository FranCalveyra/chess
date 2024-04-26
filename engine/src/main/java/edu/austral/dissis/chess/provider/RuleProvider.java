package edu.austral.dissis.chess.provider;

import edu.austral.dissis.chess.winConditions.CheckMate;
import edu.austral.dissis.chess.winConditions.DefaultCheck;
import edu.austral.dissis.chess.winConditions.WinCondition;
import edu.austral.dissis.chess.utils.GameType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RuleProvider {
    public List<WinCondition> provide(GameType type){
        if(type == GameType.DEFAULT){
            return (List.of(new WinCondition[]{
                    new CheckMate(Color.WHITE), new CheckMate(Color.BLACK),
                    new DefaultCheck(Color.WHITE), new DefaultCheck(Color.BLACK)}));
        }
        return new ArrayList<>();
    }
}
