package com.titarenko.model;

public enum Gender {
    M("male"),
    F("female"),
    OTHER("other");

    private String code;

    Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
