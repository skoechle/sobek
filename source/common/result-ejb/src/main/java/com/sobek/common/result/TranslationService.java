package com.sobek.common.result;

import java.util.Locale;

public interface TranslationService {
	public Result translateResult(Result result, Locale locale);
	public Result retranslateResult(Result result, Locale locale);
}
