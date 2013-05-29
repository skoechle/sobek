package com.sobek.workflow.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sobek.common.util.SystemProperties;
import com.sobek.pgraph.Pgraph;

@Entity
@Table(name="WORKFLOW_DEFINITION")
@NamedQueries({
		@NamedQuery(name=WorkflowDefinition.GET_CONFIG_BY_NAME, query="SELECT wfd FROM WorkflowDefinition wfd WHERE wfd.name = :" + WorkflowDefinition.NAME_PARAMETER)
	})
public class WorkflowDefinition {
	
	public static final Logger logger = Logger.getLogger(WorkflowDefinition.class.getPackage().getName());
	public static final String GET_CONFIG_BY_NAME = "WorkflowConfigurationEntity.getConfigByName";
	public static final String NAME_PARAMETER = "name";

	@Id
	@Column(name="NAME")
	private String name = "";
	
	@Lob
	@Column(name="PGRAPH")
	private byte[] pgraphByteArray;
	
	@Transient
	private Serializable pgraph;

	@SuppressWarnings("unused")
	private WorkflowDefinition() {
		// Required by JPA
	}

	public WorkflowDefinition(String name, Pgraph pgraph) {
		if(name == null || name.isEmpty()
				|| pgraph == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Name (cannot be null or empty) : " + name + SystemProperties.NEW_LINE +
					"Pgraph (cannot be null) : " + pgraph + SystemProperties.NEW_LINE);
		}

		this.name = name;
		this.pgraph = pgraph;
	}
	
	

	public String getName() {
		return this.name;
	}

	public Pgraph getPgraph() {
		return (Pgraph)this.pgraph;
	}

	public void setPgraph(Pgraph pgraph) {
		if(pgraph != null) {
			this.pgraph = pgraph;
		}
	}

	@PrePersist
	private void convertPgraphToByteArray()
	{
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(out );
			stream.writeObject(this.pgraph);
			this.pgraphByteArray = out.toByteArray();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unable to store the pgraph.", e);
		}
	}
	
	@PostLoad
	private void convertByteArrayToPgraph() {
		try {
			ByteArrayInputStream out = new ByteArrayInputStream(this.pgraphByteArray);
			ObjectInputStream stream = new ObjectInputStream(out );
			this.pgraph = (Pgraph)stream.readObject();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unable to read the pgraph byte array from the database.", e);
		}
	}
}
