// Thuật toán tự cài: Bubble Sort + Binary Search cho mảng Book[]
package modules.digitallibrary;

public class AlgoUtils {

    // --- Hàm tự viết so sánh chuỗi theo alphabet (không phân biệt hoa/thường) ---
    // Trả về: <0 nếu s1 < s2, =0 nếu s1 == s2, >0 nếu s1 > s2
    public static int compareStrings(String s1, String s2) {
        // Đưa về chữ thường để bỏ phân biệt hoa/thường
        String a = s1.toLowerCase();
        String b = s2.toLowerCase();

        int n = Math.min(a.length(), b.length());
        for (int i = 0; i < n; i++) {
            char c1 = a.charAt(i);
            char c2 = b.charAt(i);
            if (c1 != c2) {
                return c1 - c2;  // so sánh theo mã ASCII
            }
        }
        // Nếu chạy hết vòng lặp -> chuỗi nào ngắn hơn thì nhỏ hơn
        return a.length() - b.length();
    }

    // --- Bubble Sort theo title ---
    public static void bubbleSortByTitle(Book[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (compareStrings(arr[j].title, arr[j + 1].title) > 0) {
                    // hoán đổi
                    Book tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    // --- Binary Search theo title (exact match) ---
    public static Book binarySearchByTitle(Book[] arr, String titleExact) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            int cmp = compareStrings(arr[mid].title, titleExact);
            if (cmp == 0) return arr[mid]; // tìm thấy
            if (cmp < 0) l = mid + 1;      // titleExact lớn hơn -> dịch phải
            else r = mid - 1;              // titleExact nhỏ hơn -> dịch trái
        }
        return null; // không tìm thấy
    }
}
