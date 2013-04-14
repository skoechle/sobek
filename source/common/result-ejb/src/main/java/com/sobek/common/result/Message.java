package com.sobek.common.result;

import static java.lang.System.out;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;

import com.sobek.common.result.entity.TranslationDAOLocal;

public abstract class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// Without the java escape character '\' this would be '\{\{[^\{\}]*\}\}'
	// which should match anything inclosed in {{}}.  The negation symbol '^'
	// is needed to make the match start at '{{' and stop at '}}'.  The
	// negation of '{' should not be needed, but it is included in case
	// someone does this {{ blah {{ blah }}.
	private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{[^\\{\\}]*\\}\\}");

	private MessageCode code = null;
	private String message = "";
	boolean translated = false;
	
	
	@EJB
	private TranslationDAOLocal translationDAO;
	
	protected Message(MessageCode code) {
		if(code == null) {
			throw new IllegalArgumentException(
					"An instance of " + Message.class + " cannot be created with " +
					"null values.  The given values were: " +
					"ErrorCode [" + code + "].");
		}
		
		this.code = code;
	}
	
	protected abstract String getSubstitutionValue(String key);

	public MessageCode getCode() {
		return this.code;
	}
	
	public boolean translated() {
		return this.translated;
	}
	
	// package private
	synchronized void resetTranslation() {
		this.message = "";
		this.translated = false;
	}
	
	// package private
	synchronized void translate(Locale locale) {
		if(!this.translated) {
			if(locale == null) {
				locale = Locale.ENGLISH;
			}
		
			String translation = this.translationDAO.getTranslation(code, locale);
			
			// If a translation could not be retrieved with the full locale, try
			// only using the language.
			if(translation == null || translation.isEmpty()) {
				translation = this.translationDAO.getTranslation(code, locale.getLanguage());
			}

			// If we didn't get a translation for the local, try English (if English
			// was not the attempted local).
			if(translation == null || translation.isEmpty()) {
				if(!locale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
					translation = this.translationDAO.getTranslation(code, Locale.ENGLISH.getLanguage());
				}
			}
			
			// If we still didn't get a translation, just set the message to an
			// empty string.  We don't want to blow up on translations, the error
			// code will be enough to fix the translation issue.
			if(translation == null || translation.isEmpty()) {
				this.message = "";
			} else {
				this.message = translation;
			}
			
			this.substitutePlaceholders();
			this.translated = true;
		}
	}

	private void substitutePlaceholders() {
		Matcher matcher = PLACEHOLDER_PATTERN.matcher(this.message);
		Set<String> valuesToReplace = new HashSet<String>();
		while(matcher.find()) {
			String finding = matcher.group();
			out.println("Found: " + finding);
			if(!valuesToReplace.contains(finding)) {
				valuesToReplace.add(finding);
			}
		}
		
		for(String key : valuesToReplace) {
			String value = this.getSubstitutionValue(key);
			this.message = this.message.replace(key, value);
		}
	}
}
