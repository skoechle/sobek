package com.sobek.pgraph;

import java.util.List;

import com.sobek.pgraph.material.RawMaterial;
import com.sobek.pgraph.operation.Operation;


/**
 * 
 * @author Matt
 *
 * The PgraphManager is responsible for orchestrating Operations and Materials in order to 
 * create a workflow. The relation of Operations and Materials are represented as a Process Graph
 * (p-graph).
 *
 */
public interface PgraphManager{
    
    /**
     * Creates and starts a new p-graph. A list of started operations
     * is returned.
     * 
     * @param config The configuration used to create the p-graph.
     * @param rawMaterial The RawMaterial used to start the p-graph.
     * @return A list of started operations.
     * 
     * @throws IllegalArgumentException If the config is invalid or if either parameter is null.
     */
    public List<Operation> start(Object config, RawMaterial rawMaterial);
}
