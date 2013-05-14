package com.sobek.pgraph;

import java.util.List;

import javax.naming.NamingException;

import com.sobek.pgraph.operation.Operation;



/**
 * 
 * @author Matt
 *
 * The PgraphManager is responsible for orchestrating Operations and Materials in order to 
 * create a workflow. The relation of Operations and Materials are represented as a Process Graph
 * (P-Graph).
 *
 */
public interface PgraphManager{
    
    
    public long createPgraph(List<Edge> edges) throws InvalidPgraphStructureException, IllegalArgumentException;
    
    
    /**
     * Returns a list of operations for a P-Graph that have not started and are ready to be started. This
     * implies that all resource required to start any given operation are available.
     * 
     * @param pgraphId The id of the P-Graph
     * @return A list of ready operations.
     * 
     * @throws NoSuchPgraphException If there is no pgraph with pgraphId.
     * @throws InvalidPgraphStructureException If the structure of the pgraph is invalid.
     * @throws NamingException If one of the node jndi names in the P-Graph failed to be looked up.
     */
    public List<Operation> getReadyOperations(long pgraphId) throws NoSuchPgraphException, InvalidPgraphStructureException, NamingException;
}
