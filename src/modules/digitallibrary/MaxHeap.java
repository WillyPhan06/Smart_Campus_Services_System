// Max-Heap theo borrowCount để lấy Top N sách mượn nhiều nhất (tự cài)
package modules.digitallibrary;

public class MaxHeap {
    private Book[] heap;
    private int size = 0;

    public MaxHeap(int capacity) {
        heap = new Book[capacity];
    }

    public void add(Book b) {
        heap[size] = b;
        int i = size;
        size++;
        // Sift-up
        while (i > 0 && heap[(i - 1) / 2].borrowCount < heap[i].borrowCount) {
            Book tmp = heap[i];
            heap[i] = heap[(i - 1) / 2];
            heap[(i - 1) / 2] = tmp;
            i = (i - 1) / 2;
        }
    }

    public Book poll() {
        if (size == 0) return null;
        Book root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapify(0);
        return root;
    }

    private void heapify(int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < size && heap[l].borrowCount > heap[largest].borrowCount) largest = l;
        if (r < size && heap[r].borrowCount > heap[largest].borrowCount) largest = r;

        if (largest != i) {
            Book tmp = heap[i];
            heap[i] = heap[largest];
            heap[largest] = tmp;
            heapify(largest);
        }
    }
}
