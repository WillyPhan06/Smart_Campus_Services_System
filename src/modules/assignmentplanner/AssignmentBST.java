// File: assignmentplanner/AssignmentBST.java
// Description: A custom Binary Search Tree to store and query assignments by due date.

package modules.assignmentplanner;

/**
 * AssignmentBST.java
 * This class implements a Binary Search Tree (BST) to store assignments.
 * The tree is ordered based on the assignment's due date, allowing for
 * efficient range-based searches (e.g., "find all assignments due this week").
 *
 * How it works:
 * - Each node in the tree contains a single Assignment.
 * - For any given node, all assignments in its left subtree have an earlier or equal due date.
 * - All assignments in its right subtree have a later due date.
 * - Operations like insert and search are implemented using recursion.
 */
class AssignmentBST {

    /**
     * Inner class representing a single node in the BST.
     * It holds the assignment data and references to its left and right children.
     */
    private static class Node {
        Assignment assignment;
        Node left;
        Node right;

        Node(Assignment assignment) {
            this.assignment = assignment;
            this.left = null;
            this.right = null;
        }
    }

    // The root is the starting point of the tree.
    private Node root;

    public AssignmentBST() {
        this.root = null;
    }

    /**
     * Public method to insert a new assignment into the tree.
     * This is the entry point that kicks off the recursive insertion process.
     * @param assignment The assignment to be added.
     */
    public void insert(Assignment assignment) {
        root = insertRecursive(root, assignment);
    }

    /**
     * Private helper method that recursively finds the correct position
     * for the new assignment and inserts it.
     * @param current The current node being considered in the traversal.
     * @param assignment The assignment to insert.
     * @return The node that should be the root of the current subtree (this handles tree modifications).
     */
    private Node insertRecursive(Node current, Assignment assignment) {
        // Base case: If the current node is null, we've found an empty spot.
        // Create a new node with the assignment and return it.
        if (current == null) {
            return new Node(assignment);
        }

        // Recursive step: Compare the new assignment's due date with the current node's due date.
        SimpleDate newDate = assignment.getDueDate();
        SimpleDate currentDate = current.assignment.getDueDate();

        // Compare the due date to know which side of tree to go down
        if (newDate.isEqual(currentDate)) {
            // Same dates, compare title as strings.
            if (assignment.getTitle().compareTo(current.assignment.getTitle()) < 0) {
                current.left = insertRecursive(current.left, assignment);
            } else {
                current.right = insertRecursive(current.right, assignment);
            }
        } else if (newDate.isBefore(currentDate)) {
            // The new date is before the current date.
            current.left = insertRecursive(current.left, assignment);
        } else {
            // The new date is after the current date.
            current.right = insertRecursive(current.right, assignment);
        }
        return current;
    }

    /**
     * Public method to delete an assignment from the tree.
     * @param assignment The assignment to be deleted.
     */
    public void delete(Assignment assignment) {
        root = deleteRecursive(root, assignment);
    }

    /**
     * Private recursive helper to find and delete the specified assignment.
     * @param current The current node in the traversal.
     * @param assignment The assignment to delete.
     * @return The modified node after deletion.
     */
    private Node deleteRecursive(Node current, Assignment assignment) {
        // Base case: If the tree is empty or the node is not found.
        if (current == null) {
            return null;
        }

        SimpleDate deleteDate = assignment.getDueDate();
        SimpleDate currentDate = current.assignment.getDueDate();

        // Navigate the tree to find the node to delete
        if (deleteDate.isBefore(currentDate)) {
            current.left = deleteRecursive(current.left, assignment);
        } else if (currentDate.isBefore(deleteDate)) {
            current.right = deleteRecursive(current.right, assignment);
        } else { // Dates are equal, now compare by title
            int titleComparison = assignment.getTitle().compareTo(current.assignment.getTitle());
            if (titleComparison < 0) {
                current.left = deleteRecursive(current.left, assignment);
            } else if (titleComparison > 0) {
                current.right = deleteRecursive(current.right, assignment);
            } else {
                // We found the node to delete. Now handle the 3 cases.

                // Case 1: Node is a leaf (no children)
                if (current.left == null && current.right == null) {
                    return null;
                }
                // Case 2: Node has one child
                if (current.right == null) {
                    return current.left;
                }
                if (current.left == null) {
                    return current.right;
                }
                // Case 3: Node has two children
                // Find the in-order successor (smallest value in the right subtree).
                Assignment smallestValue = findSmallestValue(current.right);
                // Replace the current node's data with the successor's data.
                current.assignment = smallestValue;
                // Recursively delete the successor node from the right subtree.
                current.right = deleteRecursive(current.right, smallestValue);
            }
        }
        return current;
    }

    /**
     * Helper method to find the smallest value (in-order successor) in a subtree.
     * This is always the leftmost node.
     * @param root The root of the subtree to search.
     * @return The assignment with the smallest value.
     */
    private Assignment findSmallestValue(Node root) {
        // If the left child is null, this is the smallest node.
        if (root.left == null) {
            return root.assignment;
        }
        // Otherwise, keep going left recursively.
        else {
            return findSmallestValue(root.left);
        }
    }

    /**
     * Public method to find all assignments within a given date range.
     * @param startDate The start of the date range (inclusive).
     * @param endDate The end of the date range (inclusive).
     * @return A CustomList containing all assignments that fall within the range.
     */
    public CustomList<Assignment> findInRange(SimpleDate startDate, SimpleDate endDate) {
        CustomList<Assignment> results = new CustomArrayList<>();
        findInRangeRecursive(root, startDate, endDate, results);
        return results;
    }

    /**
     * Private helper method that recursively traverses the tree to find assignments
     * within the specified date range.
     * @param node The current node being visited.
     * @param startDate The start of the range.
     * @param endDate The end of the range.
     * @param results The list where found assignments are collected.
     */
    private void findInRangeRecursive(Node node, SimpleDate startDate,
                                      SimpleDate endDate, CustomList<Assignment> results) {
        // Base case: If the node is null, we've reached the end of a branch.
        if (node == null) {
            return;
        }

        SimpleDate currentDate = node.assignment.getDueDate();

        // --- The Core Logic of Efficient Range Searching ---

        // 1. If the current node's date is later than the start date,
        //    it's possible that assignments in the LEFT subtree could be in range.
        //    So, we recursively search the left subtree.
        if (startDate.isBeforeOrEqual(currentDate)) {
            findInRangeRecursive(node.left, startDate, endDate, results);
        }

        // 2. Check if the CURRENT node's date falls within the range.
        //    If it does, add its assignment to our results list.
        if (startDate.isBeforeOrEqual(currentDate) && currentDate.isBeforeOrEqual(endDate)) {
            results.add(node.assignment);
        }

        // 3. If the current node's date is earlier than the end date,
        //    it's possible that assignments in the RIGHT subtree could be in range.
        //    So, we recursively search the right subtree.
        if (currentDate.isBeforeOrEqual(endDate)) {
            findInRangeRecursive(node.right, startDate, endDate, results);
        }
    }
}
