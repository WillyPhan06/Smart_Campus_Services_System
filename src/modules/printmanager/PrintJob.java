package modules.printmanager;

// Plain Old Java Object (POJO) to represent a print job
public class PrintJob {
    private String username;
    private String filename;
    private int copies;
    private String role;        // NEW: role of the user
    private int priorityLevel;  // NEW: numeric priority for comparison


    public PrintJob(String username, String filename, int copies, String role) {
        this.username = username;
        this.filename = filename;
        this.copies = copies;
        this.role = role.toLowerCase().trim();
        this.priorityLevel = assignPriority(this.role); // auto map role to priority
    }

    private int assignPriority(String role) {
        switch (role) {
            case "staff":
                return 4;
            case "teacher":
                return 3;
            case "student":
                return 2;
            default:
                return 1; // "other" or anything else
        }
    }

    public String getUsername() {
        return username;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        String base = "User: " + username + " (" + role + "), File: " + filename;
        if (copies > 1) {
            base += " (x" + copies + ")";
        }
        return base;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public void incrementCopies(int extra) {
        this.copies += extra;
    }

    public String getRole() {
        return role;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

}
