package com.sobek.pgraph;

public final class Edge{
    private final Node fromNode;
    private final Node toNode;
    
    public Edge(Node fromNode, Node toNode){
	if(fromNode == null || toNode == null){
	    String message = "Paramteres can't be null. Paramteres were:\n" +
		    	"fromNode: " + fromNode + "\n" +
		    	"toNode: " + toNode + "\n";
	    
	    throw new IllegalArgumentException(message);
	}
	
	this.fromNode = fromNode;
	this.toNode = toNode;
    }

    public Node getFromNode(){
        return fromNode;
    }

    public Node getToNode(){
        return toNode;
    }
}
