package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.utils.Position;

import java.awt.*;
import java.util.Map;

public class CheckMate implements WinCondition {
  @Override
  public boolean isValidRule(Board context) {
    return kingInCheckMate(context, Color.BLACK) || kingInCheckMate(context, Color.WHITE);
  }

  private boolean kingInCheckMate(Board context, Color team) {
    //TODO
    Piece king = getPieceByType(context.getActivePiecesAndPositions(), team, PieceType.KING);
    //It's a mess, need to check all surroundings.
    return true;
  }

  private Piece getPieceByType(Map<Position, Piece> activePiecesAndPositions, Color team, PieceType type) {
    for(Map.Entry<Position, Piece> entry: activePiecesAndPositions.entrySet()){
      if(entry.getValue().getType() == type && entry.getValue().getPieceColour() == team) return entry.getValue();
    }
    return null;
  }
}
