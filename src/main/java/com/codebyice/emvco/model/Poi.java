package com.codebyice.emvco.model;

import java.util.Arrays;
import java.util.Optional;

public enum Poi implements DocumentingEnum {
    STATIC("11", "Static"),
    DYNAMIC("12", "Dynamic");

    private final String value;
    private final String description;

    Poi(String value, String description){
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static Optional<Poi> fromValue(String value){
        return Arrays.stream(Poi.values())
                .filter(a -> a.value.equals(value))
                .findFirst();
    }
}
