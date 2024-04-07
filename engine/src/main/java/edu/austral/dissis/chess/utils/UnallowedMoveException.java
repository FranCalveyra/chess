package edu.austral.dissis.chess.utils;

public class UnallowedMoveException extends Exception{
    public UnallowedMoveException(String message){
        super(message);
    }
}
