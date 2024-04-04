package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.rule.*;
import edu.austral.dissis.chess.utils.GameType;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.ChessPieceMapProvider;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ChessTest {
  //Setup
  private final Map<Position,Piece> pieces = new ChessPieceMapProvider().provide(GameType.DEFAULT);
  private final Board board = new Board(pieces);
  private final List<GameRule> rules = new ArrayList<>(List.of(new Check(), new CheckMate(),new Stalemate()));
  private final ChessGame game = new ChessGame(board, rules);

  ChessTest(){
    game.startGame();
  }

  //Tests
  @Test
  public void pawnShouldMoveOneOrTwoTilesOnFirstMove(){
    Piece whitePawn = pieces.get(new Position(1,0));
    assertEquals(whitePawn.getPieceColour(), Color.WHITE);
    assertEquals(getPiecePosition(whitePawn), new Position(1,0));
    game.makeMove(whitePawn, new Position(2,0));
    assertEquals(new Position(2,0),getPiecePosition(whitePawn));

    Piece otherWhitePawn = pieces.get(new Position(1,1));
    assertEquals(otherWhitePawn.getPieceColour(), Color.WHITE);
    assertEquals(getPiecePosition(otherWhitePawn), new Position(1,1));
    game.makeMove(otherWhitePawn, new Position(3,1));
    assertNotEquals(new Position(3,1), getPiecePosition(otherWhitePawn));

    Piece blackPawn = pieces.get(new Position(6,0));
    assertEquals(blackPawn.getPieceColour(),Color.BLACK);
    assertEquals(getPiecePosition(blackPawn), new Position(6,0));
    game.makeMove(blackPawn, new Position(4,0));
    assertEquals(new Position(4,0), getPiecePosition(blackPawn));

    game.makeMove(otherWhitePawn, new Position(3,1));
    assertEquals(new Position(3,1), getPiecePosition(otherWhitePawn));


  }

  private Position getPiecePosition(Piece piece){
    for(Map.Entry<Position, Piece> entry: pieces.entrySet()){
      if(entry.getValue() == piece) return entry.getKey();
    }
    return null;
  }
}
