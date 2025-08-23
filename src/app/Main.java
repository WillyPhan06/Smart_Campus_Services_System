package app;

import core.ModuleRegistry;
import core.AbstractModule;
import modules.printmanager.PrintJobManager;
import modules.digitallibrary.LibraryManager;
import modules.eventcalendar.EventCalendarManager;
import java.util.Scanner;  // ✅ add this import

public class Main {
    public static void main(String[] args) {
        // Create registry
        ModuleRegistry registry = new ModuleRegistry();

        // Register modules here
        registry.register(new PrintJobManager());
        registry.register(new LibraryManager());
        registry.register(new EventCalendarManager());

        // Create shared Scanner
        Scanner scanner = new Scanner(System.in);

        // Start the menu
        MenuHandler menu = new MenuHandler(registry, scanner); // ✅ pass scanner
        menu.start();
    }
}
