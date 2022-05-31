package com.translation.pelican.mapper;

import com.translation.pelican.domain.constant.TranslationError;
import com.translation.pelican.domain.translation.TranslationData;
import com.translation.pelican.domain.translation.TranslationOutput;
import com.translation.pelican.service.rest.mapper.TranslationOutputMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TranslationOutPutMapperTest {

    @Test
    void map_errors() {
        TranslationData translationDataErr = TranslationData.builder()
                .key(null)
                .errors(List.of(TranslationError.UNSUPPORTED_COUNTRY))
                .translation(null)
                .build();
        TranslationOutput output = TranslationOutputMapper.map(translationDataErr);
        assertNotNull(output.getErrors());
        assertEquals(TranslationError.UNSUPPORTED_COUNTRY.getErrorText(), output.getErrors().get(0));
    }

    @Test
    void map_success() {
        String result = "Hello";
        TranslationData translationDataErr = TranslationData.builder()
                .key(null)
                .errors(null)
                .translation(result)
                .build();
        TranslationOutput output = TranslationOutputMapper.map(translationDataErr);
        assertEquals(result, translationDataErr.getTranslation());
        assertNull(output.getErrors());
    }

}
