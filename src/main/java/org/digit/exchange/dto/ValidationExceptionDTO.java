package org.digit.exchange.dto;

import java.util.List;

public class ValidationExceptionDTO extends RuntimeException {
    private final List<String> errors;

    public ValidationExceptionDTO(List<String> errors) {
        super("Validation failed.");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}

