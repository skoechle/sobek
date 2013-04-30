package com.sobek.common.result;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.sobek.common.result.entity.TranslationDAOLocal;

public class TestMessageTranslate {
	@Test
	public void valildTranslation() throws Exception {
		String subVal1 = "{{value1}}";
		String subVal2 = "{{value 2}}";
		String replace1 = "1234";
		String replace2 = "sub text for 2";
		String start = "some, easy, text to start with.";
		String middle = "!We have some middle....Text??!//";
		String end = "just finish {it} up already";
		String stringToTranslate = subVal1 + start + middle + subVal2 + end + subVal2;
		String expectedTranslation = replace1 + start + middle + replace2 + end + replace2;
		
		MessageCode code = mock(MessageCode.class);
		PublicMessage message = new PublicMessage(code);
		message.addSubstitutionValue(subVal1, replace1);
		message.addSubstitutionValue(subVal2, replace2);
		
		TranslationDAOLocal dao = mock(TranslationDAOLocal.class);
		when(dao.getTranslation(code, Locale.FRENCH.getLanguage())).thenReturn(stringToTranslate);
		
		this.setDAO(dao, message);
		
		message.translate(Locale.FRENCH);
		
		String actual = message.getText();
		
		Assert.assertEquals(expectedTranslation, actual);
		Assert.assertTrue(
				"The message was not marked as translated after " +
				"translate had been called.",
				message.translated());
	}
	
	@Test
	public void fallBackToEnglish() throws Exception {
		String subVal1 = "{{value1}}";
		String subVal2 = "{{value 2}}";
		String replace1 = "1234";
		String replace2 = "sub text for 2";
		String start = "some, easy, text to start with.";
		String middle = "!We have some middle....Text??!//";
		String end = "just finish {it} up already";
		String stringToTranslate = subVal1 + start + middle + subVal2 + end + subVal2;
		String expectedTranslation = replace1 + start + middle + replace2 + end + replace2;
		
		MessageCode code = mock(MessageCode.class);
		PublicMessage message = new PublicMessage(code);
		message.addSubstitutionValue(subVal1, replace1);
		message.addSubstitutionValue(subVal2, replace2);
		
		TranslationDAOLocal dao = mock(TranslationDAOLocal.class);
		when(dao.getTranslation(code, Locale.FRENCH.getLanguage())).thenReturn(null);
		when(dao.getTranslation(code, Locale.ENGLISH.getLanguage())).thenReturn(stringToTranslate);
		
		this.setDAO(dao, message);
		
		message.translate(Locale.FRENCH);
		
		String actual = message.getText();
		
		Assert.assertEquals(expectedTranslation, actual);
		Assert.assertTrue(
				"The message was not marked as translated after " +
				"translate had been called.",
				message.translated());
	}
	
	@Test
	public void nullLocal() throws Exception {
		String subVal1 = "{{value1}}";
		String subVal2 = "{{value 2}}";
		String replace1 = "1234";
		String replace2 = "sub text for 2";
		String start = "some, easy, text to start with.";
		String middle = "!We have some middle....Text??!//";
		String end = "just finish {it} up already";
		String stringToTranslate = subVal1 + start + middle + subVal2 + end + subVal2;
		String expectedTranslation = replace1 + start + middle + replace2 + end + replace2;
		
		MessageCode code = mock(MessageCode.class);
		PublicMessage message = new PublicMessage(code);
		message.addSubstitutionValue(subVal1, replace1);
		message.addSubstitutionValue(subVal2, replace2);

		// when null is passed in, the code should default to ENGLISH.
		TranslationDAOLocal dao = mock(TranslationDAOLocal.class);
		when(dao.getTranslation(code, Locale.ENGLISH.getLanguage())).thenReturn(stringToTranslate);
		
		this.setDAO(dao, message);
		
		message.translate(null);
		
		String actual = message.getText();
		
		Assert.assertEquals(expectedTranslation, actual);
		Assert.assertTrue(
				"The message was not marked as translated after " +
				"translate had been called.",
				message.translated());
	}
	
	@Test
	public void noTranslationFound() throws Exception {
		String subVal1 = "{{value1}}";
		String subVal2 = "{{value 2}}";
		String replace1 = "1234";
		String replace2 = "sub text for 2";
		String expectedTranslation = "";
		
		MessageCode code = mock(MessageCode.class);
		PublicMessage message = new PublicMessage(code);
		message.addSubstitutionValue(subVal1, replace1);
		message.addSubstitutionValue(subVal2, replace2);

		TranslationDAOLocal dao = mock(TranslationDAOLocal.class);
		when(dao.getTranslation(code, Locale.ENGLISH.getLanguage())).thenReturn(null);
		
		this.setDAO(dao, message);
		
		message.translate(Locale.ENGLISH);
		
		String actual = message.getText();
		
		Assert.assertEquals(expectedTranslation, actual);
		Assert.assertTrue(
				"The message was not marked as translated after " +
				"translate had been called.",
				message.translated());
	}
	
	@Test
	public void placeHolderNotFound() throws Exception {
		String subVal1 = "{{value1}}";
		String subVal2 = "{{value 2}}";
		String replace1 = "1234";
		String start = "some, easy, text to start with.";
		String middle = "!We have some middle....Text??!//";
		String end = "just finish {it} up already";
		String stringToTranslate = subVal1 + start + middle + subVal2 + end + subVal2;
		String expectedTranslation = replace1 + start + middle + subVal2 + end + subVal2;
		
		MessageCode code = mock(MessageCode.class);
		PublicMessage message = new PublicMessage(code);
		message.addSubstitutionValue(subVal1, replace1);

		// when null is passed in, the code should default to ENGLISH.
		TranslationDAOLocal dao = mock(TranslationDAOLocal.class);
		when(dao.getTranslation(code, Locale.ENGLISH.getLanguage())).thenReturn(stringToTranslate);
		
		this.setDAO(dao, message);
		
		message.translate(null);
		
		String actual = message.getText();
		
		Assert.assertEquals(expectedTranslation, actual);
		Assert.assertTrue(
				"The message was not marked as translated after " +
				"translate had been called.",
				message.translated());
	}
	
	private void setDAO(TranslationDAOLocal dao, Message message) throws Exception {
		Field field = Message.class.getDeclaredField("translationDAO");
		
		field.setAccessible(true);
		
		field.set(message, dao);
	}
}
