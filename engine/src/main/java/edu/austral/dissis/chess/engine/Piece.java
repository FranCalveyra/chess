package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.rule.movement.PieceMovementRule;
import edu.austral.dissis.chess.utils.Position;

import java.awt.*;
import java.util.List;

public class Piece {
  private final List<PieceMovementRule> movementRules;
  private final Color pieceColour;
  private final String name;
  private boolean isActiveInBoard = true;
  public Piece(List<PieceMovementRule> movementRules, Color pieceColour, String name){
    this.movementRules = movementRules;
    this.pieceColour = pieceColour;
    this.name  = name;
  }
  public boolean checkValidMove(Position oldPos, Position newPos){
    for(PieceMovementRule rule: movementRules){
      if(!rule.isValidMove(oldPos, newPos)) return false;
    }
    return true;
  }
  public boolean isActiveInBoard() {
    return isActiveInBoard;
  }
  public void changePieceActivity(){
    isActiveInBoard = !isActiveInBoard;
  }

  public Color getPieceColour() {
    return pieceColour;
  }
}
