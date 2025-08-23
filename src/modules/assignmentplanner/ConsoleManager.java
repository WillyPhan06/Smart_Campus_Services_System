// File: assignmentplanner/ConsoleManager.java
// Description: A helper class for handling console input for your module.

package modules.assignmentplanner;

import java.util.Scanner; // We must import the built-in Scanner class.

/**
 * ConsoleManager.java
 * This helper class is 'package-private' (no public keyword), meaning it's only
 * visible to other classes within the 'assignmentplanner' package. This is a good
 * encapsulation practice.
 */
class ConsoleManager {

    // A single, static scanner is efficient as it avoids creating new objects repeatedly.
    // 'final' makes it a constant, preventing accidental reassignment.
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int readInt(String prompt, int min, int max) {
        int choice = -1;
        while (true) {
            // A try-catch block handles potential errors, like a user entering text
            // instead of a number, preventing the program from crashing.
            try {
                System.out.print(prompt);
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Error: Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a whole number.");
            }
        }
    }

    public static int readMonth() {
        return readInt("Enter Month (1-12): ", 1, 12);
    }

    public static int readDay(int year, int month) {
        int maxDay = switch (month) {
            case 4, 6, 9, 11 -> 30;
            case 2 -> {
                boolean isLeap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                yield isLeap ? 29 : 28;
            }
            default -> 31;
            // A switch is a clean alternative to a long if-else chain for checking a
            // variable against multiple constant values.
        };
        return readInt("Enter Day: ", 1, maxDay);
    }
}
