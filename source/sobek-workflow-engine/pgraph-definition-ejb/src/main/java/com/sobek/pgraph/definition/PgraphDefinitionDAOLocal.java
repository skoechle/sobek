package com.sobek.pgraph.definition;

import javax.ejb.Local;

import com.sobek.pgraph.definition.entity.PgraphDefinition;

@Local
public interface PgraphDefinitionDAOLocal {
	PgraphDefinition find(String name);

	void update(PgraphDefinition definition);

	void create(PgraphDefinition definition);
}
