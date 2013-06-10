package com.sobek.pgraph.definition;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sobek.pgraph.definition.entity.PgraphDefinition;

@Stateless
public class PgraphDefinitionDAOBean implements PgraphDefinitionDAOLocal {
	private static Logger logger =
			Logger.getLogger(PgraphDefinitionDAOBean.class.getPackage().getName());
	@PersistenceContext(name="pgraphDefinitionPU")
	private EntityManager manager;
	
	@Override
	public PgraphDefinition find(String name) {
		PgraphDefinition returnValue = null;
		if(name != null) {
			Query query = this.manager.createNamedQuery(PgraphDefinition.GET_DEFINITION_BY_NAME);
			query.setParameter(PgraphDefinition.NAME_PARAMETER, name);
			@SuppressWarnings("unchecked")
			List<PgraphDefinition> resultList = (List<PgraphDefinition>)query.getResultList();
			if(resultList != null && resultList.size() > 0) {
				returnValue = resultList.get(0);
			}
		}
		return returnValue;
	}

	@Override
	public void update(PgraphDefinition definition) {
		logger.log(Level.FINEST, "Entering, definition = [{0}].", definition);
		this.manager.persist(definition);
	}

	@Override
	public void create(PgraphDefinition definition) {
		logger.log(Level.FINEST, "Entering, definition = [{0}].", definition);
		this.manager.persist(definition);
	}
}
