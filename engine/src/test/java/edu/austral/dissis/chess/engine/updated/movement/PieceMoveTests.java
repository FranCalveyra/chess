package edu.austral.dissis.chess.engine.updated.movement;

import edu.austral.dissis.chess.engine.updated.runners.ChessTestMoveRunner;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.Validity;
import edu.austral.dissis.chess.test.move.MoveTester;
import edu.austral.dissis.chess.test.parser.ParseSettings;
import org.junit.jupiter.api.Test;


public class PieceMoveTests {
    //Runners
    private final ChessTestMoveRunner moveRunner = new ChessTestMoveRunner();
    private final MoveTester moveTester = new MoveTester(moveRunner);

    @Test
    public void testKnightPositions() {
        moveTester.testMove("/moves/knight/valid_moves.txt", new TestPosition(5, 4), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/knight/valid_moves_1.txt", new TestPosition(8, 2), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/knight/valid_moves_0.txt", new TestPosition(5, 4), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/knight/valid_moves_0.txt", new TestPosition(3, 3), new ParseSettings(Validity.INVALID));
        for (int i = 1; i <=8 ; i++) {
            for (int j = 1; j <=8 ; j++) {
                moveTester.testMove("/moves/knight/valid_moves_0.txt", new TestPosition(i, j), new ParseSettings(Validity.INVALID));
            }
        }
    }
    @Test
    public void testBishopPositions(){
        moveTester.testMove("/moves/bishop/valid_moves.txt", new TestPosition(5, 4), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/bishop/valid_moves_1.txt", new TestPosition(7, 4), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/bishop/valid_moves_0.txt", new TestPosition(1, 1), new ParseSettings(Validity.VALID));
    }
    @Test
    public void testPawnPositions(){
        moveTester.testMove("/moves/pawn/valid_moves.txt", new TestPosition(8, 2), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/pawn/valid_moves.txt", new TestPosition(4, 2), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/pawn/valid_moves.txt", new TestPosition(7, 6), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/pawn/valid_moves_1.txt", new TestPosition(7, 4), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/pawn/valid_moves_0.txt", new TestPosition(1, 1), new ParseSettings(Validity.INVALID));
    }
    @Test
    public void testKingPositions(){
        moveTester.testMove("/moves/king/valid_moves.txt", new TestPosition(5, 4), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/king/valid_moves_1.txt", new TestPosition(8, 2), new ParseSettings(Validity.INVALID));
        moveTester.testMove("/moves/king/valid_moves_0.txt", new TestPosition(8, 1), new ParseSettings(Validity.INVALID));
    }


//    @Test
//    public void testQueenPositions(){
//        moveTester.testMove("/moves/queen/valid_moves.txt", new TestPosition(5, 4), new ParseSettings(Validity.INVALID));
//        moveTester.testMove("/moves/queen/valid_moves_1.txt", new TestPosition(7, 4), new ParseSettings(Validity.INVALID));
//        moveTester.testMove("/moves/queen/valid_moves_0.txt", new TestPosition(1, 1), new ParseSettings(Validity.INVALID));
//    }
//    @Test
//    public void testRookPositions() {
//        moveTester.testMove("/moves/rook/valid_moves.txt", new TestPosition(5, 4), new ParseSettings(Validity.INVALID));
//        moveTester.testMove("/moves/rook/valid_moves_1.txt", new TestPosition(7, 4), new ParseSettings(Validity.INVALID));
//        moveTester.testMove("/moves/rook/valid_moves_0.txt", new TestPosition(1, 1), new ParseSettings(Validity.INVALID));
//    }
}
