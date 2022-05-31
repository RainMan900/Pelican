package com.translation.pelican.web.controller;

import com.translation.pelican.domain.translation.CountryTranslationResponse;
import com.translation.pelican.domain.translation.TranslationOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.translation.pelican.service.TranslationService;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryTranslationController {

    @Autowired
    private TranslationService translationService;

    @GetMapping("/{country}/{word}")
    public TranslationOutput getTranslationByCountry(@PathVariable String country, @PathVariable String word) {
        return translationService.translate(country, word);
    }

    // Better to use post body in real application but currently allows easy usage from cmd line
    @PostMapping("/{country}/{key}/{word}")
    public void addTranslation(@PathVariable String country, @PathVariable String key, @PathVariable String word) {
        translationService.addTranslation(country, key, word);
    }

    @GetMapping("/{language}")
    public List<CountryTranslationResponse> getAllByLanguage(@PathVariable String language) {
        return translationService.getAllByLanguage(language);
    }

}
