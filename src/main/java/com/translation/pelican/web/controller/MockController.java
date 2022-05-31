package com.translation.pelican.web.controller;

import com.translation.pelican.domain.constant.TranslationCountry;
import com.translation.pelican.domain.constant.TranslationError;
import com.translation.pelican.domain.translation.CountryTranslationResponse;
import com.translation.pelican.exception.CountryExistsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mock/translation")
public class MockController {

    // In real application this class would not exist, it's just easy to mock against localhost.
    // Could remove duplication of methods implementation but its mock so what the hell...

    // These maps can be viewed as temporary mock databases
    private static final String HELLO = "Hello";
    private static final String HOUSE = "House";
    private static final String RANDOM = "Random";
    protected Map<String, String> lithuanianTranslations = new HashMap<>(Map.of(
            HELLO, "Labas",
            RANDOM, "randpomInLith",
            HOUSE, "HouseInLithuanua"
    ));
    protected Map<String, String> estonianTranslations = new HashMap<>(Map.of(
            HELLO, "Tere",
            RANDOM, "Suvaline",
            HOUSE, "Maja"
    ));
    protected Map<String, String> belgianTranslations = new HashMap<>(Map.of(
            HELLO, "Hallo",
            RANDOM, "randomInBelgium",
            HOUSE, "HouseInBelgian"
    ));

    @GetMapping("/Lithuania/{word}")
    public CountryTranslationResponse mockLithuania(@PathVariable String word) {
        CountryTranslationResponse response = new CountryTranslationResponse();
        String translation = lithuanianTranslations.get(word);
        if (translation != null) {
            response.setTranslation(translation);
        } else {
            response.setError(TranslationError.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/Estonia/{word}")
    public CountryTranslationResponse mockEstonia(@PathVariable String word) {
        CountryTranslationResponse response = new CountryTranslationResponse();
        String translation = estonianTranslations.get(word);
        if (translation != null) {
            response.setTranslation(translation);
        } else {
            response.setError(TranslationError.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/Belgium/{word}")
    public CountryTranslationResponse mockBelgium(@PathVariable String word) {
        CountryTranslationResponse response = new CountryTranslationResponse();
        String translation = belgianTranslations.get(word);
        if (translation != null) {
            response.setTranslation(translation);
        } else {
            response.setError(TranslationError.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("/Belgium/{key}/{word}")
    public void addBelgianTranslation(@PathVariable String word, @PathVariable String key) {
        if (belgianTranslations.containsKey(word)) {
            throw new CountryExistsException();
        }
        this.belgianTranslations.put(key, word);
    }

    @PostMapping("/Estonia/{key}/{word}")
    public void addEstonianTranslation(@PathVariable String word, @PathVariable String key) {
        if (estonianTranslations.containsKey(word)) {
            throw new CountryExistsException();
        }
        this.estonianTranslations.put(key, word);
    }

    @PostMapping("/Lithuania/{key}/{word}")
    public void addLithuanianTranslation(@PathVariable String word, @PathVariable String key) {
        if (belgianTranslations.containsKey(word)) {
            throw new CountryExistsException();
        }
        this.lithuanianTranslations.put(key, word);
    }

    @GetMapping("/{language}")
    public List<CountryTranslationResponse> getAllEstonian(@PathVariable String language) {
        Map<String, String> translations = new HashMap<>();
        if (TranslationCountry.Estonia.name().equals(language)) {
            translations = estonianTranslations;
        } else if (TranslationCountry.Lithuania.name().equals(language)) {
            translations = lithuanianTranslations;
        } else if (TranslationCountry.Belgium.name().equals(language)) {
            translations = belgianTranslations;
        }
        return translations.entrySet().stream().map(stringStringEntry -> {
            CountryTranslationResponse translation = new CountryTranslationResponse();
            translation.setTranslation(stringStringEntry.getValue());
            translation.setKey(stringStringEntry.getKey());
            return translation;
        }).collect(Collectors.toList());
    }

}
