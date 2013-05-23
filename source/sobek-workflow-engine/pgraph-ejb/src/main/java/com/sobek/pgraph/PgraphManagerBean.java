package com.sobek.pgraph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sobek.pgraph.entity.MaterialEntity;
import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.pgraph.entity.PgraphEntity;
import com.sobek.pgraph.entity.RawMaterialEntity;

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
	
	//HashMap<Node, Long> persistedNodes = new HashMap<Node, Long>();
	
	// Add all the edges to the pgraph
//	for(Edge edge : edges){
//	    Node fromNode = edge.getFromNode();
//	    Node toNode = edge.getToNode();
//	    long fromNodeId = -1L;
//	    long toNodeId = -1L;
//	    
//	    // Persist unique nodes or get persisted node.
//	    if(persistedNodes.containsKey(fromNode)){
//		fromNodeId = persistedNodes.get(fromNode);
//	    }else{
//		NodeEntity nodeEntity = new NodeEntity(pgraphEntity.getId(), fromNode.getNodeType(), fromNode.getJndiName());
//		pgraphDao.addNode(nodeEntity);
//		
//		fromNodeId = nodeEntity.getId();
//		persistedNodes.put(fromNode, fromNodeId);
//	    }
//	    
//	    if(persistedNodes.containsKey(toNode)){
//		fromNodeId = persistedNodes.get(toNode);
//	    }else{
//		NodeEntity nodeEntity = new NodeEntity(pgraphEntity.getId(), toNode.getNodeType(), toNode.getJndiName());
//		pgraphDao.addNode(nodeEntity);
//		
//		toNodeId = nodeEntity.getId();
//		persistedNodes.put(toNode, toNodeId);
//	    }
//	    
//	    // Persist the edge
//	    EdgePrimaryKey edgePk = new EdgePrimaryKey(pgraphEntity.getId(), fromNodeId, toNodeId);
//	    EdgeEntity edgeEntity = new EdgeEntity(edgePk);
//	    pgraphDao.addEdge(edgeEntity);
//	}
	
	//--------------------------------------------------
	//           validate pgraph structure.
	//--------------------------------------------------
	//TODO
	boolean valid = true;
	StringBuilder validationErrors = new StringBuilder();
	
	if(!isConnected(pgraphEntity.getId())){
	    //valid = false;
	}

	if(!hasOneRawMaterial(pgraphEntity.getId())){
	    //valid = false;
	}
	
	if(!hasOneProduct(pgraphEntity.getId())){
	    //valid = false;
	}
	
	if(!isInterleaved(pgraphEntity.getId())){
	    //valid = false;
	}
	
	if(!valid){
	    String message = validationErrors.toString();
	    logger.error(message);
	    throw new InvalidPgraphStructureException(message);
	}
	
	return pgraphEntity.getId();
    }
    
    private boolean isConnected(long pgraphId){
	//TODO
	return true;
    }
        
    private boolean hasOneRawMaterial(long pgraphId){
	//TODO
	return true;
    }
    
    private boolean hasOneProduct(long pgraphId){
	//TODO
	return true;
    }
    
    private boolean isInterleaved(long pgraphId){
	//TODO
	return true;
    }
    
    /*
     * Uses a Breadth First Search to find the ready Operations whose required resources are available.
     */
    @Override
    public List<Operation> getReadyOperations(long pgraphId) throws NoSuchPgraphException, InvalidPgraphStructureException{
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
        		OperationEntity operationEntity = (OperationEntity)nodeEntity;
        		switch(operationEntity.getState()){
        		    case UNEVALUATED: // If the operation is not yet started then check materials
        			logger.trace("Node {} in pgraphId {} is not started.", nodeEntity, pgraphId);
        			
        			if(checkRequiredMaterials(operationEntity)){
        			    // We can start this operation if all required materials are available.
        			    logger.trace("All materials are available for node {} in pgraphId {}.", nodeEntity, pgraphId);
        			    Operation operation = new Operation(
        				   operationEntity.getId(), 
        				   operationEntity.getMessageQueueName(), 
        				   operationEntity.getState());
        			   
        			    readyOperations.add(operation);
        			}
        			
        			break;
        		    case COMPLETE: // If this operation is completed then search the child nodes.
        			logger.trace("Node {} in pgraphId {} is complete.", nodeEntity, pgraphId);
        			addChildNodesToQueue(nodeEntity, queuedNodes, visitedNodes);
        			break;
				default:
					break;
        		}
        		
        		break;     		
		case RAW_MATERIAL:
                case INTERMEDIATE_PRODUCT:
                case PRODUCT:
        		logger.trace("Node {} in pgraphId {} is a material.", nodeEntity, pgraphId);
        		
        		MaterialEntity materialEntity = (MaterialEntity)nodeEntity;
        		
        		// Only add Materials that are available
        		if(MaterialState.AVAILABLE.equals(materialEntity.getState())){
        		   logger.trace("Material is available for node {} in pgraphId {}.", nodeEntity, pgraphId);
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
    
    private void addChildNodesToQueue(NodeEntity nodeEntity, Queue<NodeEntity> q, HashSet<NodeEntity> visitedNodes){
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
    
    private boolean checkRequiredMaterials(OperationEntity operationEntity){
	logger.trace("Checking for required materials for node {}.", operationEntity);
	
	boolean materialsAvailable = true;
	Set<MaterialEntity> inputMaterials = operationEntity.getInputMaterials();
	
	if(inputMaterials.isEmpty()){
	    String message = "Expected to find at least one material that is required for nodeId " + operationEntity.getId() + ".";
	    logger.error(message);
	    throw new InvalidPgraphStructureException(message);
	}
	
	Iterator<MaterialEntity> materialNodeIterator = inputMaterials.iterator();
	
	logger.trace("Checking materials {} for node {}.", inputMaterials, operationEntity);
	while(materialNodeIterator.hasNext() && materialsAvailable){
	    NodeEntity materialNodeEntity = materialNodeIterator.next();
	    logger.trace("Checking material {} for node {}.", materialNodeEntity, operationEntity);
	    
	    switch(materialNodeEntity.getType()){
		case INTERMEDIATE_PRODUCT:
		case RAW_MATERIAL:
		    		    
		    materialsAvailable = MaterialState.AVAILABLE.equals(((MaterialEntity)materialNodeEntity).getState());
		    logger.trace("Available = {} for node {}.", materialsAvailable, operationEntity);
		    break;
		default:
		    String message = "Expected to find Material nodes while checking for required materials for nodeId " + operationEntity.getId() + ".";
		    logger.error(message);
		    throw new InvalidPgraphStructureException(message);
	    }
	}
	
	logger.trace("Returning materialsAvailable = {} for node {}.", materialsAvailable, operationEntity);
	return materialsAvailable;
    }
    
    @Override
    public void updateOperation(long operationId, float percentComplete, OperationState state) throws NoSuchOperationException{
	logger.debug("Operation ID {}, Percent complete {}, Operation state {} - Entering.", operationId, percentComplete, state);
	
	StringBuilder validationErrors = new StringBuilder();
	boolean parametersValid = true;
	
	if(percentComplete < 0f || percentComplete > 1f){
	    validationErrors.append("Percent complete must be between 0 and 1 inclusive.\n");
	    parametersValid = false;
	}
	
	if(state == null){
	    validationErrors.append("State cannot be null.\n");
	    parametersValid = false;
	}
	
	if(!parametersValid){
	    validationErrors.append("Parameters were:\n");
	    validationErrors.append("Operation ID: ").append(operationId).append("\n");
	    validationErrors.append("Percent complete: ").append(percentComplete).append("\n");
	    validationErrors.append("State: ").append(state);
	    
	    String message = validationErrors.toString();
	    logger.error(message);
	    throw new IllegalArgumentException(message);
	}
	
	logger.debug("Operation ID {} - Retrieving operation entity.", operationId);
	OperationEntity operationEntity = pgraphDao.getOperation(operationId);
	
	if(operationEntity == null){
	    String message = "No Operation exists with ID " + operationId + ".\n";
	    logger.error(message);
	    throw new NoSuchOperationException(message);
	}
	
	logger.debug("Operation ID {} - Updating operation entity.", operationId);
	
	operationEntity.setPercentComplete(percentComplete);
	operationEntity.setState(state);
	
	logger.debug("Operation ID {}, Percent complete {}, Operation state {} - Returning.", operationId, percentComplete, state);
    }

    @Override
    public List<Operation> start(long pgraphId, Serializable rawMaterial) throws InvalidPgraphStructureException, NoSuchPgraphException{
	logger.debug("Pgraph ID {} - Entering.", pgraphId);
	
	if(rawMaterial == null){
	    String message = "Raw Material cannot be null.";
	    logger.error(message);    
	    throw new IllegalArgumentException(message);
	}
		
	logger.debug("Pgraph ID {} - Updating Raw Material.", pgraphId);
	RawMaterialEntity rawMaterialEntity = pgraphDao.getRawMaterialNode(pgraphId);
	rawMaterialEntity.setState(MaterialState.AVAILABLE);
	rawMaterialEntity.setValue(rawMaterial);
	
	logger.debug("Pgraph ID {} - Searching for ready operations.", pgraphId);
	List<Operation> readyOperations = this.getReadyOperations(pgraphId);
	logger.debug("Pgraph ID {} - Found {} ready operations.", pgraphId, readyOperations.size());
	
	return readyOperations;
    }

    @Override
    public List<Operation> completeOperation(long operationId, long materialId, Serializable materialValue) throws NoSuchOperationException, NoSuchMaterialException{
	logger.debug("Operation ID {}, Material ID {} - Entering.", operationId, materialId);
	
	if(materialValue == null){
	    String message = "Material value cannot be null.";
	    logger.error(message);
	    throw new IllegalArgumentException(message);
	}
	
	logger.debug("Operation ID {}, Material ID {} - Retrieving Operation entity.", operationId, materialId);
	OperationEntity operationEntity = pgraphDao.getOperation(operationId);
	
	if(operationEntity == null){
	    String message = "No Operation exists with Operation ID " + operationId + ".";
	    logger.error(message);
	    throw new NoSuchOperationException(message);
	}
	
	logger.debug("Operation ID {}, Material ID {} - Retrieving Material entity.", operationId, materialId);
	MaterialEntity materialEntity =  pgraphDao.getMaterialEntity(materialId);
	
	if(materialEntity == null){
	    String message = "No Material exists with Material ID " + materialId + ".";
	    logger.error(message);
	    throw new NoSuchMaterialException(message);
	}
	
	if(!operationEntity.getOutputMaterials().contains(materialEntity)){
	    String message = "The Material with ID " + materialId + " is not an output material of Operation ID " + operationId;
	    logger.error(message);
	    throw new IllegalArgumentException(message);
	}
	
	logger.debug("Operation ID {}, Material ID {} - Updating Operation entity.", operationId, materialId);
	operationEntity.setPercentComplete(1.0f);
	operationEntity.setState(OperationState.COMPLETE);
	
	logger.debug("Operation ID {}, Material ID {} - Updating material entity.", operationId, materialId);
	materialEntity.setValue(materialValue);
	materialEntity.setState(MaterialState.AVAILABLE);
	
	logger.debug("Operation ID {}, Material ID {} - Checking for ready operations.", operationId, materialId);
	List<Operation> readyOperations;
	
	try{
	    readyOperations = getReadyOperations(operationEntity.getPgraphId());
	}catch(NoSuchPgraphException e){
	    String message = "The Operation with ID " + operationEntity.getId() + " belongs to Pgraph ID "
		    + operationEntity.getPgraphId() + ", however, that pgraph does not exist.";
	    logger.error(message);
	    throw new InvalidPgraphStructureException(message);
	}
	
	logger.debug("Operation ID {}, Material ID {} - Found {} ready operations.", operationId, materialId, readyOperations.size());
	return readyOperations;
    }

    @Override
    public PgraphState getState(long pGraphId){
	// TODO Auto-generated method stub
	return PgraphState.COMPLETE;
    }

    @Override
    public void failOperation(long operationId) throws NoSuchOperationException{
	// TODO Auto-generated method stub

    }
}