package modules.eventcalendar;

public class Event {
    private String title;
    private String type;
    private String date;
    private int id;

    public Event(int id, String title, String type, String date) {
        super();
        this.id = id;
        this.title = title;
        this.type = type;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Event [id=" + id + ", title=" + title + ", type=" + type +
                ", date=" + date + "]";
    }
}