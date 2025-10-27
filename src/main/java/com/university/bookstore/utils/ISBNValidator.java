package com.university.bookstore.utils;

import java.util.regex.Pattern;

public class ISBNValidator {
    private static final Pattern ISBN_10_PATTERN = Pattern.compile("^\\d{9}[\\dX]$");
    private static final Pattern ISBN_13_PATTERN = Pattern.compile("^\\d{13}$");

    public static boolean isValidISBN(String isbn) {
        if (isbn == null) return false;

        // 1. Normalize
        String normalized = isbn.replaceAll("[-\\s]", "").toUpperCase();

        // 2. Check ISBN-10
        if (ISBN_10_PATTERN.matcher(normalized).matches()) {
            return isValidISBN10(normalized);
        }

        // 3. Check ISBN-13
        if (ISBN_13_PATTERN.matcher(normalized).matches()) {
            return isValidISBN13(normalized);
        }

        return false;
    }

    private static boolean isValidISBN10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }
        char check = isbn.charAt(9);
        sum += (check == 'X') ? 10 : (check - '0');
        return sum % 11 == 0;
    }

    private static boolean isValidISBN13(String isbn) {
        // must start with 978 or 979
        if (!isbn.startsWith("978") && !isbn.startsWith("979")) return false;

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int check = (10 - (sum % 10)) % 10;
        return check == (isbn.charAt(12) - '0');
    }
}
