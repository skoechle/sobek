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

	List<OperationEntity> start(long pGraphId, Serializable parameters);

	void updateOperation(long pGraphId, long operationId, float percentComplete, String status);

	List<OperationEntity> completeOperation(long pGraphId, long operationId, Serializable material, String name);

	PgraphState getState(long pGraphId);
}
