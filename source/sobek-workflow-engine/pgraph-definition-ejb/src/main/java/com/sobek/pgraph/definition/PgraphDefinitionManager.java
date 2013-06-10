package com.sobek.pgraph.definition;

import java.io.Serializable;

import com.sobek.pgraph.definition.entity.PgraphDefinition;

public interface PgraphDefinitionManager extends Serializable {
	PgraphDefinition find(String name);
	boolean register(PgraphDefinition definition);
	boolean update(PgraphDefinition definition);
}
