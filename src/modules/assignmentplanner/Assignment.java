// File: assignmentplanner/Assignment.java
// Description: A class to hold the data for a single assignment.

package modules.assignmentplanner;

/**
 * Assignment.java
 * A "model" or "POJO" (Plain Old Java Object) whose purpose is to hold data
 * in a structured way. It is also designed to be immutable.
 */
class Assignment {
    private String title;
    private String subject;
    private SimpleDate dueDate;

    public Assignment(String title, String subject, SimpleDate dueDate) {
        this.title = title;
        this.subject = subject;
        this.dueDate = dueDate;
    }

    // Getters provide read-only access. The absence of setters enforces immutability.
    public String getTitle() { return title; }
    public String getSubject() { return subject; }
    public SimpleDate getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return String.format("Title: %-10s | Subject: %-10s | Due Date: %s",
                title, subject, dueDate.toString());
    }
}