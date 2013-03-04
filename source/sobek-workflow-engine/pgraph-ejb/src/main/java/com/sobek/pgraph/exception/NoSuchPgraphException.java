package com.sobek.pgraph.exception;

public class NoSuchPgraphException extends Exception{
    private static final long serialVersionUID = 1L;
    
    public NoSuchPgraphException(String message){
	super(message);
    }
}
