package com.sobek.common.result.entity;

import java.util.Locale;

import javax.ejb.Local;

import com.sobek.common.result.MessageCode;

@Local
public interface TranslationDAOLocal {
	String getTranslation(MessageCode code, Locale locale);
	String getTranslation(MessageCode code, String language);
}
