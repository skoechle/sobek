package com.sobek.pgraph.entity;

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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sobek.common.util.SystemProperties;

@Entity
@Table(name="MATERIAL_VALUE")
public class MaterialValueEntity {
	
	private static final Logger logger = Logger.getLogger(MaterialValueEntity.class.getPackage().getName());

    @SequenceGenerator(name="MaterialValueIdGenerator",
            sequenceName="MATERIAL_VALUE_ID_GENERATOR",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
         generator="MaterialValueIdGenerator")
	@Id
	@Column(name="ID")
	private long id;
    
    @OneToOne
    @JoinColumn(name="MATERIAL_ID")
    private MaterialEntity material;
	
	@Column(name="MATERIAL_VALUE")
	@Lob
	private byte[] valueByteArray;
	
	@Transient
	private Serializable value;

	// Required by JPA
	private MaterialValueEntity() {
	}

	public MaterialValueEntity(MaterialEntity material, Serializable value) {
		this();
		if(material == null || value == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Material (cannot be null) : " + material + SystemProperties.NEW_LINE +
					"Value (cannot be null) : " + value + SystemProperties.NEW_LINE);
		}
		
		material.setValue(this);
		this.value = value;
		this.material = material;
	}
	
	public long getId() {
		return this.id;
	}
	
	public MaterialEntity getMaterial() {
		return this.material;
	}
	
	public Serializable getValue() {
		return this.value;
	}

	@PrePersist
	private void convertPgraphToByteArray()
	{
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(out );
			stream.writeObject(this.value);
			this.valueByteArray = out.toByteArray();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unable to store the pgraph.", e);
		}
	}
	
	@PostLoad
	private void convertByteArrayToPgraph() {
		try {
			ByteArrayInputStream out = new ByteArrayInputStream(this.valueByteArray);
			ObjectInputStream stream = new ObjectInputStream(out );
			this.value = (Serializable)stream.readObject();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unable to read the pgraph byte array from the database.", e);
		}
	}
}
