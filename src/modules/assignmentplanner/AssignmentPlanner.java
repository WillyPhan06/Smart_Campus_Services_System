// File: assignmentplanner/AssignmentPlanner.java
// Description: The main application class for your module.

package modules.assignmentplanner;

/**
 * AssignmentPlanner.java
 * This class must be 'public' because it implements the public IModule interface.
 * This is the main "entry point" class for our module that the main application will create.
 */
public class AssignmentPlanner implements IModule {
    // The primary data structure is a CustomArrayList. This design choice
    // prioritizes fast O(1) random access for get/edit operations, which are
    // common in this application. The potential O(n) cost of resizing on add
    // is considered an acceptable trade-off.
    private CustomList<Assignment> assignments = new CustomArrayList<>();

    // The BST for efficient date-range queries.
    private AssignmentBST assignmentsBinaryTree = new AssignmentBST();

    // A separate list to store the sorted assignments. This acts as a cache.
    private CustomList<Assignment> sortedAssignments = null;

    // A flag to track if the main assignments list has been modified.
    // This helps in avoiding unnecessary re-sorting.
    private boolean isModified = true;

    @Override
    public void run() {
        System.out.println("\n--- Welcome to the Assignment Planner ---");
        while (true) {
            printMenu();
            int choice = ConsoleManager.readInt("Enter your choice: ", 0, 7);
            if (choice == 0) {
                System.out.println("Returning to the main menu!");
                break;
            }
            switch (choice) {
                case 1: addAssignment(); break;
                case 2: viewAllAssignments(); break;
                case 3: viewSortedAssignmentsByDueDate(); break;
                case 4: searchAssignmentByTitle(); break;
                case 5: searchAssignmentByDateRange(); break;
                case 6: editAssignment(); break;
                case 7: deleteAssignment(); break;
            }
        }
    }

    private void printMenu() {
        System.out.println("\nAssignment Planner Menu:");
        System.out.println("1. Add New Assignment");
        System.out.println("2. View All Assignments");
        System.out.println("3. View All Assignments (sorted by due date)");
        System.out.println("4. Search for Assignment by Title");
        System.out.println("5. Search for Assignment by Due Date range");
        System.out.println("6. Edit an Assignment");
        System.out.println("7. Delete an Assignment");
        System.out.println("0. Exit to Main Menu");
    }

    private void addAssignment() {
        System.out.println("\n-- Add New Assignment --");
        String title = ConsoleManager.readLine("Enter Title: ");
        String subject = ConsoleManager.readLine("Enter Subject: ");

        System.out.println("Enter Due Date:");
        int year = ConsoleManager.readInt("Enter Year (e.g., 2025): ", 2020, 2050);
        int month = ConsoleManager.readMonth();
        int day = ConsoleManager.readDay(year, month);
        SimpleDate dueDate = new SimpleDate(day, month, year);

        // Create new assignment
        Assignment newAssignment = new Assignment(title, subject, dueDate);
        // Add the assignment into both array list and binary tree
        assignments.add(newAssignment);
        assignmentsBinaryTree.insert(newAssignment);
        isModified = true; // Mark the list as modified.
        System.out.println("Assignment added successfully!");
    }

    private void viewAllAssignments() {
        System.out.println("\n-- All Assignments --");
        if (assignments.isEmpty()) {
            System.out.println("No assignments recorded.");
            return;
        }
        for (int i = 0; i < assignments.size(); i++) {
            System.out.println((i + 1) + "- " + assignments.get(i));
        }
    }

    private void deleteAssignment() {
        System.out.println("\n-- Delete an Assignment --");
        if (assignments.isEmpty()) {
            System.out.println("There are no assignments to delete.");
            return;
        }

        viewAllAssignments();
        System.out.println("-------------------------");
        int choice = ConsoleManager.readInt("Enter the number of the assignment to delete (or 0 to cancel): ", 0, assignments.size());

        if (choice == 0) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        int indexToDelete = choice - 1;

        Assignment removedAssignment = assignments.remove(indexToDelete);
        // rebuildTree();
        // Also remove that assignment from the Binary Tree.
        assignmentsBinaryTree.delete(removedAssignment);
        isModified = true; // Mark the list as modified.
        System.out.println("Successfully deleted: " + removedAssignment.getTitle());
    }

