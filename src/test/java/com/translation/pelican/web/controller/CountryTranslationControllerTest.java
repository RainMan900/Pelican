package com.translation.pelican.web.controller;

import com.translation.pelican.domain.constant.TranslationCountry;
import com.translation.pelican.domain.constant.TranslationError;
import com.translation.pelican.domain.translation.TranslationOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CountryTranslationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final List<String> supportedCountries = TranslationCountry.supportedCountries.stream()
        .map(TranslationCountry::name)
        .collect(Collectors.toList());


    @Test
    void translateHelloInLithuanian() {
        supportedCountries.forEach(country -> {
            ResponseEntity<TranslationOutput> response = restTemplate.getForEntity("/country/" + country + "/Hello", TranslationOutput.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().getData());
            assertHelloByCountry(country, response.getBody().getData());
            assertNull(response.getBody().getErrors());
        });
    }

    // Maybe not good to check that specifically here, since it can change, but it was one of business requirements, so I will leave it here
    private void assertHelloByCountry(String country, String result) {
        if (TranslationCountry.Estonia.name().equals(country)) {
            assertEquals("tere", result);
        } else if (TranslationCountry.Lithuania.name().equals(country)) {
            assertEquals("Labas", result);
        } else if (TranslationCountry.Belgium.name().equals(country)) {
            assertEquals("Hallo", result);
        }
    }

    @Test
    void addTranslation() {
        ResponseEntity<Void> response = restTemplate.postForEntity("/country/Estonia/House/Maja", null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void throwErrorWhenInvalidCountry() {
        ResponseEntity<TranslationOutput> response = restTemplate.getForEntity("/country/UnknownCountry/Hello", TranslationOutput.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNull(response.getBody().getData());
        assertNotNull(response.getBody().getErrors());
        assertNotNull(response.getBody().getErrors().get(0));
        assertEquals(TranslationError.UNSUPPORTED_COUNTRY.getErrorText(), response.getBody().getErrors().get(0));
    }

}
