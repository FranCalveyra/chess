package edu.austral.dissis.chess.utils;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.rule.movement.*;
import edu.austral.dissis.chess.utils.GameType;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessGameProvider {
  public Map<Position, Piece> provide(GameType type){
    Map<Position, Piece> result = new HashMap<>();

    if(type != GameType.DEFAULT) return null;
    //Pawns
    for (int j = 0; j <8 ; j++) {
      result.put(new Position(1,j), new Piece(List.of(new PawnTakingRule(), new PawnMovementRule(), new PawnFirstMoveRule()), Color.WHITE, PieceType.PAWN));
      result.put(new Position(6,j), new Piece(List.of(new PawnTakingRule(), new PawnMovementRule(), new PawnFirstMoveRule()), Color.BLACK, PieceType.PAWN));
    }
    //Rooks
    result.put(new Position(0,0), new Piece(List.of(new RookMovementRule()), Color.WHITE, PieceType.ROOK));
    result.put(new Position(0,7), new Piece(List.of(new RookMovementRule()), Color.WHITE, PieceType.ROOK));
    result.put(new Position(7,7), new Piece(List.of(new RookMovementRule()), Color.BLACK, PieceType.ROOK));
    result.put(new Position(7,0), new Piece(List.of(new RookMovementRule()), Color.BLACK, PieceType.ROOK));
    //Knights
    result.put(new Position(0,1), new Piece(List.of(new KnightMovementRule()), Color.WHITE, PieceType.KNIGHT));
    result.put(new Position(0,6), new Piece(List.of(new KnightMovementRule()), Color.WHITE, PieceType.KNIGHT));
    result.put(new Position(7,1), new Piece(List.of(new KnightMovementRule()), Color.BLACK, PieceType.KNIGHT));
    result.put(new Position(7,6), new Piece(List.of(new KnightMovementRule()), Color.BLACK, PieceType.KNIGHT));
    //Bishops
    result.put(new Position(0,2), new Piece(List.of(new DiagonalMovementRule()), Color.WHITE, PieceType.BISHOP));
    result.put(new Position(0,5), new Piece(List.of(new DiagonalMovementRule()), Color.WHITE, PieceType.BISHOP));
    result.put(new Position(7,2), new Piece(List.of(new DiagonalMovementRule()), Color.BLACK, PieceType.BISHOP));
    result.put(new Position(7,5), new Piece(List.of(new DiagonalMovementRule()), Color.BLACK, PieceType.BISHOP));
    //King and Queen
    result.put(new Position(0,3), new Piece(List.of(new QueenMovementRule()), Color.WHITE, PieceType.QUEEN));
    result.put(new Position(0,4), new Piece(List.of(new KingMovementRule()), Color.WHITE, PieceType.KING));
    result.put(new Position(7,3), new Piece(List.of(new QueenMovementRule()), Color.BLACK, PieceType.QUEEN));
    result.put(new Position(7,4), new Piece(List.of(new KingMovementRule()), Color.BLACK, PieceType.KING));
    return result;
  }
}