    private void editAssignment() {
        System.out.println("\n-- Edit an Assignment --");
        if (assignments.isEmpty()) {
            System.out.println("There are no assignments to edit.");
            return;
        }

        viewAllAssignments();
        System.out.println("-------------------------");
        int choice = ConsoleManager.readInt("Enter the number of the assignment to edit (or 0 to cancel): ", 0, assignments.size());

        if (choice == 0) {
            System.out.println("Edit operation cancelled.");
            return;
        }

        int indexToEdit = choice - 1;
        Assignment oldAssignment = assignments.get(indexToEdit);

        // Start with the original data. We will only change the parts the user selects.
        String title = oldAssignment.getTitle();
        String subject = oldAssignment.getSubject();
        SimpleDate dueDate = oldAssignment.getDueDate();
        boolean changed = false;

        while (true) {
            System.out.println("\nEditing: \n" + choice + "- " + oldAssignment.toString());
            System.out.println("What would you like to edit?");
            System.out.println("1. Title");
            System.out.println("2. Subject");
            System.out.println("3. Due Date");
            System.out.println("0. Finish editing");

            int editChoice = ConsoleManager.readInt("Enter your choice: ", 0, 3);

            if (editChoice == 0) {
                System.out.println("Returning to the main menu!");
                break;
            }
            switch (editChoice) {
                case 1:
                    System.out.println("Current Title: " + title);
                    title = ConsoleManager.readLine("Enter new Title: ");
                    changed = true;
                    break;
                case 2:
                    System.out.println("Current Subject: " + subject);
                    subject = ConsoleManager.readLine("Enter new Subject: ");
                    changed = true;
                    break;
                case 3:
                    System.out.println("Current Due Date: " + dueDate.toString());
                    System.out.println("Enter new Due Date:");
                    int year = ConsoleManager.readInt("Enter Year (e.g., 2025): ", 2020, 2050);
                    int month = ConsoleManager.readMonth();
                    int day = ConsoleManager.readDay(year, month);
                    // The constructor for SimpleDate is called in the new d-m-y order.
                    dueDate = new SimpleDate(day, month, year);
                    changed = true;
                    break;
            }
        }

        if (changed) {
            // Create a brand new Assignment object with the updated information.
            Assignment newAssignment = new Assignment(title, subject, dueDate);

            // Replace the old object with the new one in the list.
            assignments.set(indexToEdit, newAssignment);
            // Update the tree by deleting the old assignment and insert newly edited one.
            assignmentsBinaryTree.delete(oldAssignment);
            assignmentsBinaryTree.insert(newAssignment);
            //rebuildTree();

            System.out.println("Assignment updated successfully!");
            System.out.println("New Assignment: " + newAssignment.toString());
            isModified = true; // Mark the list as modified.
        }
        else {
            System.out.println("No changes were made!");
        }
    }

    // This method only handles the DISPLAY of the sorted list.
    private void viewSortedAssignmentsByDueDate() {
        System.out.println("\n-- Assignments Sorted by Due Date --");
        if (assignments.isEmpty()) {
            System.out.println("No assignments recorded.");
            return;
        }

        // If the list has been modified or the sorted list is empty, re-sort.
        if (isModified || sortedAssignments == null) {
            sortedAssignments = sortAssignmentsByDueDate();
            isModified = false; // Reset the flag after sorting.
        }

        for (int i = 0; i < sortedAssignments.size(); i++) {
            System.out.println((i + 1) + ": " + sortedAssignments.get(i));
        }
    }

    /**
     * A private helper sort method.
     * It creates a temporary, sortable copy of the main list, sorts it, and returns it.
     * @return A new CustomArrayList containing the sorted assignments.
     */
    private CustomArrayList<Assignment> sortAssignmentsByDueDate() {
        CustomArrayList<Assignment> sortableList = new CustomArrayList<>();
        for (int i = 0; i < assignments.size(); i++) {
            sortableList.add(assignments.get(i));
        }

        // Perform the sort on the temporary list.
        mergeSort(sortableList, 0, sortableList.size() - 1);

        return sortableList;
    }

