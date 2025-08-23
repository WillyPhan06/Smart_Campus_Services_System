import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class listroom {
    private room[] rooms;
    private int count;
    private static final int DEFAULT_CAPACITY = 10;

    public listroom() {
        rooms = new room[DEFAULT_CAPACITY];
        count = 0;
    }

    // Add a new room with user input
    public void addRoom() {
        if (count == rooms.length) {
            expandArray();
        }

        room r = new room();
        r.getRoomDeatils();

        // Validation - check if room data is valid
        if (r.getDatebooking() == null || r.getStartbooking() == null || r.getEndbooking() == null) {
            System.out.println("Room booking failed: invalid date or time data.");
            return;
        }

        // Check if room is available for the selected time slot
        if (!checkAvailability(r.getDatebooking().toLocalDate(), r.getStartbooking(), r.getEndbooking())) {
            System.out.println("This time slot is already booked. Booking failed.");
            return;
        }

        rooms[count] = r;
        count++;
        System.out.println("Room successfully booked: " + r.getRoomName());
    }

    // Add a pre-created room object
    public void addroom(room r) {
        if (r == null) {
            System.out.println("Error: Cannot add null room");
            return;
        }

        if (count == rooms.length) {
            expandArray();
        }

        // Check if room is available for the selected time slot
        if (!checkAvailability(r.getDatebooking().toLocalDate(), r.getStartbooking(), r.getEndbooking())) {
            System.out.println("This time slot is already booked. Booking failed.");
            return;
        }

        rooms[count] = r;
        count++;
        System.out.println("Room successfully booked: " + r.getRoomName());
    }

    // Expand the array when it's full
    private void expandArray() {
        room[] newRooms = new room[rooms.length * 2];
        for (int i = 0; i < count; i++) {
            newRooms[i] = rooms[i];
        }
        rooms = newRooms;
        System.out.println("Room capacity expanded to " + rooms.length);
    }

    // Display all rooms
    public void displayAllRooms() {
        if (count == 0) {
            System.out.println("No rooms available.");
            return;
        }
        System.out.println("List of all rooms:");
        System.out.println("-------------------");
        for (int i = 0; i < count; i++) {
            if (rooms[i] != null) {
                System.out.println("Room " + (i + 1) + ":");
                System.out.println(rooms[i].toString());
                System.out.println("-------------------");
            }
        }
        System.out.println("Total rooms: " + count);
    }

    // Check if a room is available for a specific time slot
    public boolean checkAvailability(LocalDate date, LocalTime startTime, LocalTime endTime) {
        // Validation
        if (date == null || startTime == null || endTime == null) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            room r = rooms[i];

            // Skip if the room has been canceled
            if (r == null) continue;

            // Same date check
            if (r.getDatebooking().toLocalDate().equals(date)) {
                // Time overlap check
                if (!(endTime.isBefore(r.getStartbooking()) || startTime.isAfter(r.getEndbooking()))) {
                    return false; // Time slot not available
                }
            }
        }
        return true; // Time slot is available
    }

    // Cancel a booking by room ID
    public boolean cancelBooking(int roomId) {
        for (int i = 0; i < count; i++) {
            if (rooms[i] != null && rooms[i].getRoomID() == roomId) {
                System.out.println("Canceling booking for: " + rooms[i].getRoomName());

                // Mark as canceled by setting to null
                rooms[i] = null;

                // Shift array to remove the gap
                shiftArray(i);

                return true;
            }
        }
        return false; // Room not found
    }

    // Shift array elements to remove gaps
    private void shiftArray(int index) {
        for (int i = index; i < count - 1; i++) {
            rooms[i] = rooms[i + 1];
        }
        rooms[count - 1] = null;
        count--;
    }

    // Display bookings sorted by date and time
    public void displayBookingsSortedByDate() {
        if (count == 0) {
            System.out.println("No bookings available.");
            return;
        }

        // Create a copy of the rooms array for sorting
        room[] sortedRooms = new room[count];
        int validCount = 0;

        for (int i = 0; i < count; i++) {
            if (rooms[i] != null) {
                sortedRooms[validCount++] = rooms[i];
            }
        }

        if (validCount == 0) {
            System.out.println("No valid bookings to display.");
            return;
        }

        // Bubble sort by date, then by start time if dates are equal
        for (int i = 0; i < validCount - 1; i++) {
            for (int j = 0; j < validCount - i - 1; j++) {
                LocalDate date1 = sortedRooms[j].getDatebooking().toLocalDate();
                LocalDate date2 = sortedRooms[j + 1].getDatebooking().toLocalDate();

                // Compare dates first
                if (date1.isAfter(date2)) {
                    // Swap if first date is after second date
                    room temp = sortedRooms[j];
                    sortedRooms[j] = sortedRooms[j + 1];
                    sortedRooms[j + 1] = temp;
                } 
                // If dates are equal, compare start times
                else if (date1.isEqual(date2)) {
                    LocalTime time1 = sortedRooms[j].getStartbooking();
                    LocalTime time2 = sortedRooms[j + 1].getStartbooking();

                    if (time1.isAfter(time2)) {
                        // Swap if first time is after second time
                        room temp = sortedRooms[j];
                        sortedRooms[j] = sortedRooms[j + 1];
                        sortedRooms[j + 1] = temp;
                    }
                }
            }
        }

        // Display sorted bookings
        System.out.println("Bookings sorted by date and time:");
        System.out.println("--------------------------------");
        for (int i = 0; i < validCount; i++) {
            System.out.println("Booking " + (i + 1) + ":");
            System.out.println(sortedRooms[i].toString());
            System.out.println("--------------------------------");
        }
    }

    public int getCount() {
        return count;
    }
}