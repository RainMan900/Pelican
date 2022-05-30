package com.translation.pelican.domain.translation;

import com.translation.pelican.domain.constant.TranslationError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Response class sent by country specific applications: Estonia, Lithuania, Belgium etc
public class CountryTranslationResponse {

    private String translation;

    private TranslationError error;

}
