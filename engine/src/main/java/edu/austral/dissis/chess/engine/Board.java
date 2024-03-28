package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.utils.Position;

import java.awt.*;
import java.util.Map;

public class Board {
  private final Map<Position, Piece> pieces;
  private final int rows, columns;
  private Color currentTurn;

  public Board(Map<Position, Piece> pieces, int rows, int columns){
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
  }


  public Map<Position, Piece> getActivePiecesAndPositions(){
    return pieces;
  }
  public void removePieceAt(Position piecePosition, Piece piece){
    pieces.remove(piecePosition, piece);
  }
  public void addPieceAt(Position position, Piece piece){
    pieces.put(position, piece);
  }

  public void updatePiecePosition(Position newCoords, Piece piece){
  }

}
