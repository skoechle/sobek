package com.sobek.pgraph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sobek.pgraph.entity.EdgeEntity;
import com.sobek.pgraph.entity.IntermediateProductEntity;
import com.sobek.pgraph.entity.MaterialEntity;
import com.sobek.pgraph.entity.MaterialValueEntity;
import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.pgraph.entity.PgraphEntity;
import com.sobek.pgraph.entity.ProductEntity;
import com.sobek.pgraph.entity.RawMaterialEntity;

@Stateless
public class PgraphManagerBean implements PgraphManagerLocal {
	private static final Logger logger = LoggerFactory
			.getLogger(PgraphManagerBean.class);

	@EJB
	private PgraphDaoLocal pgraphDao;

	@Override
	public long createPgraph(Pgraph pgraphDefinition)
			throws InvalidPgraphStructureException {
		
		HashMap<Long, Node> nodeDefinitionsByDefinitionId = new HashMap<Long, Node>();
		
		for(Node nodeDefinition : pgraphDefinition.getNodes()) {
			nodeDefinitionsByDefinitionId.put(nodeDefinition.getId(), nodeDefinition);
		}
		
		List<Edge> edgeDefinitions = pgraphDefinition.getEdges();
		// --------------------------------------------------
		// Validate parameters
		// --------------------------------------------------
		if (edgeDefinitions == null) {
			String message = "A null paramters was passed. parameters were:\n"
					+ "edges: " + edgeDefinitions;

			logger.error(message);
			throw new IllegalArgumentException(message);
		}

		if (edgeDefinitions.contains(null)) {
			String message = "The edge list contained a null value.";

			logger.error(message);
			throw new IllegalArgumentException(message);
		}

		// --------------------------------------------------
		// Persist the pgraph
		// --------------------------------------------------
		PgraphEntity pgraphEntity = new PgraphEntity();

		this.pgraphDao.addPgraph(pgraphEntity);
		
		HashMap<Long, NodeEntity> persistedNodesByDefinitionId = new HashMap<Long, NodeEntity>();

		// Add all the edges to the pgraph
		for (Edge edge : edgeDefinitions) {

			long fromNodeId = edge.getFromNode();
			long toNodeId = edge.getToNode();
			Node fromNodeDefinition = nodeDefinitionsByDefinitionId.get(fromNodeId);
			Node toNodeDefinition = nodeDefinitionsByDefinitionId.get(toNodeId);
			NodeEntity fromNodeEntity = null;
			NodeEntity toNodeEntity = null;

			// Persist unique nodes or get persisted node.
			if (persistedNodesByDefinitionId.containsKey(fromNodeId)) {
				fromNodeEntity = persistedNodesByDefinitionId.get(fromNodeId);
			} else {
				fromNodeEntity =
						createNode(pgraphEntity, fromNodeDefinition, persistedNodesByDefinitionId);
			}

			if (persistedNodesByDefinitionId.containsKey(toNodeId)) {
				toNodeEntity = persistedNodesByDefinitionId.get(toNodeId);
			} else {
				toNodeEntity =
						createNode(pgraphEntity, toNodeDefinition, persistedNodesByDefinitionId);
			}
			
			// TODO: Add error handling to code below
			if(fromNodeEntity instanceof OperationEntity) {
				OperationEntity operation = (OperationEntity)fromNodeEntity;
				operation.addOutputMaterial((MaterialEntity)toNodeEntity);
			} else {
				OperationEntity operation = (OperationEntity)toNodeEntity;
				operation.addInputMaterial((MaterialEntity)fromNodeEntity);
			}
			
			this.pgraphDao.addNode(fromNodeEntity);
			this.pgraphDao.addNode(toNodeEntity);

			// Persist the edge
			new EdgeEntity(pgraphEntity, fromNodeEntity, toNodeEntity);
		}

		// --------------------------------------------------
		// validate pgraph structure.
		// --------------------------------------------------
		// TODO
		boolean valid = true;
		StringBuilder validationErrors = new StringBuilder();

		if (!isConnected(pgraphEntity.getId())) {
			// valid = false;
		}

		if (!hasOneRawMaterial(pgraphEntity.getId())) {
			// valid = false;
		}

		if (!hasOneProduct(pgraphEntity.getId())) {
			// valid = false;
		}

		if (!isInterleaved(pgraphEntity.getId())) {
			// valid = false;
		}

		if (!valid) {
			String message = validationErrors.toString();
			logger.error(message);
			throw new InvalidPgraphStructureException(message);
		}

		return pgraphEntity.getId();
	}

