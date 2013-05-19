package com.sobek.workflow;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sobek.pgraph.Pgraph;

@XmlType(namespace = "com.sobek.workflowConfiguration")
@XmlRootElement(namespace = "com.sobek.workflowConfiguration")
public class WorkflowConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

    @XmlElement(required = true)
	private String name = "";
    @XmlElement(required = true)
	private Pgraph pgraph = null;

    
    @SuppressWarnings("unused")
	private WorkflowConfiguration() {
	}


	public WorkflowConfiguration(String name, Pgraph pgraph) {
		this.name = name;
		this.pgraph = pgraph;
	}


	public String getName() {
		return name;
	}


	public Pgraph getPgraph() {
		return pgraph;
	}
}
