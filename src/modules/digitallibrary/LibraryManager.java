package modules.digitallibrary;

import core.AbstractModule;
import java.util.Scanner;

public class LibraryManager extends AbstractModule {
    private BookList library = new BookList();

    /*public LibraryManager() {
        // Add default 10 books
        String[][] defaultBooks = {
                {"Clean Code", "Robert C. Martin"},
                {"The Pragmatic Programmer", "Andrew Hunt"},
                {"Design Patterns", "Erich Gamma"},
                {"Refactoring", "Martin Fowler"},
                {"Effective Java", "Joshua Bloch"},
                {"Introduction to Algorithms", "Thomas H. Cormen"},
                {"Code Complete", "Steve McConnell"},
                {"The Mythical Man-Month", "Frederick P. Brooks Jr."},
                {"Head First Design Patterns", "Eric Freeman"},
                {"Patterns of Enterprise Application Architecture", "Martin Fowler"}
        };
        for (String[] info : defaultBooks) {
            library.addBook(info[0], info[1], 5);
        }
    }*/

    @Override
    public String getModuleName() {
        return "Digital Library System";
    }

    @Override
    public void start(Scanner scanner) {
        while (true) {
            System.out.println("\n==== DIGITAL LIBRARY SYSTEM ====");
            System.out.println("1) Add new book (append at end)");
            System.out.println("2) Add new book (insert sorted by title)");
            System.out.println("3) Show all books");
            System.out.println("4) Search by title (partial)");
            System.out.println("5) Search by author (partial)");
            System.out.println("6) Borrow a book");
            System.out.println("7) Return a book");
            System.out.println("8) Sort + Binary Search by exact title");
            System.out.println("9) Top N most borrowed books");
            System.out.println("10) Show operation logs");
            System.out.println("11) Show all books (sorted by title)");
            System.out.println("12) Delete book (by id )");
            System.out.println("13) Update book (by id )");
            System.out.println("0) Back to main menu");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter book title: ");
                    String t1 = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String a1 = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int q1 = scanner.nextInt();
                    library.addBook(t1, a1, q1);
                    break;
                case "2":
                    System.out.print("Enter book title: ");
                    String t2 = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String a2 = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int q2 = scanner.nextInt();
                    library.addBookSorted(t2, a2, q2);

                    break;
                case "3":
                    library.showAllBooks();
                    break;
                case "4":
                    System.out.print("Enter title keyword: ");
                    library.searchByTitle(scanner.nextLine());
                    break;
                case "5":
                    System.out.print("Enter author keyword: ");
                    library.searchByAuthor(scanner.nextLine());
                    break;
                case "6":
                    System.out.print("Enter book ID to borrow: ");
                    try {
                        int idB = Integer.parseInt(scanner.nextLine());
                        library.borrowBook(idB);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID.");
                    }
                    break;
                case "7":
                    System.out.print("Enter book ID to return: ");
                    try {
                        int idR = Integer.parseInt(scanner.nextLine());
                        library.returnBook(idR);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID.");
                    }
                    break;
                case "8":
                    System.out.print("Enter exact title to search: ");
                    String key = scanner.nextLine();
                    library.sortAndBinarySearch(key); // BookList lo hết việc
                    break;

                case "9":
                    System.out.print("Enter N: ");
                    try {
                        int N = Integer.parseInt(scanner.nextLine());
                        Book[] all = library.toArray();
                        MaxHeap heap = new MaxHeap(all.length);
                        for (Book b : all) heap.add(b);
                        for (int i = 0; i < N; i++) {
                            Book top = heap.poll();
                            if (top != null) System.out.println(top);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number.");
                    }
                    break;
                case "10":
                    library.getLogs().printLogs();
                    break;
                case "0":
                    return; // ✅ Back to main menu
                case "11":
                    Book[] sortedArr = library.toArray();
                    AlgoUtils.bubbleSortByTitle(sortedArr);

                    System.out.println("Books sorted by title:");
                    for (Book b : sortedArr) {
                        System.out.println(b.id+ "."+ " " + b.title + " by " + b.author
                                + " | Qty: " + b.quantity
                                + " | Borrowed: " + b.borrowCount
                                + " | Status: " + b.status);
                    }
                    break;
                case "12":
                    System.out.print("Enter ID of book to delete: ");
                    int delId = Integer.parseInt(scanner.nextLine());
                    library.deleteBook(delId);
                    break;
                case "13":
                    System.out.print("Enter ID of book to update: ");
                    int updId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter new title (press Enter to skip): ");
                    String newT = scanner.nextLine();

                    System.out.print("Enter new author (press Enter to skip): ");
                    String newA = scanner.nextLine();

                    System.out.print("Enter new quantity (press Enter to skip): ");
                    String qStr = scanner.nextLine();
                    Integer newQ = qStr.isEmpty() ? null : Integer.parseInt(qStr);

                    library.updateBook(updId, newT, newA, newQ);
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
