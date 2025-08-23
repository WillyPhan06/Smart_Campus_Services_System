package modules.printmanager;

// Custom queue implementation using an array
public class PrintQueue implements QueueOperations<PrintJob> {
    private PrintJob[] history;
    private int historySize;
    private PrintJob[] jobs;
    private int front;
    private int rear;
    private int size;
    private int capacity;
    private int totalSubmittedJobs = 0;
    private int totalSubmittedCopies = 0;
    public PrintQueue(int capacity) {
        this.capacity = capacity;
        jobs = new PrintJob[capacity];
        front = 0;
        rear = -1;
        size = 0;

        history = new PrintJob[capacity];
        historySize = 0;
    }

    @Override
    public void enqueue(PrintJob job) {
        // Merge consecutive duplicate at rear if same user+file+role
        if (!isEmpty()) {
            PrintJob lastJob = jobs[(front + size - 1) % capacity];
            if (lastJob.getUsername().equals(job.getUsername()) &&
                    lastJob.getFilename().equals(job.getFilename()) &&
                    lastJob.getRole().equals(job.getRole())) {
                lastJob.incrementCopies(job.getCopies());
                totalSubmittedJobs++;
                totalSubmittedCopies += job.getCopies();
                return;
            }
        }

        if (isFull()) {
            System.out.println("Queue is full. Cannot add job.");
            return;
        }

        // Find correct position based on priority
        int insertIndex = size; // default is at the end
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            if (job.getPriorityLevel() > jobs[index].getPriorityLevel()) {
                insertIndex = i;
                break;
            }
        }

        // Shift jobs to make space for the new one
        for (int i = size; i > insertIndex; i--) {
            int from = (front + i - 1) % capacity;
            int to = (front + i) % capacity;
            jobs[to] = jobs[from];
        }

        // Insert job
        int actualIndex = (front + insertIndex) % capacity;
        jobs[actualIndex] = job;

        size++;
        rear = (front + size - 1) % capacity;

