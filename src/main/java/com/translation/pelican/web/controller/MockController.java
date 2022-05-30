package com.translation.pelican.web.controller;

import com.translation.pelican.domain.constant.TranslationError;
import com.translation.pelican.domain.translation.CountryTranslationResponse;
import com.translation.pelican.exception.CountryExistsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mock/translation")
public class MockController {

    // In real application this class would not exist, it's just easy to mock against localhost.
    // Could remove duplication of methods implementation but its mock so what the hell...

    // These maps can be viewed as temporary mock databases
    private static final String HELLO = "Hello";
    protected Map<String, String> lithuanianTranslations = new HashMap<>(Map.of(HELLO, "Labas"));
    protected Map<String, String> estonianTranslations = new HashMap<>(Map.of(HELLO, "Tere"));
    protected Map<String, String> belgianTranslations = new HashMap<>(Map.of(HELLO, "Hallo"));

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

}
