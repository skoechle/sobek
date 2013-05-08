package com.sobek.common.result.entity;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.sobek.common.result.MessageCode;

@Embeddable
public class TranslationKey implements Serializable{

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Column(name="LANGUAGE")
	private String language;
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="MESSAGE_CODE")
	private long messageCode;
	
	// Required by JPA
	@SuppressWarnings("unused")
	private TranslationKey() {}
	
	public TranslationKey(Locale locale, MessageCode messageCode) {
		if(locale == null || messageCode == null) {
			throw new IllegalArgumentException(
					"Cannot create an instace of " + TranslationKey.class +
					" with null values.  The given values were: " +
					"Locale [" + locale + "], " +
					"MessageCode [" + messageCode + "]. ");
		}
		
		this.language = locale.getLanguage();
		this.country = locale.getCountry();
		this.messageCode = messageCode.getValue();
	}
	
	public TranslationKey(String language, String country, MessageCode messageCode) {
		if(language == null || language.isEmpty()
				|| country == null || country.isEmpty()
				|| messageCode == null) {
			throw new IllegalArgumentException(
					"Cannot create an instace of " + TranslationKey.class +
					" with null or empty values.  The given values were: " +
					"Language [" + language + "], " +
					"Country [" + country + "], " +
					"MessageCode [" + messageCode + "]. ");
		}
		
		
		this.language = language;
		this.country = country;
		this.messageCode = messageCode.getValue();
	}
}
