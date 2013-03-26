package com.sobek.common.result;

import java.util.Locale;

public class TranslationServiceBean implements TranslationServiceLocal, TranslationServiceRemote {

	@Override
	public Result translateResult(Result result, Locale locale) {
		result.translate(locale);
		return result;
	}

	@Override
	public Result retranslateResult(Result result, Locale locale) {
		result.retranslate(locale);
		return result;
	}

}
