package com.sobek.workflow.engine.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sobek.workflow.engine.WorkflowStatus;

//import javax.annotation.Generated;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

@Entity
@Table(name="WORKFLOW")
public class WorkflowData implements Serializable {
	private static final long serialVersionUID = 1L;

    @SequenceGenerator(name="idGenerator",
            sequenceName="WORKFLOW_ID_GENERATOR",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
         generator="idGenerator")
    @Id
	@Column(name="ID")
	private long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="STATUS")
	private String statusString;
	
	@Transient
	private WorkflowStatus status;
	
	@Column(name="PARAMETERS")
	@Lob
//	private String serializedParameters = "";
//	
//	@Transient
	private Serializable parameters;

	@SuppressWarnings("unused")
	private WorkflowData() {
	}
	
	public WorkflowData(String name, Serializable parameters) {
		this.name = name;
		this.parameters = parameters;
		this.status = WorkflowStatus.CREATED;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public WorkflowStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(WorkflowStatus status) {
		this.status = status;
	}

	public Serializable getParameters() {
		return this.parameters;
	}

	public void failed() {
		this.status = WorkflowStatus.FAILED;
	}
	
	@PrePersist
	private void prePersist() {
		this.statusString = status.name();

//		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//		try {
//			ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
//			objectStream.writeObject(this.parameters);
//			objectStream.flush();
//			objectStream.close();
//			byteStream.close();
//			this.serializedParameters = byteStream.toString();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@PostLoad
	private void postLoad() {
		this.status = WorkflowStatus.valueOf(this.statusString);

//		ByteArrayInputStream byteStream = new ByteArrayInputStream(this.serializedParameters.getBytes());
//		try {
//			ObjectInputStream objectStream = new ObjectInputStream(byteStream);
//			this.parameters = (Serializable) objectStream.readObject();
//			this.serializedParameters = "";
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
