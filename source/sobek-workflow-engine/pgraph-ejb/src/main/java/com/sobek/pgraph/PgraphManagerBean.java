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

import com.sobek.pgraph.material.Material;
import com.sobek.pgraph.material.RawMaterial;
import com.sobek.pgraph.operation.Operation;

@Stateless
public class PgraphManagerBean implements PgraphManagerLocal{
    private static final Logger logger = LoggerFactory.getLogger(PgraphManagerBean.class);
    
    @EJB
    private PgraphDaoLocal pgraphDao;
      
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
	Queue<NodeEntity> queuedNodes = new LinkedList<NodeEntity>();	
	
	// Keeps track of nodes that have been visited.
	HashSet<NodeEntity> visitedNodes = new HashSet<NodeEntity>();

	// Start the search from the root node (raw material).
	logger.debug("Getting root node for pgraphId {}.", pgraphId);
	NodeEntity root = pgraphDao.getRawMaterialNode(pgraphId);
	logger.debug("Got root node {} for pgraphId {}.", root, pgraphId);
	
	queuedNodes.add(root);
	visitedNodes.add(root);
	
	while(!queuedNodes.isEmpty()){
	    NodeEntity nodeEntity = queuedNodes.remove();
	    logger.trace("Visiting node {} in pgraphId {}.", nodeEntity, pgraphId);
	    
	    switch(nodeEntity.getType()){
		case OPERATION:
        		logger.trace("Node {} in pgraphId {} is an operation.", nodeEntity, pgraphId);
        		Operation operation = (Operation)nodeEntity.getValue();
        		logger.trace("The node value is {} for node {} in pgraphId {}.", operation, nodeEntity, pgraphId);
        		
        		switch(operation.getState()){
        		    case NOT_STARTED: // If the operation is not yet started then check materials
        			logger.trace("Node {} in pgraphId {} is not started.", nodeEntity, pgraphId);
        			
        			if(checkRequiredMaterials(nodeEntity)){
        			    // We can start this operation if all required materials are available.
        			    logger.trace("All materials are available for node {} in pgraphId {}.", nodeEntity, pgraphId);
        			    readyOperations.add(operation);
        			}
        			
        			break;
        		    case COMPLETE: // If this operation is completed then search the child nodes.
        			logger.trace("Node {} in pgraphId {} is complete.", nodeEntity, pgraphId);
        			addChildNodesToQueue(nodeEntity, queuedNodes, visitedNodes);
        			break;
        		}
        		
        		break;     		
		case RAW_MATERIAL:
                case INTERMEDIATE_PRODUCT:
                case PRODUCT:
        		logger.trace("Node {} in pgraphId {} is a material.", nodeEntity, pgraphId);
        		Material material = (Material)nodeEntity.getValue();
        		logger.trace("The node value is {} for node {} in pgraphId {}.", material, nodeEntity, pgraphId);
        		
        		// Only add Materials that are available
        		if(material.isAvailable()){
        		    logger.trace("Material {} is available for node {} in pgraphId {}.", material, nodeEntity, pgraphId);
        		   addChildNodesToQueue(nodeEntity, queuedNodes, visitedNodes);
        		}
        		
        		break;
	    }
	    
	    logger.trace("Finished visiting node {} in pgraphId {}.", nodeEntity, pgraphId);
	    logger.trace("pgraphId {}.\n Queued nodes: {}\n Visited nodes: {}\n Ready operations: {}", pgraphId, queuedNodes, visitedNodes, readyOperations);	  
	}
	
	logger.debug("Returning {} for pgraphId {}.", readyOperations, pgraphId);
	return readyOperations;
    }
    
    private void addChildNodesToQueue(NodeEntity nodeEntity, Queue<NodeEntity> q, HashSet<NodeEntity> visitedNodes) throws NamingException{
	logger.trace("Getting child nodes for node {}.", nodeEntity);
	List<NodeEntity> childNodes = pgraphDao.getChildNodes(nodeEntity.getId());
	logger.trace("Got child nodes {} for node {}.", childNodes, nodeEntity);
	
	for(NodeEntity childNodeEntity : childNodes){
	    if(!visitedNodes.contains(childNodeEntity)){
		q.add(childNodeEntity);
		visitedNodes.add(childNodeEntity);
	    }
	}
    }
    
    private boolean checkRequiredMaterials(NodeEntity nodeEntity) throws NamingException{
	logger.trace("Checking for required materials for node {}.", nodeEntity);
	
	boolean materialsAvailable = true;
	List<NodeEntity> materialNodes = pgraphDao.getParentNodes(nodeEntity.getId());
	
	if(materialNodes.isEmpty()){
	    String message = "Expected to find at least one material that is required for nodeId " + nodeEntity.getId() + ".";
	    logger.error(message);
	    throw new InvalidPgraphStructureException(message);
	}
	
	Iterator<NodeEntity> materialNodeIterator = materialNodes.iterator();
	
	logger.trace("Checking materials {} for node {}.", materialNodes, nodeEntity);
	while(materialNodeIterator.hasNext() && materialsAvailable){
	    NodeEntity materialNodeEntity = materialNodeIterator.next();
	    logger.trace("Checking material {} for node {}.", materialNodeEntity, nodeEntity);
	    
	    switch(materialNodeEntity.getType()){
		case INTERMEDIATE_PRODUCT:
		case RAW_MATERIAL:
		    Material material = (Material)materialNodeEntity.getValue();
		    logger.trace("Material value is {} for node {}.", material, materialNodes);
		    
		    materialsAvailable = material.isAvailable();
		    logger.trace("Available = {} for material {} and node {}.", materialsAvailable, material, nodeEntity);
		    break;
		default:
		    String message = "Expected to find Material nodes while checking for required materials for nodeId " + nodeEntity.getId() + ".";
		    logger.error(message);
		    throw new InvalidPgraphStructureException(message);
	    }
	}
	
	logger.trace("Returning materialsAvailable = {} for node {}.", materialsAvailable, nodeEntity);
	return materialsAvailable;
    }
}