    private void searchAssignmentByTitle() {
        System.out.println("\n-- Search for Assignment --");
        String searchTerm = ConsoleManager.readLine("Enter the title to search for: ").toLowerCase();
        boolean found = false;
        if (!assignments.isEmpty()) {
            // This is a linear search with O(n) complexity.
            for (int i = 0; i < assignments.size(); i++) {
                Assignment current = assignments.get(i);
                if (current.getTitle().toLowerCase().contains(searchTerm)) {
                    System.out.println("Found: " + current);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No assignment found with that title.");
        }
    }

    /**
     * Handles the user interaction for finding assignments within a specific date range.
     * Get a start and end date, then uses the BST to perform the search and displays the results.
     */
    private void searchAssignmentByDateRange() {
        System.out.println("\n-- Find Assignments by Date Range --");
        if (assignments.isEmpty()) {
            System.out.println("There are no assignments to search.");
            return;
        }

        System.out.println("Enter Start Date:");
        int startYear = ConsoleManager.readInt("Enter Year (e.g., 2025): ", 2020, 2050);
        int startMonth = ConsoleManager.readMonth();
        int startDay = ConsoleManager.readDay(startYear, startMonth);
        SimpleDate startDate = new SimpleDate(startDay, startMonth, startYear);

        System.out.println("\nEnter End Date:");
        int endYear = ConsoleManager.readInt("Enter Year (e.g., 2025): ", startYear, 2050);
        int endMonth = ConsoleManager.readMonth();
        int endDay = ConsoleManager.readDay(endYear, endMonth);
        SimpleDate endDate = new SimpleDate(endDay, endMonth, endYear);

        // Use the BST to find assignments in the specified range.
        CustomList<Assignment> results = assignmentsBinaryTree.findInRange(startDate, endDate);

        System.out.println("\n-- Assignments due between " + startDate + " and " + endDate + " --");
        if (results.isEmpty()) {
            System.out.println("No assignments found in this date range.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ": " + results.get(i));
            }
        }
    }

    /**
     * A helper method to clear and rebuild the BST from the main assignments list.
     * This is called after any operation that could change the tree's structure,
     * such as deleting an assignment or editing a due date.
     * O(n log(n)) time complexity, similar to re-sort
     */
    private void rebuildTree() {
        assignmentsBinaryTree = new AssignmentBST();
        for (int i = 0; i < assignments.size(); i++) {
            assignmentsBinaryTree.insert(assignments.get(i));
        }
    }

    // The recursive "divide" step of the Merge Sort algorithm.
    private void mergeSort(CustomArrayList<Assignment> list, int left, int right) {
        if (left < right) {
            int middle = left + (right - left) / 2; // Avoids potential overflow vs (left+right)/2
            mergeSort(list, left, middle);
            mergeSort(list, middle + 1, right);
            merge(list, left, middle, right); // The "conquer" step.
        }
    }

    // The "conquer" step, which merges two sorted sub-arrays.
    private void merge(CustomArrayList<Assignment> list, int l, int m, int r) {
        // Calculate the sizes of the two temporary sub-arrays.
        int n1 = m - l + 1;
        int n2 = r - m;

        // Create temporary array lists to hold the sub-array data.
        // This is the source of Merge Sort's O(n) space complexity.
        CustomArrayList<Assignment> L = new CustomArrayList<>();
        CustomArrayList<Assignment> R = new CustomArrayList<>();

        // Copy data from the main list into the temporary sub-arrays.
        for (int i = 0; i < n1; ++i) L.add(list.get(l + i));
        for (int j = 0; j < n2; ++j) R.add(list.get(m + 1 + j));

        // Merge the temporary arrays back into the original list.
        int i = 0, j = 0; // Initial indexes of first and second sub-arrays.
        int k = l;        // Initial index of the merged sub-array in the original list.
        while (i < n1 && j < n2) {
            // Compare the elements from L and R and place the smaller one into the list.
            if (L.get(i).getDueDate().isBeforeOrEqual(R.get(j).getDueDate())) {
                list.set(k, L.get(i));
                i++;
            } else {
                list.set(k, R.get(j));
                j++;
            }
            k++;
        }

        // After the main loop, one of the sub-arrays might still have elements left.
        // Copy any remaining elements from L.
        while (i < n1) { list.set(k, L.get(i)); i++; k++; }
        // Copy any remaining elements from R.
        while (j < n2) { list.set(k, R.get(j)); j++; k++; }
    }

    public static void main(String[] args) {
        // Create an instance of your application
        AssignmentPlanner planner = new AssignmentPlanner();

        // Call the run method to start the module
        planner.run();
    }
}
