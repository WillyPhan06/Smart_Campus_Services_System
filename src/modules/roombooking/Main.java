package modules.roombooking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        listroom lr = new listroom();
        int choice = 0;

        do {
            displayMenu();
            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // Add a new room booking
                        lr.addRoom();
                        break;

                    case 2: // Check availability
                        checkAvailability(sc, lr);
                        break;

                    case 3: // Cancel booking
                        cancelBooking(sc, lr);
                        break;

                    case 4: // Display sorted bookings
                        System.out.println("Displaying bookings sorted by date and time:");
                        lr.displayBookingsSortedByDate();
                        break;

                    case 5:
                        System.out.println("Exiting the Room Booking System...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again with valid input.");
                sc.nextLine(); // Clear the scanner buffer
            }
        } while(choice != 5);

        //sc.close(); // Close scanner when done
    }

    // Display the main menu
    private static void displayMenu() {
        System.out.println("\n===== ROOM BOOKING SYSTEM =====");
        System.out.println("1. Add a new room booking with time and date");
        System.out.println("2. Check availability for a specific time slot");
        System.out.println("3. Cancel an existing booking");
        System.out.println("4. Display all bookings sorted by date and time");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    // Check availability for a time slot
    private static void checkAvailability(Scanner sc, listroom lr) {
        try {
            System.out.println("Enter date (yyyy-MM-dd): ");
            String dateStr = sc.nextLine();
            System.out.println("Enter start time (HH:mm): ");
            String startTimeStr = sc.nextLine();
            System.out.println("Enter end time (HH:mm): ");
            String endTimeStr = sc.nextLine();

            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime startTime = LocalTime.parse(startTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(endTimeStr, DateTimeFormatter.ofPattern("HH:mm"));

            boolean isAvailable = lr.checkAvailability(date, startTime, endTime);
            if (isAvailable) {
                System.out.println("The room is available for this time slot.");
            } else {
                System.out.println("The room is not available for this time slot.");
            }
        } catch (Exception e) {
            System.out.println("Error in date/time format. Please use the correct format.");
        }
    }

    // Cancel a booking
    private static void cancelBooking(Scanner sc, listroom lr) {
        try {
            System.out.println("Enter Room ID to cancel booking: ");
            int roomId = sc.nextInt();
            sc.nextLine(); // Consume newline

            boolean canceled = lr.cancelBooking(roomId);
            if (canceled) {
                System.out.println("Booking successfully canceled.");
            } else {
                System.out.println("Could not find a booking with that room ID.");
            }
        } catch (Exception e) {
            System.out.println("Invalid room ID. Please enter a number.");
        }
    }
}