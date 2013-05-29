package com.sobek.pgraph;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sobek.pgraph.entity.EdgeEntity;
import com.sobek.pgraph.entity.MaterialEntity;
import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.pgraph.entity.PgraphEntity;
import com.sobek.pgraph.entity.RawMaterialEntity;

@Stateless
public class PgraphDaoBean implements PgraphDaoLocal {

	private static final Logger logger = LoggerFactory
			.getLogger(PgraphDaoBean.class);

	@PersistenceContext(unitName = "sobek-pgraph")
	private EntityManager entityManager;

	@Override
	public void addPgraph(PgraphEntity pgraphEntity) {
		this.entityManager.persist(pgraphEntity);
		this.entityManager.flush();
	}

	@Override
	public void addNode(NodeEntity nodeEntity) {
		this.entityManager.persist(nodeEntity);
		this.entityManager.flush();
	}

	@Override
	public void addEdge(EdgeEntity edgeEntity) {
		this.entityManager.persist(edgeEntity);
		this.entityManager.flush();
	}

	@Override
	public PgraphEntity getPgraph(long pgraphId) {
		return entityManager.find(PgraphEntity.class, pgraphId);
	}

	@Override
	public List<NodeEntity> getParentNodes(long nodeId) {
		logger.trace("Getting parent nodes for nodeId {}.", nodeId);

		TypedQuery<NodeEntity> query = entityManager.createNamedQuery(
				NodeEntity.GET_PARENT_NODES_QUERY, NodeEntity.class);
		query.setParameter("nodeId", nodeId);

		List<NodeEntity> parentNodes = query.getResultList();

		logger.trace("Returning {} parent nodes for nodeId {}.",
				parentNodes.size(), nodeId);
		return parentNodes;
	}

	@Override
	public List<NodeEntity> getChildNodes(long nodeId) {
		logger.trace("Getting child nodes for nodeId {}.", nodeId);

		TypedQuery<NodeEntity> query = entityManager.createNamedQuery(
				NodeEntity.GET_CHILD_NODES_QUERY, NodeEntity.class);
		query.setParameter("nodeId", nodeId);

		List<NodeEntity> childNodes = query.getResultList();

		logger.trace("Returning {} child nodes for nodeId {}.",
				childNodes.size(), nodeId);
		return childNodes;
	}

	@Override
	public OperationEntity getOperation(long nodeId) {
		return entityManager.find(OperationEntity.class, nodeId);
	}

	@Override
	public MaterialEntity getMaterialEntity(long nodeId) {
		return entityManager.find(MaterialEntity.class, nodeId);
	}

	@Override
	public RawMaterialEntity getRawMaterialNode(long pgraphId)
			throws InvalidPgraphStructureException, NoSuchPgraphException {
		logger.trace("Getting raw material for pgraphId {}.", pgraphId);

		TypedQuery<RawMaterialEntity> query = entityManager.createNamedQuery(
				PgraphEntity.GET_RAW_MATERIAL_QUERY, RawMaterialEntity.class);
		query.setParameter("pgraphId", pgraphId);

		try {
			RawMaterialEntity node = query.getSingleResult();
			logger.trace("Returning node with nodeId {} for pgraphId {}.",
					node.getId(), pgraphId);
			return node;
		} catch (NoResultException e) {
			if (pgraphExists(pgraphId)) {
				String message = "No raw material node exists for pgraphId "
						+ pgraphId
						+ ". There must always be exactly one raw material in a pgraph.";
				logger.error(message, e);
				throw new InvalidPgraphStructureException(message);
			} else {
				String message = "No pgraph exists for pgraphId " + pgraphId
						+ ".";
				logger.error(message, e);
				throw new NoSuchPgraphException(message);
			}
		} catch (NonUniqueResultException e) {
			String message = "Multiple raw materials were found for "
					+ pgraphId
					+ ". There must always be exactly one raw material in a pgraph.";
			logger.error(message, e);
			throw new InvalidPgraphStructureException(message);
		}
	}

	@Override
	public boolean pgraphExists(long pgraphId) {
		logger.trace("Checking if pgraphId {} exists.", pgraphId);

		TypedQuery<Long> query = entityManager.createNamedQuery(
				PgraphEntity.COUNT_BY_ID_QUERY, Long.class);
		query.setParameter("pgraphId", pgraphId);

		Long result = query.getSingleResult();
		boolean exists = result > 0;

		logger.trace("Returning exists = {} for pgraphId {}", exists, pgraphId);
		return exists;
	}
}
