package modules.eventcalendar;

import java.util.Scanner;

public class EventCalendarImpl implements IEventCalendar {
    // Custom array-based data structure instead of ArrayList
    Event[] events;
    int currentSize;
    int maxSize;

    Scanner sc = new Scanner(System.in);

    // Predefined events
    Event event1 = new Event(1, "Student Orientation", "Academic", "2025-09-15");
    Event event2 = new Event(2, "Tech Conference", "Academic", "2025-08-27");
    Event event3 = new Event(3, "Sports Day", "Sports", "2025-10-30");
    Event event4 = new Event(4, "Anniversary", "Festival", "2025-09-10");

    // Constructor to initialize event array
    public EventCalendarImpl() {
        maxSize = 100;
        events = new Event[maxSize];
        currentSize = 0;

        // Add predefined events
        addEventToArray(event1);
        addEventToArray(event2);
        addEventToArray(event3);
        addEventToArray(event4);
    }

    // Custom method to add event to array
    private void addEventToArray(Event event) {
        if (currentSize < maxSize) {
            events[currentSize] = event;
            currentSize++;
        }
    }

    // Custom method to expand array if needed
    private void expandArray() {
        if (currentSize >= maxSize) {
            maxSize *= 2;
            Event[] newEvents = new Event[maxSize];
            for (int i = 0; i < currentSize; i++) {
                newEvents[i] = events[i];
            }
            events = newEvents;
        }
    }

    @Override
    public void addNewEvent() {
        int id;
        while (true) {
            System.out.print("Enter event ID: ");
            try {
                id = Integer.parseInt(sc.nextLine().trim());
                
                // Check if ID already exists
                boolean idExists = false;
                for (int i = 0; i < currentSize; i++) {
                    if (events[i].getId() == id) {
                        idExists = true;
                        break;
                    }
                }
                
                if (idExists) {
                    System.out.println("ID already exists! Please enter a different ID.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format! Please enter a number.");
            }
        }

        System.out.print("Enter event title: ");
        String title = sc.nextLine().trim();

        String type = "";
        while (true) {
            System.out.print("Enter event type (Sports / Festival / Academic): ");
            type = sc.nextLine().trim();
            if (type.equalsIgnoreCase("sports") || type.equalsIgnoreCase("festival") || type.equalsIgnoreCase("academic")) {
                break;
            } else {
                System.out.println("Invalid type! Please enter only Sports, Festival, or Academic.");
            }
        }

        String date = "";
        while (true) {
            System.out.print("Enter event date (YYYY-MM-DD): ");
            date = sc.nextLine().trim();
            
            // Validate date format and month range
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                String[] dateParts = date.split("-");
                int month = Integer.parseInt(dateParts[1]);
                if (month >= 1 && month <= 12) {
                    break;
                } else {
                    System.out.println("Invalid month! Month must be between 1 and 12.");
                }
            } else {
                System.out.println("Invalid! Please enter YYYY-MM-DD format.");
            }
        }

        Event newEvent = new Event(id, title, type, date);
        expandArray();
        addEventToArray(newEvent);

        System.out.println("Event added successfully!");
        System.out.println(newEvent);
    }

    @Override
    public void displayAllEvents() {
        if (currentSize == 0) {
            System.out.println("No events found.");
            return;
        }
        
        // Sort events by ID before displaying
        sortEventsById();
        
        System.out.println("=== All Events (Sorted by ID) ===");
        for (int i = 0; i < currentSize; i++) {
            System.out.println(events[i]);
        }
    }
    
    // Helper method to sort events by ID
    private void sortEventsById() {
        if (currentSize <= 1) {
            return;
        }
        
        // Simple bubble sort for ID
        for (int i = 0; i < currentSize - 1; i++) {
            for (int j = 0; j < currentSize - i - 1; j++) {
                if (events[j].getId() > events[j + 1].getId()) {
                    // Swap events
                    Event tempEvent = events[j];
                    events[j] = events[j + 1];
                    events[j + 1] = tempEvent;
                }
            }
        }
    }

    @Override
    public void sortEventsByDate() {
        if (currentSize <= 1) {
            System.out.println("No sorting needed.");
            return;
        }

        // Using bubble sort algorithm from your existing code
        int swaps = bubbleSortEvents(events, currentSize);

        System.out.println("Events sorted by date successfully!");
        System.out.println("Total swaps: " + swaps);
        
        // Display sorted events directly without calling displayAllEvents()
        System.out.println("=== Sorted Events by Date ===");
        for (int i = 0; i < currentSize; i++) {
            System.out.println(events[i]);
        }
    }

    // Custom bubble sort for events based on date
    private int bubbleSortEvents(Event[] arr, int n) {
        int i, j, temp;
        boolean swapped;
        int swapCount = 0;

        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (compareDates(arr[j].getDate(), arr[j + 1].getDate()) > 0) {
                    // Swap events
                    Event tempEvent = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tempEvent;
                    swapped = true;
                    swapCount++;
                }
            }
            if (!swapped)
                break;
        }
        return swapCount;
    }

    // Custom date comparison method
    private int compareDates(String date1, String date2) {
        // Simple string comparison for YYYY-MM-DD format
        return date1.compareTo(date2);
    }

    @Override
    public void searchEventsByName() {
        System.out.print("Enter event name to search: ");
        String searchName = sc.nextLine().toLowerCase();

        boolean found = false;
        System.out.println("=== Search Results by Name ===");

        for (int i = 0; i < currentSize; i++) {
            if (events[i].getTitle().toLowerCase().contains(searchName)) {
                System.out.println(events[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No events found with name containing: " + searchName);
        }
    }

    @Override
    public void searchEventsByType() {
        System.out.print("Enter event type to search: (Sports / Festival / Academic) ");
        String searchType = sc.nextLine().trim();

        boolean found = false;
        System.out.println("=== Search Results by Type ===");

        for (int i = 0; i < currentSize; i++) {
            String eventType = events[i].getType();
            if (eventType.equalsIgnoreCase(searchType)) {
                System.out.println(events[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No events found with type: '" + searchType + "'");
        }
    }

    @Override
    public void deleteEventById() {
        if (currentSize == 0) {
            System.out.println("No events to delete.");
            return;
        }
        
        System.out.print("Enter event ID to delete: ");
        try {
            int deleteId = Integer.parseInt(sc.nextLine().trim());
            
            boolean found = false;
            for (int i = 0; i < currentSize; i++) {
                if (events[i].getId() == deleteId) {
                    System.out.println("Found event: " + events[i]);
                    System.out.print("Are you sure you want to delete this event? (y/n): ");
                    String confirm = sc.nextLine().trim().toLowerCase();
                    
                    if (confirm.equals("y") || confirm.equals("yes")) {
                        // Remove event by shifting remaining elements
                        for (int j = i; j < currentSize - 1; j++) {
                            events[j] = events[j + 1];
                        }
                        currentSize--;
                        System.out.println("Event deleted successfully!");
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                System.out.println("No event found with ID: " + deleteId);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }

    // Utility method to print events
    private void printEvents(Event[] arr, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}