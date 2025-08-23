// Quản lý danh sách sách bằng Linked List (tự cài) + triển khai IBookOperations
package modules.digitallibrary;

public class BookList implements IBookOperations {
    private Node head, tail;       // đầu/cuối danh sách
    private int size = 0;          // số phần tử
    private int nextId = 1;        // tự tăng ID
    private StackLog logs = new StackLog(200); // log hoạt động (stack tự cài)

    // --- Thêm sách (append cuối danh sách) ---
    @Override
    public void addBook(String title, String author) {
        Book b = new Book(nextId++, title, author);
        Node n = new Node(b);
        if (head == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
        logs.push("ADD: " + title);
        System.out.println("Book added (append) successfully!");
    }

    // --- Thêm sách & chèn theo alphabet (sorted insert) ---
    @Override
    public void addBookSorted(String title, String author) {
        Book b = new Book(nextId++, title, author);
        Node newNode = new Node(b);

        // Trường hợp rỗng
        if (head == null) {
            head = tail = newNode;
            size++;
            logs.push("ADD_SORTED: " + title);
            System.out.println("Book added in sorted order successfully!");
            return;
        }

        // Chèn vào đầu nếu nhỏ hơn head
        if (b.title.compareToIgnoreCase(head.data.title) < 0) {
            newNode.next = head;
            head = newNode;
            size++;
            logs.push("ADD_SORTED: " + title);
            System.out.println("Book added in sorted order successfully!");
            return;
        }

        // Duyệt tìm vị trí phù hợp
        Node cur = head;
        while (cur.next != null && cur.next.data.title.compareToIgnoreCase(b.title) < 0) {
            cur = cur.next;
        }

        // Chèn giữa/cuối
        newNode.next = cur.next;
        cur.next = newNode;
        if (newNode.next == null) tail = newNode; // cập nhật tail nếu thêm cuối

        size++;
        logs.push("ADD_SORTED: " + title);
        System.out.println("Book added in sorted order successfully!");
    }

    // --- Hiển thị tất cả sách ---
    @Override
    public void showAllBooks() {
        if (head == null) {
            System.out.println("(Library is empty)");
            return;
        }
        Node cur = head;
        while (cur != null) {
            System.out.println(cur.data);
            cur = cur.next;
        }
    }

    // --- Tìm theo tiêu đề (partial search) ---
    @Override
    public void searchByTitle(String keyword) {
        Node cur = head;
        boolean found = false;
        String key = keyword.toLowerCase();
        while (cur != null) {
            if (cur.data.title.toLowerCase().contains(key)) {
                System.out.println(cur.data);
                found = true;
            }
            cur = cur.next;
        }
        if (!found) System.out.println("No book found with title keyword: " + keyword);
    }

    // --- Tìm theo tác giả (partial search) ---
    @Override
    public void searchByAuthor(String keyword) {
        Node cur = head;
        boolean found = false;
        String key = keyword.toLowerCase();
        while (cur != null) {
            if (cur.data.author.toLowerCase().contains(key)) {
                System.out.println(cur.data);
                found = true;
            }
            cur = cur.next;
        }
        if (!found) System.out.println("No book found by author keyword: " + keyword);
    }

    // --- Mượn sách ---
    @Override
    public void borrowBook(int id) {
        Book b = findById(id);
        if (b != null && b.status == Book.Status.AVAILABLE) {
            b.status = Book.Status.UNAVAILABLE;
            b.borrowCount++;
            logs.push("BORROW: " + b.title);
            System.out.println("Borrowed successfully!");
        } else {
            System.out.println("Cannot borrow this book.");
        }
    }

    // --- Trả sách ---
    @Override
    public void returnBook(int id) {
        Book b = findById(id);
        if (b != null && b.status == Book.Status.UNAVAILABLE) {
            b.status = Book.Status.AVAILABLE;
            logs.push("RETURN: " + b.title);
            System.out.println("Returned successfully!");
        } else {
            System.out.println("Cannot return this book.");
        }
    }

    // --- Tìm theo ID (duyệt tuyến tính Linked List) ---
    private Book findById(int id) {
        Node cur = head;
        while (cur != null) {
            if (cur.data.id == id) return cur.data;
            cur = cur.next;
        }
        return null;
    }

    // --- Chuyển LinkedList -> Array (phục vụ sort/binary search/heap) ---
    public Book[] toArray() {
        Book[] arr = new Book[size];
        Node cur = head;
        int i = 0;
        while (cur != null) {
            arr[i++] = cur.data;
            cur = cur.next;
        }
        return arr;
    }

    public StackLog getLogs() {
        return logs;
    }
}
