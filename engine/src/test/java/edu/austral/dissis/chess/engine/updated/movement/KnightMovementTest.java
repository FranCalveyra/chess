package edu.austral.dissis.chess.engine.updated.movement;

import edu.austral.dissis.chess.engine.updated.runners.ChessTestMoveRunner;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.test.Validity;
import edu.austral.dissis.chess.test.move.MoveTester;
import edu.austral.dissis.chess.test.parser.ParseSettings;
import org.junit.jupiter.api.Test;


public class KnightMovementTest {
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
}
