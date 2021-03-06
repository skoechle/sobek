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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sobek.common.util.SystemProperties;
import com.sobek.workflow.WorkflowState;

@Entity
@Table(name="WORKFLOW")
@NamedQueries({
		@NamedQuery(name = WorkflowEntity.GET_WORKFLOW_BY_NAME, query = "SELECT wf FROM WorkflowEntity wf WHERE wf.name = :" + WorkflowEntity.NAME_PARAMETER)
	})
public class WorkflowEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(WorkflowEntity.class.getPackage().getName());

	public static final String GET_WORKFLOW_BY_NAME = "WorkflowEntity.getWorkflowByName";
	public static final String NAME_PARAMETER = "name";

    @SequenceGenerator(name="workflowIdGenerator",
            sequenceName="WORKFLOW_ID_GENERATOR",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
         generator="workflowIdGenerator")
    @Id
	@Column(name="ID")
	private long id;
	
	@Column(name="NAME")
	private String name;
    
    @Column(name="PGRAPH_ID")
    private long pgraphId;
	
	@Column(name="STATUS")
	private String stateString;
	
	@Transient
	private WorkflowState state;
	
	@Column(name="RAW_MATERIAL")
	@Lob
	private byte[] rawMaterialByteArray;
	
	@Transient
	private Serializable rawMaterial;

	@SuppressWarnings("unused")
	private WorkflowEntity() {
	}
	
	public WorkflowEntity(String name, Serializable rawMaterial, long pgraphId) {
		if(name == null || name.isEmpty()
				|| rawMaterial == null
				|| pgraphId <= 0)
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Name (cannot be null or empty) : " + name + SystemProperties.NEW_LINE +
					"Raw Material (cannot be null) : " + rawMaterial + SystemProperties.NEW_LINE +
					"Pgraph ID (cannot be <= 0) : " + pgraphId + SystemProperties.NEW_LINE);
		}
		this.name = name;
		this.rawMaterial = rawMaterial;
		this.pgraphId = pgraphId;
		this.state = WorkflowState.NOT_STARTED;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public long getPgraphId() {
		return this.pgraphId;
	}

	public WorkflowState getStatus() {
		return this.state;
	}
	
	public void setStatus(WorkflowState status) {
		this.state = status;
	}

	public Serializable getRawMaterial() {
		return this.rawMaterial;
	}

	public void failed() {
		this.state = WorkflowState.FAILED;
	}
	
	@PrePersist
	private void prePersist() {
		this.stateString = state.name();
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(out );
			stream.writeObject(this.rawMaterial);
			this.rawMaterialByteArray = out.toByteArray();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unable to store the material.", e);
		}
	}
	
	@PostLoad
	private void postLoad() {
		this.state = WorkflowState.valueOf(this.stateString);
		try {
			ByteArrayInputStream out = new ByteArrayInputStream(this.rawMaterialByteArray);
			ObjectInputStream stream = new ObjectInputStream(out );
			this.rawMaterial = (Serializable)stream.readObject();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unable to read the material byte array from the database.", e);
		}
	}
}
