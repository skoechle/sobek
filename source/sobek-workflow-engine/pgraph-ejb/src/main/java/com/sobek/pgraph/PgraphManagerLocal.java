package com.sobek.pgraph;

import javax.ejb.Local;

/**
 * 
 * @author Matt
 *
 * Local interface for PgraphManager
 */
@Local
public interface PgraphManagerLocal extends PgraphManager{

	void updateOperation(long pGraphId, long operationId,
			float percentComplete, String status);

}
