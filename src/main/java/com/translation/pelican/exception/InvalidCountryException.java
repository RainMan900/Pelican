package com.translation.pelican.exception;

import com.translation.pelican.domain.constant.TranslationError;

public class InvalidCountryException extends RuntimeException {

    public InvalidCountryException() {
        super(TranslationError.NOT_FOUND.getErrorText());
    }

}
