package app;

import core.ModuleRegistry;
import core.AbstractModule;
import java.util.Scanner;

public class MenuHandler {
    private ModuleRegistry registry;
    private Scanner scanner;

    public MenuHandler(ModuleRegistry registry, Scanner scanner) {
        this.registry = registry;
        this.scanner = scanner;
    }


    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=== Smart Campus Services ===");
            int index = 1;
            for (AbstractModule module : registry.getModules()) {
                System.out.println(index + ". " + module.getModuleName());
                index++;
            }
            System.out.println(index + ". Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == index) {
                System.out.println("Exiting...");
                break;
            } else if (choice > 0 && choice < index) {
                registry.getModules().get(choice - 1).start(scanner);
            } else {
                System.out.println("Invalid choice, try again.");
            }
        }
    }
}
