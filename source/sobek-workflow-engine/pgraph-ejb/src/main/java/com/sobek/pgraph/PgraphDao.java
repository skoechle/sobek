package com.sobek.pgraph;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.entity.PgraphEntity;
import com.sobek.pgraph.exception.InvalidPgraphStructureException;
import com.sobek.pgraph.exception.NoSuchPgraphException;

@Stateless
public class PgraphDao{
    
    private static final Logger logger = LoggerFactory.getLogger(PgraphDao.class);
   
    @PersistenceContext(unitName="sobek-pgraph")
    private EntityManager entityManager;
    
    /**
     * Gets the pgraph with pgraphId.
     * 
     * @param pgraphId 
     * @return The pgraph or null if none is found.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PgraphEntity getPgraph(long pgraphId){
	return entityManager.find(PgraphEntity.class, pgraphId);
    }
    
    /**
     * Gets a list of parent nodes or an empty list if none are found.
     * 
     * @param nodeId The id of the node to get parents for.
     * @return A list parent nodes or empty list if none are found.
     */
    public List<NodeEntity> getParentNodes(long nodeId){
	logger.trace("Getting parent nodes for nodeId {}.", nodeId);
	
	TypedQuery<NodeEntity> query = entityManager.createNamedQuery(NodeEntity.GET_PARENT_NODES_QUERY, NodeEntity.class);
	query.setParameter("nodeId", nodeId);
	
	List<NodeEntity> parentNodes = query.getResultList();
	
	logger.trace("Returning {} parent nodes for nodeId {}.", parentNodes.size(), nodeId);
	return parentNodes;
    }
    
    /**
     * Gets a list of child nodes or an empty list if none are found.
     * 
     * @param nodeId The id of the node to get childs for.
     * @return A list child nodes or empty list if none are found.
     */
    public List<NodeEntity> getChildNodes(long nodeId){
	logger.trace("Getting child nodes for nodeId {}.", nodeId);
	
	TypedQuery<NodeEntity> query = entityManager.createNamedQuery(NodeEntity.GET_CHILD_NODES_QUERY, NodeEntity.class);
	query.setParameter("nodeId", nodeId);
	
	List<NodeEntity> childNodes = query.getResultList();
	
	logger.trace("Returning {} child nodes for nodeId {}.", childNodes.size(), nodeId);
	return childNodes;
    }
    
    /**
     * Gets the raw material for a P-Graph.
     * 
     * @param pgraphId The pgraphId
     * @return The Raw Material of the P-Graph
     * @throws InvalidPgraphStructureException If there is not exactly one raw material found for the P-Graph
     * @throws NoSuchPgraphException If there is no P-Graph with pgraphId
     */
    public NodeEntity getRawMaterialNode(long pgraphId) throws InvalidPgraphStructureException, NoSuchPgraphException{
	logger.trace("Getting raw material for pgraphId {}.", pgraphId);

	TypedQuery<NodeEntity> query = entityManager.createNamedQuery(PgraphEntity.GET_RAW_MATERIAL_QUERY, NodeEntity.class);
	query.setParameter("pgraphId", pgraphId);

	try{
	    NodeEntity node = query.getSingleResult();
	    
	    logger.trace("Returning node with nodeId {} for pgraphId {}.", node.getId(), pgraphId);
	    return node;
	}catch(NoResultException e){
	    if(pgraphExists(pgraphId)){
		String message = "No raw material node exists for pgraphId " + pgraphId + ". There must always be exactly one raw material in a P-Graph.";
		logger.error(message, e);
		throw new InvalidPgraphStructureException(message);
	    }else{
		String message = "No p-graph exists for pgraphId " + pgraphId + ".";
		logger.error(message, e);
		throw new NoSuchPgraphException(message);
	    }
	}catch(NonUniqueResultException e){
	    String message = "Multiple raw materials were found for " + pgraphId + ". There must always be exactly one raw material in a P-Graph.";
	    logger.error(message, e);
	    throw new InvalidPgraphStructureException(message);
	}
    }
     
    /**
     * Checks if the P-Graph exists.
     * @param pgraphId The pgraphId to look for.
     * @return True if the P-Graph exists, false otherwise.
     */
    public boolean pgraphExists(long pgraphId){
	logger.trace("Checking if pgraphId {} exists.", pgraphId);
	
	TypedQuery<Integer> query = entityManager.createNamedQuery(PgraphEntity.COUNT_BY_ID_QUERY, Integer.class);
	query.setParameter("pgraphId", pgraphId);
	
	Integer result = query.getSingleResult();
	boolean exists = result > 0;
	
	logger.trace("Returning exists = {} for pgraphId {}", exists, pgraphId);
	return exists;
    }
}
