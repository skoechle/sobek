package com.sobek.pgraph;

public class NoSuchOperationException extends Exception{
    private static final long serialVersionUID = 1L;

    NoSuchOperationException(String message){
	super(message);
    }
}
