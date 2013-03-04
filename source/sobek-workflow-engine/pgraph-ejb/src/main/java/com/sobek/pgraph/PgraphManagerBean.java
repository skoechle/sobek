package com.sobek.pgraph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.exception.InvalidPgraphStructureException;
import com.sobek.pgraph.exception.NoSuchPgraphException;
import com.sobek.pgraph.material.Material;
import com.sobek.pgraph.material.RawMaterial;
import com.sobek.pgraph.operation.Operation;

@Stateless
public class PgraphManagerBean implements PgraphManagerLocal{
    private static final Logger logger = LoggerFactory.getLogger(PgraphManagerBean.class);
    
    @EJB
    private PgraphDao pgraphDao;
    
    @Override
    public List<Operation> startGraph(Object config, RawMaterial rawMaterial){
	// TODO Auto-generated method stub
	return null;
    }
    
    /*
     * Uses a Breadth First Search to find the ready Operations whose required resources are available.
     */
    @Override
    public List<Operation> getReadyOperations(long pgraphId) throws NoSuchPgraphException, NamingException{
	logger.debug("Getting ready operations for pgraphId {}.", pgraphId);
	
	// Make sure this graph exists.
	if(!pgraphDao.pgraphExists(pgraphId)){
	    String message = "No pgraph exists with pgraphId " + pgraphId + ".";
	    throw new NoSuchPgraphException(message);
	}
	
	// The list of operations that are ready.
	List<Operation> readyOperations = new LinkedList<Operation>();
	
	// Keeps track of nodes that need to be visited.
	Queue<NodeEntity> q = new LinkedList<NodeEntity>();	
	
	// Keeps track of nodes that have been visited.
	HashSet<NodeEntity> visitedNodes = new HashSet<NodeEntity>();

	// Start the search from the root node (raw material).
	NodeEntity root = pgraphDao.getRawMaterialNode(pgraphId);
	q.add(root);
	visitedNodes.add(root);
	
	while(!q.isEmpty()){
	    NodeEntity nodeEntity = q.remove();
	    
	    if(nodeEntity.getType().equals(NodeType.OPERATION)){
		Operation operation = (Operation)nodeEntity.getValue();
		
		switch(operation.getState()){
		    case NOT_STARTED: // If the operation is not yet started then check materials
			if(checkRequiredMaterials(nodeEntity)){
			    // We can start this operation if all required materials are available.
			    readyOperations.add(operation);
			}
			
			break;
		    case COMPLETE: // If this operation is completed then search the child nodes.
			addChildNodesToQueue(nodeEntity, q, visitedNodes);
			break;
		}
	    }else{ // The node is a material
		Material material = (Material)nodeEntity.getValue();

		// Only add Materials that are available
		if(material.isAvailable()){
		   addChildNodesToQueue(nodeEntity, q, visitedNodes);
		}
	    }
	}
	
	return readyOperations;
    }
    
    private void addChildNodesToQueue(NodeEntity nodeEntity, Queue<NodeEntity> q, HashSet<NodeEntity> visitedNodes) throws NamingException{
	List<NodeEntity> childNodes = pgraphDao.getChildNodes(nodeEntity.getId());
	
	for(NodeEntity childNodeEntity : childNodes){
	    if(!visitedNodes.contains(childNodeEntity)){
		q.add(childNodeEntity);
		visitedNodes.add(childNodeEntity);
	    }
	}
    }
    
    private boolean checkRequiredMaterials(NodeEntity nodeEntity) throws NamingException{
	boolean materialsAvailable = true;
	List<NodeEntity> materialNodes = pgraphDao.getParentNodes(nodeEntity.getId());
	Iterator<NodeEntity> materialNodeIterator = materialNodes.iterator();
	
	while(materialNodeIterator.hasNext() && materialsAvailable){
	    NodeEntity materialNodeEntity = materialNodeIterator.next();
	    
	    switch(materialNodeEntity.getType()){
		case INTERMEDIATE_PRODUCT:
		case RAW_MATERIAL:
		    Material material = (Material)materialNodeEntity.getValue();
		    materialsAvailable = material.isAvailable();
		    break;
		default:
		    String message = "Expected to find Material nodes while checking for required materials.";
		    logger.error(message);
		    throw new InvalidPgraphStructureException(message);
	    }
	}
	
	return materialsAvailable;
    }
}
