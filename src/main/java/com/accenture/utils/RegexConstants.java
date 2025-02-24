package com.accenture.utils;

public class RegexConstants {
    // Validation d'un email
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    // Validation d'un mot de passe
    public static final String MOTDEPASSE_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@\\-_|§]).{8,16}$";

}
