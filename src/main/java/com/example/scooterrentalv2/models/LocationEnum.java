package com.example.scooterrentalv2.models;

public enum LocationEnum {
    ASTRA("astra"),
    BARTOLOMEU("bartolomeu"),
    CENTRU("centru"),
    CRAITER("craiter"),
    DARSTE("darste"),
    FLORILOR("florilor"),
    NOUA("noua"),
    POIANA("poiana"),
    SCRIITORILOR("scriitorilor"),
    STUPINI("stupini"),
    SCHEI("schei"),
    TRIAJ("triaj"),
    TRACTORUL("tractorul"),
    VALEA("valea"),
    CHARGING("charging"),
    DEPOSIT("deposit");


    private final String val;

    LocationEnum(String val){
        this.val = val;
    }
    public String getVal() {
        return val;
    }
}