        // Track submissions
        totalSubmittedJobs++;
        totalSubmittedCopies += job.getCopies();
    }





    @Override
    public PrintJob dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. No job to serve.");
            return null;
        }
        PrintJob job = jobs[front];
        front = (front + 1) % capacity;
        size--;

        // NEW: record in history
        addToHistory(job);

        return job;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }


    @Override
    public PrintJob peek() {
        if (isEmpty()) {
            return null;
        }
        return jobs[front];
    }

    public int getSize() {
        return size;
    }

    // For debugging: show all jobs in order
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("No jobs in queue.");
            return;
        }
        System.out.println("Pending print jobs:");
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            System.out.println((i + 1) + ". " + jobs[index]);
        }
    }

    public void searchByUsername(String username) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            PrintJob job = jobs[index];
            if (job.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Found job: " + job);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No jobs found for user: " + username);
        }
    }

    // Search all jobs by filename
    public void searchByFilename(String filename) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            PrintJob job = jobs[index];
            if (job.getFilename().equalsIgnoreCase(filename)) {
                System.out.println("Found job: " + job);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No jobs found with file: " + filename);
        }
    }

    // Search all jobs by username + filename
    public void searchByUserAndFile(String username, String filename) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            PrintJob job = jobs[index];
            if (job.getUsername().equalsIgnoreCase(username) &&
                    job.getFilename().equalsIgnoreCase(filename)) {
                System.out.println("Found job: " + job);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No jobs found for user '" + username +
                    "' with file: " + filename);
        }
    }

    // Getter to help with searching
    public int getFront() {
        return front;
    }

    public int getCapacity() {
        return capacity;
    }

    public PrintJob getJobAt(int index) {
        return jobs[index];
    }

    private void addToHistory(PrintJob job) {
        if (historySize > 0) {
            PrintJob lastHistory = history[historySize - 1];
            if (lastHistory.getUsername().equals(job.getUsername()) &&
                    lastHistory.getFilename().equals(job.getFilename()) &&
                    lastHistory.getRole().equals(job.getRole())) {
                // Merge into last history entry (only if role matches too)
                lastHistory.incrementCopies(job.getCopies());
                return;
            }
        }


        if (historySize < history.length) {
            history[historySize++] = job;
        } else {
            System.out.println("History is full, cannot record more jobs.");
        }
    }


    public void displayHistory() {
        if (historySize == 0) {
            System.out.println("No jobs have been served yet.");
            return;
        }
        System.out.println("=== Job History ===");
        for (int i = 0; i < historySize; i++) {
            System.out.println((i + 1) + ". " + history[i]);
        }
    }

    // Cancel all jobs by username
    public void cancelByUsername(String username) {
        PrintQueue temp = new PrintQueue(capacity);
        while (!isEmpty()) {
            PrintJob job = dequeueInternal(); // helper that doesn't touch history
            if (!job.getUsername().equals(username)) {
                temp.enqueue(job);
            }
        }
        copyFrom(temp);
        System.out.println("All jobs from user '" + username + "' have been cancelled.");
    }

    // Cancel all jobs by filename
    public void cancelByFilename(String filename) {
        PrintQueue temp = new PrintQueue(capacity);
        while (!isEmpty()) {
            PrintJob job = dequeueInternal();
            if (!job.getFilename().equals(filename)) {
                temp.enqueue(job);
            }
        }
        copyFrom(temp);
        System.out.println("All jobs with file '" + filename + "' have been cancelled.");
    }

    // Cancel jobs by username + filename (specific job)
    public void cancelByUserAndFile(String username, String filename) {
        PrintQueue temp = new PrintQueue(capacity);
        while (!isEmpty()) {
            PrintJob job = dequeueInternal();
            if (!(job.getUsername().equals(username) && job.getFilename().equals(filename))) {
                temp.enqueue(job);
            }
        }
        copyFrom(temp);
        System.out.println("All jobs for user '" + username + "' with file '" + filename + "' have been cancelled.");
    }

    // Dequeue without adding to history (used for cancel filters)
    private PrintJob dequeueInternal() {
        if (isEmpty()) return null;
        PrintJob job = jobs[front];
        jobs[front] = null;
        front = (front + 1) % capacity;
        size--;
        return job;
    }

    private void copyFrom(PrintQueue other) {
        this.front = other.front;
        this.rear = other.rear;
        this.size = other.size;
        this.capacity = other.capacity;

        // make a true copy of the jobs array
        this.jobs = new PrintJob[capacity];
        for (int i = 0; i < size; i++) {
            int index = (other.front + i) % other.capacity;
            this.jobs[(this.front + i) % this.capacity] = other.jobs[index];
        }
    }


    // Serve multiple copies across jobs
    public void dequeueMultiple(int numCopies) {
        if (isEmpty()) {
            System.out.println("Queue is empty. No jobs to serve.");
            return;
        }

        int remaining = numCopies;

        while (remaining > 0 && !isEmpty()) {
            PrintJob job = jobs[front];

            if (job.getCopies() > remaining) {
                // Partial serve from this job
                job.setCopies(job.getCopies() - remaining);

                // Add served portion to history (preserve role)
                PrintJob served = new PrintJob(job.getUsername(), job.getFilename(), remaining, job.getRole());
                addToHistory(served);

                System.out.println("Served " + remaining + " copies of: " + served);
                remaining = 0; // done
            } else {
                // Serve full job and move queue
                int servedCount = job.getCopies();
                addToHistory(job);

                System.out.println("Served " + servedCount + " copies of: " + job);

                remaining -= servedCount;
                front = (front + 1) % capacity;
                size--;
            }
        }

        if (remaining > 0) {
            System.out.println("Not enough jobs in queue. " + remaining + " copies still unserved.");
        }
    }

    public void displayStatistics() {
        System.out.println("=== Print Statistics ===");

        System.out.println("Total jobs submitted: " + totalSubmittedJobs);
        System.out.println("Total jobs served: " + historySize);

        // total copies served
        int totalServedCopies = 0;
        for (int i = 0; i < historySize; i++) {
            totalServedCopies += history[i].getCopies();
        }
        System.out.println("Total copies printed: " + totalServedCopies);

        // role breakdown
        int staffJobs = 0, staffCopies = 0;
        int teacherJobs = 0, teacherCopies = 0;
        int studentJobs = 0, studentCopies = 0;
        int otherJobs = 0, otherCopies = 0;

        // Arrays for unique users and files
        String[] uniqueUsers = new String[historySize];
        int[] userCopies = new int[historySize];
        int uniqueUserCount = 0;

        String[] uniqueFiles = new String[historySize];
        int[] fileCopies = new int[historySize];
        int uniqueFileCount = 0;

        for (int i = 0; i < historySize; i++) {
            PrintJob job = history[i];

            // Role tracking
            switch (job.getRole()) {
                case "staff":
                    staffJobs++;
                    staffCopies += job.getCopies();
                    break;
                case "teacher":
                    teacherJobs++;
                    teacherCopies += job.getCopies();
                    break;
                case "student":
                    studentJobs++;
                    studentCopies += job.getCopies();
                    break;
                default:
                    otherJobs++;
                    otherCopies += job.getCopies();
                    break;
            }

            // Track user copies
            boolean foundUser = false;
            for (int j = 0; j < uniqueUserCount; j++) {
                if (uniqueUsers[j].equals(job.getUsername())) {
                    userCopies[j] += job.getCopies();
                    foundUser = true;
                    break;
                }
            }
            if (!foundUser) {
                uniqueUsers[uniqueUserCount] = job.getUsername();
                userCopies[uniqueUserCount] = job.getCopies();
                uniqueUserCount++;
            }

            // Track file copies
            boolean foundFile = false;
            for (int j = 0; j < uniqueFileCount; j++) {
                if (uniqueFiles[j].equals(job.getFilename())) {
                    fileCopies[j] += job.getCopies();
                    foundFile = true;
                    break;
                }
            }
            if (!foundFile) {
                uniqueFiles[uniqueFileCount] = job.getFilename();
                fileCopies[uniqueFileCount] = job.getCopies();
                uniqueFileCount++;
            }
        }

        System.out.println("\nJobs by role:");
        System.out.println("- Staff: " + staffJobs + " jobs (" + staffCopies + " copies)");
        System.out.println("- Teacher: " + teacherJobs + " jobs (" + teacherCopies + " copies)");
        System.out.println("- Student: " + studentJobs + " jobs (" + studentCopies + " copies)");
        System.out.println("- Other: " + otherJobs + " jobs (" + otherCopies + " copies)");

        // Find most active user
        String topUser = null;
        int maxUserCopies = 0;
        for (int i = 0; i < uniqueUserCount; i++) {
            if (userCopies[i] > maxUserCopies) {
                maxUserCopies = userCopies[i];
                topUser = uniqueUsers[i];
            }
        }

        // Find most printed file
        String topFile = null;
        int maxFileCopies = 0;
        for (int i = 0; i < uniqueFileCount; i++) {
            if (fileCopies[i] > maxFileCopies) {
                maxFileCopies = fileCopies[i];
                topFile = uniqueFiles[i];
            }
        }

        System.out.println("\nMost active user: " + (topUser != null ? topUser + " (" + maxUserCopies + " copies)" : "N/A"));
        System.out.println("Most printed file: " + (topFile != null ? topFile + " (" + maxFileCopies + " copies)" : "N/A"));

        System.out.println("\nPending jobs in queue: " + size);
    }



}
