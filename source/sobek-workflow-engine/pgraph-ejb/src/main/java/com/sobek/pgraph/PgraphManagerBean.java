package com.sobek.pgraph;

import java.util.HashMap;
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

import com.sobek.pgraph.entity.EdgeEntity;
import com.sobek.pgraph.entity.EdgePrimaryKey;
import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.entity.PgraphEntity;
import com.sobek.pgraph.material.Material;
import com.sobek.pgraph.operation.Operation;


@Stateless
public class PgraphManagerBean implements PgraphManagerLocal{
    private static final Logger logger = LoggerFactory.getLogger(PgraphManagerBean.class);
    
    @EJB
    private PgraphDaoLocal pgraphDao;
    
    @Override
    public long createPgraph(List<Edge> edges) throws InvalidPgraphStructureException{
	//--------------------------------------------------
	//              Validate parameters
	//--------------------------------------------------
	if(edges == null){
	    String message = "A null paramters was passed. parameters were:\n" + 
		    "edges: " + edges;
	    
	    logger.error(message);
	    throw new IllegalArgumentException(message);
	}
	
	if(edges.contains(null)){
	    String message = "The edge list contained a null value.";
	    
	    logger.error(message);
	    throw new IllegalArgumentException(message);
	}
	
	//--------------------------------------------------
	//              Persist the pgraph
	//--------------------------------------------------
	PgraphEntity pgraphEntity = new PgraphEntity();
	pgraphDao.addPgraph(pgraphEntity);
	
	HashMap<Node, Long> persistedNodes = new HashMap<Node, Long>();
	
	// Add all the edges to the pgraph
	for(Edge edge : edges){
	    Node fromNode = edge.getFromNode();
	    Node toNode = edge.getToNode();
	    long fromNodeId = -1L;
	    long toNodeId = -1L;
	    
	    // Persist unique nodes or get persisted node.
	    if(persistedNodes.containsKey(fromNode)){
		fromNodeId = persistedNodes.get(fromNode);
	    }else{
		NodeEntity nodeEntity = new NodeEntity(pgraphEntity.getId(), fromNode.getNodeType(), fromNode.getJndiName());
		pgraphDao.addNode(nodeEntity);
		
		fromNodeId = nodeEntity.getId();
		persistedNodes.put(fromNode, fromNodeId);
	    }
	    
	    if(persistedNodes.containsKey(toNode)){
		fromNodeId = persistedNodes.get(toNode);
	    }else{
		NodeEntity nodeEntity = new NodeEntity(pgraphEntity.getId(), toNode.getNodeType(), toNode.getJndiName());
		pgraphDao.addNode(nodeEntity);
		
		toNodeId = nodeEntity.getId();
		persistedNodes.put(toNode, toNodeId);
	    }
	    
	    // Persist the edge
	    EdgePrimaryKey edgePk = new EdgePrimaryKey(pgraphEntity.getId(), fromNodeId, toNodeId);
	    EdgeEntity edgeEntity = new EdgeEntity(edgePk);
	    pgraphDao.addEdge(edgeEntity);
	}
	
	//--------------------------------------------------
	//           validate pgraph structure.
	//--------------------------------------------------
//	boolean valid = true;
//	StringBuilder validationErrors = new StringBuilder();
//	
//	if(!isConnected(pgraphEntity.getId())){
//	    valid = false;
//	}
//
//	if(isCyclic(pgraphEntity.getId())){
//	    valid = false;
//	}
//	
//	if(!hasOneRawMaterial(pgraphEntity.getId())){
//	    valid = false;
//	}
//	
//	if(!hasOneProduct(pgraphEntity.getId())){
//	    valid = false;
//	}
//	
//	if(!isInterleaved(pgraphEntity.getId())){
//	    valid = false;
//	}
//	
//	if(!valid){
//	    String message = validationErrors.toString();
//	    logger.error(message);
//	    throw new InvalidPgraphStructureException(message);
//	}
	
	return pgraphEntity.getId();
    }
    
    /*
     * Uses a Breadth First Search to find the ready Operations whose required resources are available.
     */
    @Override
    public List<Operation> getReadyOperations(long pgraphId) throws NoSuchPgraphException, InvalidPgraphStructureException, NamingException{
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
    
    private boolean checkRequiredMaterials(NodeEntity nodeEntity) throws NamingException, InvalidPgraphStructureException{
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
    
    private boolean isConnected(long pgraphId){
	//TODO
	return false;
    }
    
    private boolean isCyclic(long pgraphId){
	//TODO
	return false;
    }
    
    private boolean hasOneRawMaterial(long pgraphId){
	//TODO
	return false;
    }
    
    private boolean hasOneProduct(long pgraphId){
	//TODO
	return false;
    }
    
    private boolean isInterleaved(long pgraphId){
	//TODO
	return false;
    }
}
