// Mô hình dữ liệu 1 quyển sách
package modules.digitallibrary;

public class Book {
    public enum Status { AVAILABLE, UNAVAILABLE }

    int id;
    String title;
    String author;
    Status status;
    int borrowCount;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = Status.AVAILABLE;
        this.borrowCount = 0;
    }

    @Override
    public String toString() {
        return String.format("#%d | '%s' - %s | %s | borrowCount=%d",
                id, title, author, status, borrowCount);
    }
}
