package com.translation.pelican.domain.constant;

public enum TranslationError {

    // enum not really required at this point, but if there were more its handy like this to have overview
    // since we have three sub-applications that are doing the actual translation, maybe this would be moved to common package to keep
    // errors unified across all applications

    UNSUPPORTED_COUNTRY("Sorry, our system does not support the language requested"),
    NOT_FOUND("Cannot find the translation requested");


    private final String errorText;

    TranslationError(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }
}
