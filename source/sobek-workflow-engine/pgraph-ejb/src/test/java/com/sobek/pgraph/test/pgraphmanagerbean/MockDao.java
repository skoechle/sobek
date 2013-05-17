package com.sobek.pgraph.test.pgraphmanagerbean;

import java.lang.reflect.Field;
<<<<<<< HEAD
=======
import java.lang.reflect.Method;
>>>>>>> 9647ec62ba437be570e3c4cd29adbed6bd31e471
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
import com.sobek.pgraph.EdgeEntity;
import com.sobek.pgraph.InvalidPgraphStructureException;
import com.sobek.pgraph.NoSuchPgraphException;
import com.sobek.pgraph.NodeEntity;
import com.sobek.pgraph.NodeType;
import com.sobek.pgraph.PgraphDaoLocal;
import com.sobek.pgraph.PgraphEntity;
=======
import com.sobek.pgraph.InvalidPgraphStructureException;
import com.sobek.pgraph.NoSuchPgraphException;
import com.sobek.pgraph.Node;
import com.sobek.pgraph.NodeType;
import com.sobek.pgraph.PgraphDaoLocal;
import com.sobek.pgraph.entity.EdgeEntity;
import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.entity.PgraphEntity;
>>>>>>> 9647ec62ba437be570e3c4cd29adbed6bd31e471

public class MockDao implements PgraphDaoLocal{
    private static Logger logger = LoggerFactory.getLogger(MockDao.class);
    private List<NodeEntity> nodes = new ArrayList<NodeEntity>();
    private List<EdgeEntity> edges = new ArrayList<EdgeEntity>();
    private PgraphEntity pgraphEntity = null;

    public MockDao(long pgraphId) throws Exception{
	this.pgraphEntity = new PgraphEntity();
	
	Field idField = PgraphEntity.class.getDeclaredField("id");
	idField.setAccessible(true);
	idField.set(pgraphEntity, pgraphId);
    }
    
    public void addNode(NodeEntity node){	
	long nodeId = nodes.size();
	
	try{
	    // Set fields
	    Field idField = NodeEntity.class.getDeclaredField("id");
	    idField.setAccessible(true);
	    idField.setLong(node, nodeId);
	}catch(Exception e){
	    e.printStackTrace();
	}
				
	nodes.add(node);
    }
    
    public void addEdge(EdgeEntity edge){
	edges.add(edge);
    }
    
    @Override
    public PgraphEntity getPgraph(long pgraphId){
	return pgraphEntity;
    }

    @Override
    public List<NodeEntity> getParentNodes(long nodeId){
	List<NodeEntity> parentNodes = new ArrayList<NodeEntity>();
	
	for(EdgeEntity edge : edges){
	    if(edge.getToNodeId() == nodeId){
		long parentId = edge.getFromNodeId();
		
		for(NodeEntity node : nodes){
		    if(node.getId() == parentId){
			parentNodes.add(node);
		    }
		}
	    }
	}
	
	return parentNodes;
    }

    @Override
    public List<NodeEntity> getChildNodes(long nodeId){
	logger.trace("Getting child nodes for nodeId {}.", nodeId);
	List<NodeEntity> childNodes = new ArrayList<NodeEntity>();
	
	for(EdgeEntity edge : edges){
	    logger.trace("Checking if nodeId {} is the head of edge {}.", nodeId, edge);
	    
	    if(edge.getFromNodeId() == nodeId){
		logger.trace("NodeId {} is head of edge {}.", nodeId, edge);
		long childNodeId = edge.getToNodeId();
		logger.trace("Child nodeId is {} for nodeId {}.", childNodeId, nodeId);
		
		for(NodeEntity node : nodes){
		    logger.trace("Checking if nodeId {} == childNodeId {}.", node.getId(), childNodeId);
		    if(node.getId() == childNodeId){
			logger.trace("Found child node with Id {} for nodeId {}.", childNodeId, nodeId);
			childNodes.add(node);
		    }
		}
	    }
	}
	
	logger.trace("Returning {} nodes.", nodes.size());
	return childNodes;
    }

    @Override
    public NodeEntity getRawMaterialNode(long pgraphId) throws InvalidPgraphStructureException, NoSuchPgraphException{
	NodeEntity rawMaterialNode = null;
	
	for(NodeEntity node : nodes){
	    if(node.getType().equals(NodeType.RAW_MATERIAL)){
		rawMaterialNode = node;
	    }
	}
	
	return rawMaterialNode;
    }

    @Override
    public boolean pgraphExists(long pgraphId){
	return pgraphEntity.getId() == pgraphId;
    }

    @Override
    public void addPgraph(PgraphEntity pgraphEntity){
<<<<<<< HEAD

    } 
=======

    }

    @Override
    public void addNode(NodeEntity nodeEntity){

    }
   
>>>>>>> 9647ec62ba437be570e3c4cd29adbed6bd31e471
}
