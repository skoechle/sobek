package com.sobek.pgraph;

public class NoSuchMaterialException extends Exception{
    private static final long serialVersionUID = 1L;

    NoSuchMaterialException(String message){
	super(message);
    }
}
