package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;
import java.awt.*;
import java.util.Map;
import java.util.NoSuchElementException;

public class Board {
  private final Map<Position, Piece> pieces;
  private final int rows, columns;
  private Color currentTurn;
  private final Piece[][] board;

  public Board(Map<Position, Piece> pieces, int rows, int columns) {
    this.pieces = pieces;
    this.rows = rows;
    this.columns = columns;
    this.board = new Piece[rows][columns];
  }
  //Default constructor for Chess Board
  public Board(Map<Position, Piece> pieces){
    this.pieces = pieces;
    this.rows = this.columns = 8;
    this.board = new Piece[8][8];
  }

  public Map<Position, Piece> getActivePiecesAndPositions() {
    return pieces;
  }

  public void removePieceAt(Position position, Piece piece) {
    piece.changePieceActivity();
    pieces.remove(position, piece);
    board[position.getRow()][position.getColumn()] = null;
  }

  public void addPieceAt(Position position, Piece piece) {
    piece.changePieceActivity();
    pieces.put(position, piece);
    board[position.getRow()][position.getColumn()] = piece;
  }

  public void updatePiecePosition(Position position, Piece piece) {
    //First, ChessGame checks if the wanted piece to move is from its team
    //TODO: ChessGame should have previously checked rules
    //TODO: Should check if moved piece is from current turn team
    Position oldPosition = getPieceCurrentPosition(piece);
    if(piece.checkValidMove(oldPosition, position)){
      //TODO
      // Update map
      // Change position in matrix
    }
  }
  public Color getCurrentTurn(){
    return currentTurn;
  }
  private Position getPieceCurrentPosition(Piece piece) {
    //O(N)
    for(Map.Entry<Position,Piece> entry: pieces.entrySet()){
      if(entry.getValue() == piece) return entry.getKey();
    }
    throw new NoSuchElementException("Piece does not exist");
  }
}
