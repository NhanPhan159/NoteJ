package com.example.NoteJ.CustomException;

public class EmptyContentException extends Exception{
    public EmptyContentException(String message){
        super(message);
    }
}
