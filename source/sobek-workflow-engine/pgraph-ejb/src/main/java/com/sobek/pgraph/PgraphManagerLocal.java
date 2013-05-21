package com.sobek.pgraph;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.sobek.pgraph.entity.OperationEntity;

/**
 * 
 * @author Matt
 *
 * Local interface for PgraphManager
 */
@Local
public interface PgraphManagerLocal extends PgraphManager{

    long createPgraph(List<Edge> edges) throws InvalidPgraphStructureException, IllegalArgumentException;
    
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
    List<Operation> getReadyOperations(long pgraphId) throws NoSuchPgraphException, InvalidPgraphStructureException;

    List<Operation> start(long pGraphId, Serializable parameters);

    void updateOperation(long operationId, float percentComplete, OperationState state);

    List<Operation> completeOperation(long pgraphId, long operationId, Serializable material, String name);

    PgraphState getState(long pgraphId);

    void failOperation(long pgraphId, long operationId, String state);
}
