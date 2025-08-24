// Mô hình dữ liệu 1 quyển sách
package modules.digitallibrary;

public class Book {
    public enum Status { AVAILABLE, UNAVAILABLE }

    int id;
    String title;
    String author;
    int quantity;
    Status status;
    int borrowCount;

    public Book(int id, String title, String author, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.borrowCount = 0;
        this.status = (quantity>0 ? Status.AVAILABLE : Status.UNAVAILABLE);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " by " + author +
                " | Qty: " + quantity +
                " | Borrowed: " + borrowCount +
                " | Status: " + status;
    }
}
