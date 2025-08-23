// Thuật toán tự cài: Bubble Sort + Binary Search cho mảng Book[]
package modules.digitallibrary;

public class AlgoUtils {

    // Sắp xếp theo title (alphabet, không phân biệt hoa thường)
    public static void bubbleSortByTitle(Book[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].title.compareToIgnoreCase(arr[j + 1].title) > 0) {
                    Book tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    // Tìm kiếm nhị phân theo title (exact match)
    public static Book binarySearchByTitle(Book[] arr, String titleExact) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            int cmp = arr[mid].title.compareToIgnoreCase(titleExact);
            if (cmp == 0) return arr[mid];
            if (cmp < 0) l = mid + 1;
            else r = mid - 1;
        }
        return null;
    }
}
