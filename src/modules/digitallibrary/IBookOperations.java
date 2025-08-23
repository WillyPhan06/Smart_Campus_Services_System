// Interface các chức năng cơ bản của thư viện
package modules.digitallibrary;

public interface IBookOperations {
    void addBook(String title, String author);            // Thêm vào cuối danh sách (append)
    void addBookSorted(String title, String author);      // Thêm & chèn theo alphabet (sorted insert)
    void showAllBooks();                                  // In toàn bộ sách
    void searchByTitle(String keyword);                   // Tìm theo tiêu đề (partial)
    void searchByAuthor(String keyword);                  // Tìm theo tác giả (partial)
    void borrowBook(int id);                              // Mượn sách
    void returnBook(int id);                              // Trả sách
}
