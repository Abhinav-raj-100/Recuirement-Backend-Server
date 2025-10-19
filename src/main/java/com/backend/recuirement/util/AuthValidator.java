package com.backend.recuirement.util;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AuthValidator {

    public static boolean isValidEmail(String email)
    {
        return email !=null && EmailValidator.getInstance().isValid(email);
    }

    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";

    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);

    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    public static String getPasswordValidationError(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty.";
        }
        if (password.length() < 8) {
            return "Password must be at least 8 characters long.";
        }

        if(!containsUppercase(password))
        {
            return "Password must contain at least one uppercase letter.";
        }

        if(!containsLowercase(password))
        {
            return "Password must contain at least one lowercase letter.";
        }

        if(!containsDigit(password))
        {
            return "Password must contain at least one digit.";
        }

        if(!containsSpecialCharacter(password))
        {
            return "Password must contain at least one special character.";
        }

        return null;
    }

    private static boolean containsUppercase(String password)
    {
        return password.chars().anyMatch(Character::isUpperCase);
    }

    private static boolean containsLowercase(String password)
    {
        return password.chars().anyMatch(Character::isLowerCase);
    }

    private static boolean containsDigit(String password)
    {
        return password.chars().anyMatch(Character::isDigit);
    }

    private static boolean containsSpecialCharacter(String password)
    {
        return password.chars().anyMatch(c-> !Character.isLetterOrDigit(c));
    }
}