	private NodeEntity createNode(
			PgraphEntity pgraph,
			Node nodeDefinition,
			HashMap<Long, NodeEntity> persistedNodesByDefinitionId)
	{
		System.out.println("======================================================================");
		System.out.println("======================================================================");
		System.out.println("Creating node \n" + nodeDefinition);
		System.out.println("======================================================================");
		System.out.println("======================================================================");
		NodeEntity entityToStore = null;

		switch(nodeDefinition.getNodeType()) {
		case INTERMEDIATE_PRODUCT:
			IntermediateProduct intermediateProduct = (IntermediateProduct)nodeDefinition;
			entityToStore = new IntermediateProductEntity(intermediateProduct.getName());
			break;
		case OPERATION:
			Operation operation = (Operation)nodeDefinition;
			entityToStore = new OperationEntity(operation.getName(), operation.getMessageQueueName());
			break;
		case PRODUCT:
			Product product = (Product)nodeDefinition;
			entityToStore = new ProductEntity(product.getName());
			break;
		case RAW_MATERIAL:
			RawMaterial rawMaterial = (RawMaterial)nodeDefinition;
			entityToStore = new RawMaterialEntity(rawMaterial.getName());
			pgraph.setRawMaterial((RawMaterialEntity)entityToStore);
			break;
		default:
			throw new IllegalStateException(
					"An implementation for node type [" + nodeDefinition.getNodeType() +
					"] has not been created, the node type cannot be processed.");
		}

		persistedNodesByDefinitionId.put(nodeDefinition.getId(), entityToStore);

		return entityToStore;
	}

	private boolean isConnected(long pgraphId) {
		// TODO
		return true;
	}

	private boolean hasOneRawMaterial(long pgraphId) {
		// TODO
		return true;
	}

	private boolean hasOneProduct(long pgraphId) {
		// TODO
		return true;
	}

	private boolean isInterleaved(long pgraphId) {
		// TODO
		return true;
	}

	/*
	 * Uses a Breadth First Search to find the ready Operations whose required
	 * resources are available.
	 */
	@Override
	public List<Operation> getReadyOperations(MaterialEntity material)
			throws NoSuchMaterialException, InvalidPgraphStructureException {
		logger.debug("Getting ready operations for material {}.", material);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("===================================================================================\n");
		stringBuilder.append("===================================================================================\n");
		stringBuilder.append("===================================================================================\n");
		stringBuilder.append("===================================================================================\n");

		// The list of operations that are ready.
		List<Operation> readyOperations = new LinkedList<Operation>();

		// Start the search from the root node (raw material).
		logger.debug("Getting the dependent operations for material {}.", material);
		Set<OperationEntity> dependentOperations = material.getDependencies();
		stringBuilder.append("\nmaterial.getDependencies() returned list : " + dependentOperations);
		stringBuilder.append("\nmaterial.getDependencies().size returned size : " + dependentOperations.size());
		logger.debug("Number of dependent operations was {}", dependentOperations.size());
		
		for(OperationEntity operation : dependentOperations) {
			stringBuilder.append("\nprocessing operation : " + operation);
			stringBuilder.append("\noperation.hasAllInputs() returned : " + operation.hasAllInputs());
			if(operation.hasAllInputs()) {
				Operation readyOperation = new Operation(operation.getId(), operation.getName(), operation.getMessageQueueName());
				readyOperations.add(readyOperation);
			}
		}


		stringBuilder.append("\n===================================================================================\n");
		stringBuilder.append("===================================================================================\n");
		stringBuilder.append("===================================================================================\n");
		stringBuilder.append("===================================================================================\n");
		System.out.println("\n" + stringBuilder.toString());
		logger.debug("Returning {} for material {}.", readyOperations, material);
		return readyOperations;
	}

	@Override
	public void updateOperation(long operationId, int percentComplete,
			OperationState state) throws NoSuchOperationException {
		logger.debug(
				"Operation ID {}, Percent complete {}, Operation state {} - Entering.",
				operationId, percentComplete, state);

		StringBuilder validationErrors = new StringBuilder();
		boolean parametersValid = true;

		if (percentComplete < 0 || percentComplete > 100) {
			validationErrors
					.append("Percent complete must be between 0 and 1 inclusive.\n");
			parametersValid = false;
		}

		if (state == null) {
			validationErrors.append("State cannot be null.\n");
			parametersValid = false;
		}

		if (!parametersValid) {
			validationErrors.append("Parameters were:\n");
			validationErrors.append("Operation ID: ").append(operationId)
					.append("\n");
			validationErrors.append("Percent complete: ")
					.append(percentComplete).append("\n");
			validationErrors.append("State: ").append(state);

			String message = validationErrors.toString();
			logger.error(message);
			throw new IllegalArgumentException(message);
		}

		logger.debug("Operation ID {} - Retrieving operation entity.",
				operationId);
		OperationEntity operationEntity = pgraphDao.getOperation(operationId);

		if (operationEntity == null) {
			String message = "No Operation exists with ID " + operationId
					+ ".\n";
			logger.error(message);
			throw new NoSuchOperationException(message);
		}

		logger.debug("Operation ID {} - Updating operation entity.",
				operationId);

		operationEntity.setPercentComplete(percentComplete);
		operationEntity.setState(state);

		logger.debug(
				"Operation ID {}, Percent complete {}, Operation state {} - Returning.",
				operationId, percentComplete, state);
	}

