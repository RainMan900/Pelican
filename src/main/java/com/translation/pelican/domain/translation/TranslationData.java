package com.translation.pelican.domain.translation;

import com.translation.pelican.domain.constant.TranslationCountry;
import com.translation.pelican.domain.constant.TranslationError;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TranslationData {

    // The object used to query and pass data inside our application, the result is finally mapped to TranslationOutput class that is sent to the user

    private String key;

    private String translation;

    private TranslationCountry country;

    private List<TranslationError> errors;

    public void addError(TranslationError error) {
        if (this.errors == null) {
            this.setErrors(List.of(error));
        } else {
            this.errors.add(error);
        }
    }

    public boolean hasAnyError() {
        return this.getErrors() != null && this.getErrors().size() > 0;
    }

    public boolean hasError(TranslationError error) {
        return this.getErrors() != null && this.getErrors().contains(error);
    }

}
