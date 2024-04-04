package edu.austral.dissis.chess.engine;

import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.rule.*;
import edu.austral.dissis.chess.rule.movement.*;
import edu.austral.dissis.chess.utils.GameType;
import edu.austral.dissis.chess.utils.Pair;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.utils.ChessGameProvider;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessTest {
  //Setup
  private final Map<Position,Piece> pieces = new ChessGameProvider().provide(GameType.DEFAULT);
  private final Board board = new Board(pieces);
  private final List<GameRule> rules = List.of(new Check(), new CheckMate(), new Stalemate());
  private final ChessGame game = new ChessGame(board, rules);

  //Tests
  @Test
  public void pawnShouldMoveOneOrTwoTilesOnFirstMove(){

  }


}