	@Override
	public List<Operation> start(long pgraphId, Serializable rawMaterial)
			throws InvalidPgraphStructureException, NoSuchPgraphException, NoSuchMaterialException {
		logger.debug("Pgraph ID {} - Entering.", pgraphId);

		if (rawMaterial == null) {
			String message = "Raw Material cannot be null.";
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		PgraphEntity pgraph = this.pgraphDao.getPgraph(pgraphId);

		logger.debug("Pgraph ID {} - Updating Raw Material.", pgraphId);
		RawMaterialEntity rawMaterialEntity = pgraph.getRawMaterial();
		new MaterialValueEntity(rawMaterialEntity, rawMaterial);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("===================================================================================\n");
		stringBuilder.append("===================================================================================\n");
		stringBuilder.append("===================================================================================\n");
		stringBuilder.append("===================================================================================\n");

		Set<OperationEntity> dependentOperations = rawMaterialEntity.getDependencies();
		stringBuilder.append("\nmaterial.getDependencies() returned list : " + dependentOperations);
		stringBuilder.append("\nmaterial.getDependencies().size returned size : " + dependentOperations.size());

		stringBuilder.append("\n===================================================================================\n");
		stringBuilder.append("===================================================================================\n");
		stringBuilder.append("===================================================================================\n");
		stringBuilder.append("===================================================================================\n");
		System.out.println("\n" + stringBuilder.toString());

		logger.debug("Pgraph ID {} - Searching for ready operations.", pgraphId);
		List<Operation> readyOperations = this.getReadyOperations(rawMaterialEntity);
		logger.debug("Pgraph ID {} - Found {} ready operations.", pgraphId,
				readyOperations.size());

		return readyOperations;
	}

	@Override
	public List<Operation> completeOperation(
			long operationId,
			String materialName,
			Serializable materialValue) throws NoSuchOperationException,
			NoSuchMaterialException {
		logger.debug("Entering, Operation ID {}, Material Name {}, Material Value {}",
				operationId, materialName, materialValue);

		if (materialValue == null) {
			String message = "Material value cannot be null.";
			logger.error(message);
			throw new IllegalArgumentException(message);
		}

		logger.debug(
				"Operation ID {}, Material ID {} - Retrieving Operation entity.",
				operationId, materialName);
		OperationEntity operationEntity = pgraphDao.getOperation(operationId);

		if (operationEntity == null) {
			String message = "No Operation exists with Operation ID "
					+ operationId + ".";
			logger.error(message);
			throw new NoSuchOperationException(message);
		}

		logger.debug(
				"Operation ID {}, Material ID {} - Retrieving Material entity.",
				operationId, materialName);
		
		MaterialEntity materialEntity = null;

		for(MaterialEntity tempMaterialEntity : operationEntity.getOutputMaterials()) {
			if(tempMaterialEntity.getName().equals(materialName)) {
				materialEntity = tempMaterialEntity;
				break;
			}
		}

		if (materialEntity == null) {
			String message = "No Material exists with Material ID "
					+ materialName + ".";
			logger.error(message);
			throw new NoSuchMaterialException(message);
		}

		logger.debug(
				"Operation ID {}, Material ID {} - Updating Operation entity.",
				operationId, materialName);
		operationEntity.setPercentComplete(100);
		operationEntity.setState(OperationState.COMPLETE);

		logger.debug(
				"Operation ID {}, Material ID {} - Updating material entity.",
				operationId, materialName);
		
		new MaterialValueEntity(materialEntity, materialValue);

		logger.debug(
				"Operation ID {}, Material ID {} - Checking for ready operations.",
				operationId, materialName);
		List<Operation> readyOperations;

		try {
			readyOperations = getReadyOperations(materialEntity);
		} catch (NoSuchMaterialException e) {
			String message = "The Operation with ID " + operationEntity.getId()
					+ " does not contain a material with ID " + materialName + ".";
			logger.error(message);
			throw new InvalidPgraphStructureException(message);
		}

		logger.debug(
				"Operation ID {}, Material ID {} - Found {} ready operations.",
				operationId, materialName, readyOperations.size());
		return readyOperations;
	}

	@Override
	public PgraphState getState(long pGraphId) {
		// TODO Auto-generated method stub
		return PgraphState.COMPLETE;
	}

	@Override
	public void failOperation(long operationId) throws NoSuchOperationException {
		// TODO Auto-generated method stub

	}
}