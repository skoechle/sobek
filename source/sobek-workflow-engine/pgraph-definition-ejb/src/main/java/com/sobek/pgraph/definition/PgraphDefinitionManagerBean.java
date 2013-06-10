package com.sobek.pgraph.definition;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sobek.pgraph.definition.entity.PgraphDefinition;

@Stateless
public class PgraphDefinitionManagerBean implements PgraphDefinitionManagerLocal, PgraphDefinitionManagerRemote {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PgraphDefinitionManager.class.getPackage().getName());

	@EJB
	private PgraphDefinitionDAOLocal dao;
	
	@Override
	public PgraphDefinition find(String name) {
		return this.dao.find(name);
	}
	
	@Override
	public boolean register(PgraphDefinition definition) {
		boolean returnValue = false;
		if(definition != null) {
			PgraphDefinition entity = this.dao.find(definition.getName());
			if(entity == null) {
				logger.log(
						Level.FINEST,
						"Replacing pgraph for workflow [{0}].",
						definition.getName());
				this.dao.create(definition);
				returnValue = true;
			} else {
				logger.log(
						Level.WARNING,
						"An attempt was made to register a configuration " +
						"object [{0}] that already exists.  The registration " +
						"request will be ignored.",
						definition);
			}
		} else {
			logger.log(
					Level.WARNING,
					"An attempt was made to register a null configuration " +
					"object [{0}].  The registration request will be ignored.",
					definition);
		}
		return returnValue;
	}

	@Override
	public boolean update(PgraphDefinition definition) {
		boolean returnValue = false;
		if(definition != null) {
			PgraphDefinition entity = this.dao.find(definition.getName());
			if(entity != null) {
				logger.log(
						Level.FINEST,
						"Replacing pgraph for workflow [{0}].",
						definition.getName());
				this.dao.update(entity);
				returnValue = true;
			} else {
				logger.log(
						Level.WARNING,
						"An attempt was made to update a configuration object " +
						"[{0}] that does not exist.  The update request will " +
						"be ignored.",
						definition);
			}
		} else {
			logger.log(
					Level.WARNING,
					"An attempt was made to update a null configuration " +
					"object [{0}].  The registration request will be ignored.",
					definition);
		}
		return returnValue;
	}
}
