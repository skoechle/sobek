package com.sobek.pgraph;

/**
 * 
 * @author Matt
 *
 * This Exception indicates that an operation was attempted on an invalid
 * pgraph.
 */
public class InvalidPgraphStructureException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    
    InvalidPgraphStructureException(String message){
	super(message);
    }
}
