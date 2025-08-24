package modules.digitallibrary;

public class BookList implements IBookOperations {
    private Node head, tail;       // đầu/cuối danh sách
    private int size = 0;          // số phần tử
    private int nextId = 1;        // ID tự tăng
    private StackLog logs = new StackLog(200); // log hoạt động

    // --- Thêm sách (append cuối) ---
    @Override
    public void addBook(String title, String author, int quantity) {
        Book b = new Book(nextId++, title, author, quantity);
        Node n = new Node(b);
        if (head == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
        logs.push("ADD: " + title + " (Qty=" + quantity + ")");
        System.out.println("Book added successfully!");
    }

    // --- Thêm sách (chèn theo alphabet) ---
    @Override
    public void addBookSorted(String title, String author, int quantity) {
        Book b = new Book(nextId++, title, author, quantity);
        Node newNode = new Node(b);

        if (head == null) {
            head = tail = newNode;
        }
        else if (AlgoUtils.compareStrings(b.title, head.data.title) < 0) {
            newNode.next = head;
            head = newNode;
        }
        else {
            Node cur = head;
            while (cur.next != null && AlgoUtils.compareStrings(cur.next.data.title, b.title) < 0) {
                cur = cur.next;
            }
            newNode.next = cur.next;
            cur.next = newNode;
            if (newNode.next == null) tail = newNode;
        }

        size++;
        logs.push("ADD_SORTED: " + title + " (Qty=" + quantity + ")");
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

    // --- Tìm theo Title (partial) ---
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

    // --- Tìm theo Author (partial) ---
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

    // --- Mượn sách (theo ID) ---
    @Override
    public void borrowBook(int id) {
        Book b = findById(id);
        if (b != null) {
            if (b.quantity > 0 ) {
                b.quantity--;
                b.borrowCount++;
                if (b.quantity == 0) {
                    b.status = Book.Status.UNAVAILABLE;
                }
                logs.push("BORROW: " + b.title);
                System.out.println("Borrowed successfully!");
            } else {
                System.out.println("This book is out of stock!");
            }
        } else {
            System.out.println("The book does not exist in the library.");
        }
    }

    // --- Trả sách (theo ID) ---
    @Override
    public void returnBook(int id) {
        Book b = findById(id);
        if (b != null) {
            b.quantity++;
            b.status = Book.Status.AVAILABLE;
            logs.push("RETURN: " + b.title);
            System.out.println("Returned successfully!");
        } else {
            System.out.println("Cannot return this book.");
        }
    }

    // --- Tìm theo ID ---
    private Book findById(int id) {
        Node cur = head;
        while (cur != null) {
            if (cur.data.id == id) return cur.data;
            cur = cur.next;
        }
        return null;
    }

    // --- Convert LinkedList -> Array ---
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

    // --- Sort + Binary Search (Option 8) ---
    public void sortAndBinarySearch(String target) {
        Book[] arr = toArray();

        // dùng AlgoUtils thay vì code trùng lặp
        AlgoUtils.bubbleSortByTitle(arr);

        /*System.out.println("Books sorted by title:");
        for (Book b : arr) {
            System.out.println(" - " + b.title + " by " + b.author);
        } */

        Book found = AlgoUtils.binarySearchByTitle(arr, target);
        if (found != null) {
            System.out.println("Found book: " + found);
        } else {
            System.out.println("Book with title '" + target + "' not found!");
        }
    }

    // --- Xoá sách theo ID ---
    @Override
    public void deleteBook(int id) {
        if (head == null) {
            System.out.println("Library is empty!");
            return;
        }

        // Trường hợp node đầu tiên
        if (head.data.id == id) {
            logs.push("DELETE: " + head.data.title);
            head = head.next;
            if (head == null) tail = null; // nếu xoá luôn node duy nhất
            size--;
            System.out.println("Book deleted successfully!");
            return;
        }

        // Duyệt từ node đầu để tìm node cần xoá
        Node cur = head;
        while (cur.next != null) {
            if (cur.next.data.id == id) {
                logs.push("DELETE: " + cur.next.data.title);

                // Nếu xoá node cuối cùng
                if (cur.next == tail) {
                    tail = cur;
                }

                // Bỏ qua node cần xoá
                cur.next = cur.next.next;

                size--;
                System.out.println("Book deleted successfully!");
                return;
            }
            cur = cur.next;
        }

        // Không tìm thấy ID
        System.out.println("Book with ID " + id + " not found!");
    }

    // --- Cập nhật sách theo ID ---
    @Override
    public void updateBook(int id, String newTitle, String newAuthor, Integer newQuantity) {
        Book b = findById(id);
        if (b == null) {
            System.out.println("Book with ID " + id + " not found!");
            return;
        }

        if (newTitle != null && !newTitle.isEmpty()) {
            b.title = newTitle;
        }
        if (newAuthor != null && !newAuthor.isEmpty()) {
            b.author = newAuthor;
        }
        if (newQuantity != null) {
            b.quantity = newQuantity;
            // Nếu quantity > 0 thì AVAILABLE, ngược lại UNAVAILABLE
            b.status = (newQuantity > 0) ? Book.Status.AVAILABLE : Book.Status.UNAVAILABLE;
        }

        logs.push("UPDATE: " + b.title);
        System.out.println("Book updated successfully!");
    }



}
