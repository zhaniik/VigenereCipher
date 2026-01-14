package com.mycompany.vigenerecipher.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class for managing HTTP cookies in the Vigenère Cipher application.
 * Provides methods for reading, writing, and managing cookies with proper error handling.
 * 
 * @author Zhanibek
 * @version 5.0
 */
public class CookieManager {
    
    /** Cookie name for tracking total visits */
    public static final String VISIT_COUNT_COOKIE = "vigenere_visits";
    
    /** Cookie name for tracking total operations */
    public static final String OPERATION_COUNT_COOKIE = "vigenere_operations";
    
    /** Cookie name for tracking error count */
    public static final String ERROR_COUNT_COOKIE = "vigenere_errors";
    
    /** Cookie name for storing the last used key */
    public static final String LAST_KEY_COOKIE = "vigenere_last_key";
    
    /** Maximum age for cookies in seconds (30 days) */
    private static final int MAX_AGE = 30 * 24 * 60 * 60;
    
    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with only static methods.
     */
    private CookieManager() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Reads an integer value from a cookie.
     * Returns 0 if the cookie is not found or has an invalid value.
     * 
     * @param request the HTTP request containing cookies
     * @param cookieName the name of the cookie to read
     * @return the integer value of the cookie, or 0 if not found or invalid
     */
    public static int readIntCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    try {
                        return Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
    
    /**
     * Reads a string value from a cookie.
     * Returns an empty string if the cookie is not found.
     * 
     * @param request the HTTP request containing cookies
     * @param cookieName the name of the cookie to read
     * @return the string value of the cookie, or empty string if not found
     */
    public static String readStringCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
    
    /**
     * Writes an integer value to a cookie.
     * 
     * @param response the HTTP response to add the cookie to
     * @param cookieName the name of the cookie
     * @param value the integer value to write
     */
    public static void writeIntCookie(HttpServletResponse response, String cookieName, int value) {
        Cookie cookie = new Cookie(cookieName, String.valueOf(value));
        cookie.setMaxAge(MAX_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    /**
     * Writes a string value to a cookie.
     * Replaces spaces and commas with safe characters to ensure browser compatibility.
     * 
     * @param response the HTTP response to add the cookie to
     * @param cookieName the name of the cookie
     * @param value the string value to write
     */
    public static void writeStringCookie(HttpServletResponse response, String cookieName, String value) {
        // Replace problematic characters to ensure browser compatibility
        String safeValue = value.replace(' ', '_').replace(',', '.');
        Cookie cookie = new Cookie(cookieName, safeValue);
        cookie.setMaxAge(MAX_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    /**
     * Increments a counter cookie by 1.
     * Reads the current value, increments it, and writes it back.
     * 
     * @param request the HTTP request containing cookies
     * @param response the HTTP response to write the updated cookie to
     * @param cookieName the name of the cookie to increment
     * @return the new value after incrementing
     */
    public static int incrementCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        int currentValue = readIntCookie(request, cookieName);
        int newValue = currentValue + 1;
        writeIntCookie(response, cookieName, newValue);
        return newValue;
    }
    
    /**
     * Gets formatted statistics from all cookies.
     * Returns a string with visit count, operation count, error count, and last key.
     * 
     * @param request the HTTP request containing cookies
     * @return a formatted string with cookie statistics
     */
    public static String getCookieStatistics(HttpServletRequest request) {
        int visitCount = readIntCookie(request, VISIT_COUNT_COOKIE);
        int operationCount = readIntCookie(request, OPERATION_COUNT_COOKIE);
        int errorCount = readIntCookie(request, ERROR_COUNT_COOKIE);
        String lastKey = readStringCookie(request, LAST_KEY_COOKIE);
        
        StringBuilder stats = new StringBuilder();
        stats.append("Visits: ").append(visitCount);
        stats.append(" | Operations: ").append(operationCount);
        stats.append(" | Errors: ").append(errorCount);
        stats.append(" | Last Key: ").append(lastKey.isEmpty() ? "None" : lastKey.replace('_', ' ').replace('.', ','));
        
        return stats.toString();
    }
    
    /**
     * Checks if a cookie with the given name exists.
     * 
     * @param request the HTTP request containing cookies
     * @param cookieName the name of the cookie to check
     * @return true if the cookie exists, false otherwise
     */
    public static boolean cookieExists(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Deletes a cookie by setting its max age to 0.
     * 
     * @param response the HTTP response to add the deletion cookie to
     * @param cookieName the name of the cookie to delete
     */
    public static void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0); // Delete cookie
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}