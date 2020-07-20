package com.codebyice.emvco.model;

import java.util.Arrays;
import java.util.Optional;

public enum  TipIndicator implements DocumentingEnum{

    _01("01", "Prompt Consumer"),
    _02("02", "Fixed Fee"),
    _03("03", "Percentage Fee");

    private final String description;
    private final String code;

    TipIndicator(String code, String description){
        this.code = code;
        this.description = description;
    }


    public static Optional<TipIndicator> fromValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.code.equalsIgnoreCase(value))
                .findFirst();
    }
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getValue() {
        return code;
    }
}
