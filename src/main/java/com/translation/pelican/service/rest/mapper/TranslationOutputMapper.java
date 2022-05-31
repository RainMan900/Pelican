package com.translation.pelican.service.rest.mapper;

import com.translation.pelican.domain.constant.TranslationError;
import com.translation.pelican.domain.translation.TranslationData;
import com.translation.pelican.domain.translation.TranslationOutput;

import java.util.List;
import java.util.stream.Collectors;

public class TranslationOutputMapper {

    private TranslationOutputMapper() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    public static TranslationOutput map(TranslationData data) {
        TranslationOutput translationOutput = new TranslationOutput();
        translationOutput.setData(data.getTranslation());

        if (data.hasAnyError()) {
            List<String> errors = data.getErrors().stream()
                    .map(TranslationError::getErrorText)
                    .collect(Collectors.toList());

            translationOutput.setErrors(errors);
        }
        return translationOutput;
    }

}
