package com.titarenko.model;

public enum Gender {
    M, F, OTHER;

    Gender() {

    }

    public static Gender getGender(String code) {
        return ("M".equals(code)) ? Gender.M
                : "F".equals(code) ? Gender.F
                : Gender.OTHER;
    }

}
