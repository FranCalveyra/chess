package edu.austral.dissis.chess.engine.updated.utils;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.piece.PieceType;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.provider.PieceProvider;
import edu.austral.dissis.chess.test.TestBoard;
import edu.austral.dissis.chess.test.TestPiece;
import edu.austral.dissis.chess.test.TestPieceSymbols;
import edu.austral.dissis.chess.test.TestPosition;
import edu.austral.dissis.chess.utils.Position;
import javafx.geometry.Pos;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.austral.dissis.chess.piece.PieceType.*;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

public class GameAdapter {
    //TODO: implement

    public static Board mapBoard(TestBoard board){
        int rows = board.component1().getRows();
        int cols = board.component1().getCols();
        Map<Position, Piece> map = mapPieces(board.component2());
        return new Board(map, rows, cols, new ArrayList<>()); //Taken pieces is an empty list
    }

    public static Position mapPos(TestPosition position){
        return new Position(position.getRow(), position.getCol());
    }

    private static Map<Position, Piece> mapPieces(Map<TestPosition, TestPiece> map) {
        Map<Position, Piece> pieceMap = new HashMap<>();
        for(Map.Entry<TestPosition,TestPiece> entry: map.entrySet()){
            TestPosition position = entry.getKey();
            TestPiece piece = entry.getValue();
            pieceMap.put(mapPos(position), mapPiece(piece));
        }
        return pieceMap;
    }
    public static Piece mapPiece(TestPiece piece){
        PieceProvider pieceProvider = new PieceProvider();
        return pieceProvider.get(mapColour(piece.component2()), mapPieceType(piece));
    }

    private static Color mapColour(char c) {
        return switch (c){
            case 'B' -> BLACK;
            case 'W' -> WHITE;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    private static PieceType mapPieceType(TestPiece piece){
        return switch(piece.component1()){
            case 'K'-> KING;
            case 'P' -> PAWN;
            case 'B' -> BISHOP;
            case 'R' -> ROOK;
            case 'N' -> KNIGHT;
            case 'Q' ->QUEEN;
            default -> throw new IllegalStateException("Unexpected value: " + piece.component1());
    };
}
}
