package edu.austral.dissis.chess.piece.movement;


import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessPosition;


public class KingMovement implements PieceMovement {

  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    // King should move only in one direction, one tile at a time
    int deltaX = Math.abs(oldPos.getColumn() - newPos.getColumn());
    int deltaY = Math.abs(oldPos.getRow() - newPos.getRow());
    boolean horizontal = deltaX == 1 && deltaY == 0;
    boolean vertical = deltaY == 1 && deltaX == 0;
    boolean diagonal = deltaX == 1 && deltaY == 1;
    if(horizontal || vertical){
      return new RookMovement().isValidMove(oldPos, newPos, context);
    }
    else if(diagonal){
      return new DiagonalMovement().isValidMove(oldPos, newPos, context);
    }
    return false;
  }
}
