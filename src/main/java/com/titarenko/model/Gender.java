package com.titarenko.model;

public enum Gender {
    M("male"),
    F("female"),
    OTHER("other");

    private String code;

    Gender(String code) {
        this.code = code;
    }

    public static Gender getGender (String code) {
        return "male".equals(code) ? Gender.M : "female".equals(code) ? Gender.F : Gender.OTHER;
    }

    public String getCode() {
        return code;
    }


}
