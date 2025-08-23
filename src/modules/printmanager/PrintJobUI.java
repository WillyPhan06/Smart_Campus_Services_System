package modules.printmanager;

import java.util.Scanner;

// Console UI for interacting with print jobs
public class PrintJobUI {
    private PrintQueue queue;
    private Scanner scanner;

    public PrintJobUI(PrintQueue queue, Scanner scanner) {
        this.queue = queue;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("=== Print Job Manager ===");
            System.out.println("1. Submit a new print job");
            System.out.println("2. Serve next print job");
            System.out.println("3. Display all jobs");
            System.out.println("4. Search jobs");
            System.out.println("5. Cancel jobs");
            System.out.println("6. Display job history");
            System.out.println("7. Show statistics");
            System.out.println("8. Back to main menu");

            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    submitJob();
                    break;
                case 2:
                    serveJob();
                    break;
                case 3:
                    queue.displayQueue();
                    break;
                case 4:
                    searchMenu();
                    break;
                case 5:
                    cancelMenu();
                    break;
                case 6:
                    queue.displayHistory();
                    break;
                case 7:
                    queue.displayStatistics();
                    break;
                case 8:
                    return; // back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }



        }
    }

    private void submitJob() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter file name: ");
        String filename = scanner.nextLine().trim();

        System.out.print("Enter number of copies: ");
        int copies;
        try {
            copies = Integer.parseInt(scanner.nextLine().trim());
            if (copies <= 0) {
                System.out.println("Invalid number of copies. Defaulting to 1.");
                copies = 1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Defaulting to 1 copy.");
            copies = 1;
        }

        System.out.print("Enter role (staff/teacher/student/other): ");
        String role = scanner.nextLine().trim();
        if (role.isEmpty()) role = "other";

        PrintJob job = new PrintJob(username, filename, copies, role);
        queue.enqueue(job);
        System.out.println("Job submitted: " + job);
    }



    private void serveJob() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty. No job to serve.");
            return;
        }

        System.out.print("Enter number of copies to serve: ");
        int copiesToServe;
        try {
            copiesToServe = Integer.parseInt(scanner.nextLine().trim());
            if (copiesToServe <= 0) {
                System.out.println("Invalid number. Please enter a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        queue.dequeueMultiple(copiesToServe);
    }


    private void searchJob() {
        System.out.print("Enter username to search: ");
        String user = scanner.nextLine();
        boolean found = false;

        for (int i = 0; i < queue.getSize(); i++) {
            int index = (queue.getFront() + i) % queue.getCapacity();
            PrintJob job = queue.getJobAt(index);
            if (job.getUsername().toLowerCase().contains(user.toLowerCase())) {
                System.out.println("Found job: " + job);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No jobs found for user: " + user);
        }
    }

    private void searchMenu() {
        while (true) {
            System.out.println("=== Search Menu ===");
            System.out.println("1. Search by username");
            System.out.println("2. Search by filename");
            System.out.println("3. Search by username + filename");
            System.out.println("4. Back");

            System.out.print("Choose an option: ");
            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter username to search: ");
                    String userSearch = scanner.nextLine().trim();
                    queue.searchByUsername(userSearch);
                    break;
                case 2:
                    System.out.print("Enter filename to search: ");
                    String fileSearch = scanner.nextLine().trim();
                    queue.searchByFilename(fileSearch);
                    break;
                case 3:
                    System.out.print("Enter username: ");
                    String user = scanner.nextLine().trim();
                    System.out.print("Enter filename: ");
                    String file = scanner.nextLine().trim();
                    queue.searchByUserAndFile(user, file);
                    break;
                case 4:
                    return; // back
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void cancelMenu() {
        while (true) {
            System.out.println("=== Cancel Menu ===");
            System.out.println("1. Cancel by username");
            System.out.println("2. Cancel by filename");
            System.out.println("3. Cancel by username + filename");
            System.out.println("4. Back");

            System.out.print("Choose an option: ");
            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter username to cancel: ");
                    String userCancel = scanner.nextLine().trim();
                    queue.cancelByUsername(userCancel);
                    break;
                case 2:
                    System.out.print("Enter filename to cancel: ");
                    String fileCancel = scanner.nextLine().trim();
                    queue.cancelByFilename(fileCancel);
                    break;
                case 3:
                    System.out.print("Enter username: ");
                    String userSpec = scanner.nextLine().trim();
                    System.out.print("Enter filename: ");
                    String fileSpec = scanner.nextLine().trim();
                    queue.cancelByUserAndFile(userSpec, fileSpec);
                    break;
                case 4:
                    return; // back
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

}
