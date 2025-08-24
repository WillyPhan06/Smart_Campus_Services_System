// Interface các chức năng cơ bản của thư viện
package modules.digitallibrary;

public interface IBookOperations {
    void addBook(String title, String author, int quantity);            // Thêm vào cuối danh sách (append)
    void addBookSorted(String title, String author, int quantity);      // Thêm & chèn theo alphabet (sorted insert)
    void showAllBooks();                                  // In toàn bộ sách
    void searchByTitle(String keyword);                   // Tìm theo tiêu đề (partial)
    void searchByAuthor(String keyword);                  // Tìm theo tác giả (partial)
    void borrowBook(int id);                              // Mượn sách
    void returnBook(int id);                              // Trả sách
    void deleteBook(int id);
    void updateBook(int id, String newTitle, String newAuthor, Integer newQuantity);


}
