// File: assignmentplanner/SimpleDate.java
// Description: A custom class to represent a Date, avoiding forbidden libraries.

package modules.assignmentplanner;

/**
 * SimpleDate.java
 * A custom class to represent a Date, created to adhere to the project constraint
 * of not using advanced utility libraries like java.time. It is package-private.
 */
class SimpleDate {
    // 'final' fields make SimpleDate objects IMMUTABLE. Once a date is created,
    // its internal state cannot be changed. This is a key design choice for safety.
    private final int day;
    private final int month;
    private final int year;

    // The constructor is the only way to create and initialize a SimpleDate object.
    public SimpleDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Checks if this date is strictly before another date.
     * @param other The date to compare against.
     * @return true if this date is earlier than the other date, false otherwise.
     */
    public boolean isBefore(SimpleDate other) {
        if (this.year < other.year) return true;
        if (this.year > other.year) return false;
        // Years are the same, check months
        if (this.month < other.month) return true;
        if (this.month > other.month) return false;
        // Months are the same, check days
        return this.day < other.day;
    }

    /**
     * Checks if this date is exactly equal to another date.
     * @param other The date to compare against.
     * @return true if the year, month, and day are all the same, false otherwise.
     */
    public boolean isEqual(SimpleDate other) {
        return this.year == other.year && this.month == other.month && this.day == other.day;
    }

    /**
     * Compares this date with another, providing the core logic for sorting.
     * @param other The other date to compare against.
     * @return true if this date is before or equal to the other date.
     */
    public boolean isBeforeOrEqual(SimpleDate other) {
        return isBefore(other) || isEqual(other);
    }

    // Overriding toString() provides a clean, readable format when printing.
    @Override
    public String toString() {
        return String.format("%02d-%02d-%04d", day, month, year);
    }
}