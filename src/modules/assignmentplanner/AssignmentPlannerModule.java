package modules.assignmentplanner;

import core.AbstractModule;
import java.util.Scanner;

public class AssignmentPlannerModule extends AbstractModule {
    @Override
    public String getModuleName() {
        return "Assignment Planner";
    }

    @Override
    public void start(Scanner scanner) {
        // Fastest integration path:
        // If AssignmentPlanner has a `public static void main(String[] args)`:
        try {
            AssignmentPlanner.main(new String[0]);
            return;
        } catch (Throwable ignored) {
            // fall through to ConsoleManager path if no static main or different signature
        }

        // If there is a ConsoleManager class that drives the UI, prefer calling it:
        try {
            ConsoleManager ui = new ConsoleManager();

            // If ConsoleManager already has a no-arg start/run method, call it here:
            // ui.start(); // or ui.run();

            // If not, and you add an overload that accepts Scanner, use this instead:
            // ui.start(scanner);

            // TODO: Uncomment the correct line above based on ConsoleManager's API.
            System.out.println("[Assignment Planner] TODO: Wire ConsoleManager.start(...) call.");
        } catch (Throwable t) {
            System.out.println("[Assignment Planner] Could not launch module: " + t.getMessage());
        }
    }
}
