package utils;

// Utility class for validating input values
public class Validator {
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isValidFileName(String fileName) {
        // Simple check: not empty and contains a dot (.)
        return !isEmpty(fileName) && fileName.contains(".");
    }
}
