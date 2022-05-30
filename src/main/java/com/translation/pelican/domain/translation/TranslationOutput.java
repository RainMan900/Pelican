package com.translation.pelican.domain.translation;


import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Setter
public class TranslationOutput {

    private String data;

    // Not required by the assignment but would be a nice way to show errors in the ui indicating errors to users
    @Nullable
    private List<String> errors;

}
