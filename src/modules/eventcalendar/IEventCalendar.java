package modules.eventcalendar;

public interface IEventCalendar {
    void addNewEvent();
    void displayAllEvents();
    void sortEventsByDate();
    void searchEventsByName();
    void searchEventsByType();
    void deleteEventById();
}