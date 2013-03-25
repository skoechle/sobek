package com.sobek.common.result.entity;

import java.util.Locale;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sobek.common.result.MessageCode;

@Stateless
public class TranslationDOABean implements TranslationDAOLocal {
	@PersistenceContext(name = "resultPU")
	private EntityManager entityManager;
	
	@Override
	public String getTranslation(MessageCode code, Locale locale) {
		String returnValue = "";
		TranslationKey key = new TranslationKey(locale, code);
		
		Translation translation = this.entityManager.find(Translation.class, key);
		if(translation != null) {
			returnValue = translation.getMessage();
		}
		return returnValue;
	}

	@Override
	public String getTranslation(MessageCode code, String language) {
		String returnValue = "";
		TranslationKey key = new TranslationKey(language, "", code);
		
		Translation translation = this.entityManager.find(Translation.class, key);
		if(translation != null) {
			returnValue = translation.getMessage();
		}
		return returnValue;
	}

}
