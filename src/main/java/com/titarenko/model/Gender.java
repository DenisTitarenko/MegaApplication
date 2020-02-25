package com.titarenko.model;

public enum Gender {
    M("M"),
    F("F"),
    OTHER("OTHER");

    private String code;

    Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
