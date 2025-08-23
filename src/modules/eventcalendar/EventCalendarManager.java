package modules.eventcalendar;

import core.AbstractModule;
import java.util.Scanner;

public class EventCalendarManager extends AbstractModule {
    private EventCalendarImpl service = new EventCalendarImpl();

    @Override
    public String getModuleName() {
        return "Campus Event Calendar";
    }

    @Override
    public void start(Scanner scanner) {
        boolean ordering = true;

        while (ordering) {
            System.out.println("\n**************** Welcome To Campus Event Calendar System ****************");
            System.out.println("0. Back to Main Menu");
            System.out.println("1. Add New Event");
            System.out.println("2. Display All Events");
            System.out.println("3. Sort Events by Date");
            System.out.println("4. Search Events by Name");
            System.out.println("5. Search Events by Type");
            System.out.println("6. Delete Event by ID");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 0:
                    ordering = false; // ✅ go back to main menu
                    break;
                case 1:
                    System.out.println("Add New Event");
                    service.addNewEvent();
                    break;
                case 2:
                    System.out.println("Display All Events");
                    service.displayAllEvents();
                    break;
                case 3:
                    System.out.println("Sort Events by Date");
                    service.sortEventsByDate();
                    break;
                case 4:
                    System.out.println("Search Events by Name");
                    service.searchEventsByName();
                    break;
                case 5:
                    System.out.println("Search Events by Type");
                    service.searchEventsByType();
                    break;
                case 6:
                    System.out.println("Delete Event by ID");
                    service.deleteEventById();
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
            }
        }
    }
}
