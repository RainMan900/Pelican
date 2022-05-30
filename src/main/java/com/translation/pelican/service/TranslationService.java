package com.translation.pelican.service;

import com.translation.pelican.domain.constant.CountryCode;
import com.translation.pelican.domain.constant.TranslationCountry;
import com.translation.pelican.domain.constant.TranslationError;
import com.translation.pelican.domain.translation.CountryTranslationResponse;
import com.translation.pelican.domain.translation.TranslationData;
import com.translation.pelican.domain.translation.TranslationOutput;
import com.translation.pelican.exception.InvalidCountryException;
import com.translation.pelican.service.rest.mapper.TranslationOutputMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class TranslationService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${rest.estoniaApplicationUrl}")
    private String estoniaApplicationUrl;
    @Value("${rest.lithuaniaApplicationUrl}")
    private String lithuaniaApplicationUrl;
    @Value("${rest.belgiumApplicationUrl}")
    private String belgiumApplicationUrl;

    public TranslationOutput translate(String country, String word) {
        TranslationData data = getTranslation(country, word);
        return TranslationOutputMapper.map(data);
    }

    private TranslationData getTranslation(String country, String word) {
        TranslationData translationData = TranslationData.builder()
                .request(word)
                .errors(null)
                .result(null)
                .build();

        setCountryIfValid(country, translationData);

        if (translationData.hasError(TranslationError.UNSUPPORTED_COUNTRY)) {
            return translationData;
        } else {
            return getTranslationByCountry(translationData);
        }
    }

    private void setCountryIfValid(String country, TranslationData translationData) {
        TranslationCountry translationCountry;
        try {
            translationCountry = TranslationCountry.valueOf(country);
            translationData.setCountry(translationCountry);
        } catch (IllegalArgumentException e) {
            translationData.addError(TranslationError.UNSUPPORTED_COUNTRY);
        }
    }

    private TranslationData getTranslationByCountry(TranslationData translationData) {
        String uri = getTranslationUriByCountryCode(translationData.getCountry().getCountryCode()) + "/" + translationData.getRequest();
        CountryTranslationResponse result = restTemplate.getForObject(uri, CountryTranslationResponse.class);

        if (result != null) {
            translationData.setResult(result.getTranslation());
            if (result.getError() != null) {
                translationData.addError(result.getError());
            }
            handleCountrySpecifics(translationData);
        }

        return translationData;
    }

    private void handleCountrySpecifics(TranslationData translationData) {
        if (TranslationCountry.Estonia.equals(translationData.getCountry())) {
            translationData.setResult(translationData.getResult().toLowerCase(Locale.ROOT));
        } else {
            translationData.setResult(StringUtils.capitalize(translationData.getResult()));
        }
    }

    @SneakyThrows
    private String getTranslationUriByCountryCode(CountryCode country) {
        switch (country) {
            case EST:
                return estoniaApplicationUrl;
            case BEL:
                return belgiumApplicationUrl;
            case LIT:
                return lithuaniaApplicationUrl;
            default: throw new InvalidCountryException();
        }
    }

    public void addTranslation(String country, String key, String word) {
        CountryCode countryCode = TranslationCountry.valueOf(country).getCountryCode();
        String uri = getTranslationUriByCountryCode(countryCode) + "/" + key + "/" + word;
        // eventually should be put to post body with new java class instead url params
        restTemplate.postForObject(uri, null, Void.class);

    }


}
