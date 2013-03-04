package com.sobek.pgraph.exception;

/**
 * 
 * @author Matt
 *
 * This Exception indicates that an operation was attempted on an invalid
 * pgraph.
 */
public class InvalidPgraphStructureException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    
    public InvalidPgraphStructureException(String message){
	super(message);
    }
}
