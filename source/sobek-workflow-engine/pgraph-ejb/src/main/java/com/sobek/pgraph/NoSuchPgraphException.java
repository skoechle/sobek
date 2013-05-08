package com.sobek.pgraph;

public class NoSuchPgraphException extends Exception{
    private static final long serialVersionUID = 1L;
    
    NoSuchPgraphException(String message){
	super(message);
    }
}
