package com.sobek.pgraph;

import java.util.List;


public interface PgraphDaoLocal{

    /**
     * Gets the pgraph with pgraphId.
     * 
     * @param pgraphId 
     * @return The pgraph or null if none is found.
     */
    public abstract PgraphEntity getPgraph(long pgraphId);

    /**
     * Gets a list of parent nodes or an empty list if none are found.
     * 
     * @param nodeId The id of the node to get parents for.
     * @return A list parent nodes or empty list if none are found.
     */
    public abstract List<NodeEntity> getParentNodes(long nodeId);

    /**
     * Gets a list of child nodes or an empty list if none are found.
     * 
     * @param nodeId The id of the node to get child nodes for.
     * @return A list child nodes or empty list if none are found.
     */
    public abstract List<NodeEntity> getChildNodes(long nodeId);

    /**
     * Gets the raw material for a P-Graph.
     * 
     * @param pgraphId The pgraphId
     * @return The Raw Material of the P-Graph
     * @throws InvalidPgraphStructureException If there is not exactly one raw material found for the P-Graph
     * @throws NoSuchPgraphException If there is no P-Graph with pgraphId
     */
    public abstract NodeEntity getRawMaterialNode(long pgraphId) throws InvalidPgraphStructureException, NoSuchPgraphException;

    /**
     * Checks if the P-Graph exists.
     * @param pgraphId The pgraphId to look for.
     * @return True if the P-Graph exists, false otherwise.
     */
    public abstract boolean pgraphExists(long pgraphId);

}