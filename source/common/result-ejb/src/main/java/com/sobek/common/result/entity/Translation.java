package com.sobek.common.result.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TRANSLATION")
public class Translation implements Serializable{

	@Transient
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TranslationKey key;
	
	@Column(name="MESSAGE")
	private String message;

	public String getMessage() {
		return this.message;
	}
}
