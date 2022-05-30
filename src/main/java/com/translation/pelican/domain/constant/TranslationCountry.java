package com.translation.pelican.domain.constant;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum TranslationCountry {

//    Maybe a better way would be to keep countries in a database and use as separate class and cache them,
//    that would allow more flexibility to change country data, although in our case we have only three countries and maybe overkill
    Estonia(CountryCode.EST),
    Lithuania(CountryCode.LIT),
    Belgium(CountryCode.BEL);

    private final CountryCode countryCode;

    TranslationCountry(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public static final List<TranslationCountry> supportedCountries = new ArrayList<>(EnumSet.allOf(TranslationCountry.class));
}
