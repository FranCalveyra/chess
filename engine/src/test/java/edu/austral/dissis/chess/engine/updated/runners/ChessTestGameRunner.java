package edu.austral.dissis.chess.engine.updated.runners;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.test.*;
import edu.austral.dissis.chess.test.game.*;
import edu.austral.dissis.chess.utils.Position;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapBoard;
import static edu.austral.dissis.chess.engine.updated.utils.GameAdapter.mapPos;

public class ChessTestGameRunner implements TestGameRunner {

    private final ChessGame game;
    public ChessTestGameRunner(ChessGame game){
        this.game = game;
    }

    @NotNull
    @Override
    public TestMoveResult executeMove(@NotNull TestPosition from, @NotNull TestPosition to) {
        return switch (game.makeMove(mapPos(from), mapPos(to)).getMessage()){
            case INVALID_MOVE -> new TestMoveFailure(getBoard());
            case WHITE_WIN -> new BlackCheckMate(getBoard());
            case BLACK_WIN -> new WhiteCheckMate(getBoard());
            case VALID_MOVE, PIECE_TAKEN -> new TestMoveSuccess(this);
        };
    }

    @NotNull
    @Override
    public TestBoard getBoard() {
        return new TestBoard(new TestSize(game.getBoard().getRows(), game.getBoard().getColumns()),getTestMap(game.getBoard().getPiecesAndPositions()));
    }

    @NotNull
    @Override
    public TestGameRunner withBoard(@NotNull TestBoard testBoard) {
        return new ChessTestGameRunner(ChessGame.createChessGame(mapBoard(testBoard), game.getRules(), game.getPromoter(), game.getSelector(), game.getCurrentTurn()));
    }

    private Map<TestPosition, TestPiece> getTestMap(Map<Position, Piece> pieceMap){
        Map<TestPosition, TestPiece> testMap = new HashMap<>();
        for(Entry<Position, Piece> entry : pieceMap.entrySet()){
            Position currentPosition = entry.getKey();
            testMap.put(new TestPosition(currentPosition.getRow(), currentPosition.getColumn()), mapPiece(entry.getValue()));
        }
        return testMap;
    }

    private TestPiece mapPiece(Piece piece){
        char color = piece.getPieceColour() == Color.BLACK ? TestPieceSymbols.BLACK : TestPieceSymbols.WHITE;
        return new TestPiece(mapPieceType(piece.getType()),color);
    }
    private char mapPieceType(PieceType pieceType){
        return switch (pieceType){
            case KNIGHT -> TestPieceSymbols.KNIGHT;
            case BISHOP -> TestPieceSymbols.BISHOP;
            case ROOK -> TestPieceSymbols.ROOK;
            case QUEEN -> TestPieceSymbols.QUEEN;
            case KING -> TestPieceSymbols.KING;
            case PAWN -> TestPieceSymbols.PAWN;
        };
    }

}
