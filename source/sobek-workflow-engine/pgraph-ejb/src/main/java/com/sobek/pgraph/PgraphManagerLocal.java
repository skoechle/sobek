package com.sobek.pgraph;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

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
     */
    List<Operation> getReadyOperations(long pgraphId) throws NoSuchPgraphException, InvalidPgraphStructureException;

    List<Operation> start(long pGraphId, Serializable rawMaterial) throws InvalidPgraphStructureException, NoSuchPgraphException;

    void updateOperation(long operationId, float percentComplete, OperationState state) throws NoSuchOperationException;

    List<Operation> completeOperation(long operationId, long materialId, Serializable materialValue) throws NoSuchOperationException, NoSuchMaterialException;

    PgraphState getState(long pgraphId);

    void failOperation(long operationId) throws NoSuchOperationException;
}
