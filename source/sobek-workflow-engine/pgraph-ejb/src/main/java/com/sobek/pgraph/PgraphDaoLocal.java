package com.sobek.pgraph;

import java.util.List;

import com.sobek.pgraph.entity.EdgeEntity;
import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.entity.PgraphEntity;


public interface PgraphDaoLocal{
    
    /**
     * Adds a pgraph
     * 
     * @param pgraphEntity
     */
    public void addPgraph(PgraphEntity pgraphEntity);
    
    /**
     * Adds a node
     * @param nodeEntity
     */
    public void addNode(NodeEntity nodeEntity);
    
    /**
     * Adds an edge
     * @param edgeEntity
     */
    public void addEdge(EdgeEntity edgeEntity);
    
    /**
     * Gets the pgraph with pgraphId.
     * 
     * @param pgraphId 
     * @return The pgraph or null if none is found.
     */
    public PgraphEntity getPgraph(long pgraphId);

    /**
     * Gets a list of parent nodes or an empty list if none are found.
     * 
     * @param nodeId The id of the node to get parents for.
     * @return A list parent nodes or empty list if none are found.
     */
    public List<NodeEntity> getParentNodes(long nodeId);

    /**
     * Gets a list of child nodes or an empty list if none are found.
     * 
     * @param nodeId The id of the node to get child nodes for.
     * @return A list child nodes or empty list if none are found.
     */
    public List<NodeEntity> getChildNodes(long nodeId);

    /**
     * Gets the raw material for a P-Graph.
     * 
     * @param pgraphId The pgraphId
     * @return The Raw Material of the P-Graph
     * @throws InvalidPgraphStructureException If there is not exactly one raw material found for the P-Graph
     * @throws NoSuchPgraphException If there is no P-Graph with pgraphId
     */
    public NodeEntity getRawMaterialNode(long pgraphId) throws InvalidPgraphStructureException, NoSuchPgraphException;

    /**
     * Checks if the P-Graph exists.
     * @param pgraphId The pgraphId to look for.
     * @return True if the P-Graph exists, false otherwise.
     */
    public boolean pgraphExists(long pgraphId);

}