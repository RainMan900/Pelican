package com.translation.pelican.exception;

public class CountryExistsException extends RuntimeException {

    public CountryExistsException() {
        super("This word already exists. If you wish to modify, use the modify api");
    }
}
