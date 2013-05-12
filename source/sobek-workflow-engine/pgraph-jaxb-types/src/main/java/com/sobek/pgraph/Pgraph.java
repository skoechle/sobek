package com.sobek.pgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "com.sobek.pgraph")
@XmlRootElement(namespace = "com.sobek.pgraph")
public class Pgraph{
    
    @XmlElement(required = true)
    private final List<Node> nodes = new ArrayList<Node>();
    
    @XmlElement(required = true)
    private final List<Edge> edges = new ArrayList<Edge>();
    
    public void addNode(Node node){
	this.nodes.add(node);
    }
    
    public List<Node> getNodes(){
	return Collections.unmodifiableList(this.nodes);
    }
    
    public void addEdge(Edge edge){
	this.edges.add(edge);
    }
    
    public List<Edge> getEdges(){
	return Collections.unmodifiableList(this.edges);
    }
}
