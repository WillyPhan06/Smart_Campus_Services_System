package app;

import core.ModuleRegistry;
import modules.printmanager.PrintJobManager;
import modules.digitallibrary.LibraryManager;
import modules.eventcalendar.EventCalendarManager;
import modules.assignmentplanner.AssignmentPlannerModule;
import modules.roombooking.RoomBookingModule;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create registry with capacity for 10 modules
        ModuleRegistry registry = new ModuleRegistry(10);

        // Register modules here
        registry.register(new PrintJobManager());
        registry.register(new LibraryManager());
        registry.register(new EventCalendarManager());
        registry.register(new AssignmentPlannerModule());
        registry.register(new RoomBookingModule());

        // Create shared Scanner
        Scanner scanner = new Scanner(System.in);

        // Start the menu
        MenuHandler menu = new MenuHandler(registry, scanner);
        menu.start();
    }
}
