package com.sobek.pgraph;

import java.util.List;

import com.sobek.pgraph.entity.EdgeEntity;
import com.sobek.pgraph.entity.MaterialEntity;
import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.pgraph.entity.PgraphEntity;


public interface PgraphDaoLocal{
    
    /**
     * Adds a pgraph
     * 
     * @param pgraphEntity
     */
    void addPgraph(PgraphEntity pgraphEntity);
    
    /**
     * Adds a node
     * 
     * @param nodeEntity
     */
    void addNode(NodeEntity nodeEntity);
    
    /**
     * Adds an edge
     * 
     * @param edgeEntity
     */
    void addEdge(EdgeEntity edgeEntity);
    
    /**
     * Gets the pgraph with pgraphId.
     * 
     * @param pgraphId 
     * 
     * @return The pgraph or null if none is found.
     */
    PgraphEntity getPgraph(long pgraphId);

    /**
     * Gets a list of parent nodes or an empty list if none are found.
     * 
     * @param nodeId The id of the node to get parents for.
     * 
     * @return A list parent nodes or empty list if none are found.
     */
    List<NodeEntity> getParentNodes(long nodeId);

    /**
     * Gets a list of child nodes or an empty list if none are found.
     * 
     * @param nodeId The id of the node to get child nodes for.
     * 
     * @return A list child nodes or empty list if none are found.
     */
    List<NodeEntity> getChildNodes(long nodeId);
    
    /**
     * Gets the Operation with a Node ID.
     * 
     * @param nodeId - The Node ID of the Operation.
     * 
     * @return The operation or null if not found.
     */
    OperationEntity getOperation(long nodeId);
    
    /**
     * Gets the Material with a Node ID.
     *  
     * @param nodeId - The Node ID of the Material
     * 
     * @return - The Material or null if none is found.
     */
    MaterialEntity getMaterialEntity(long nodeId);

    /**
     * Checks if the P-Graph exists.
     * 
     * @param pgraphId The pgraphId to look for.
     * 
     * @return True if the P-Graph exists, false otherwise.
     */
    boolean pgraphExists(long pgraphId);

}