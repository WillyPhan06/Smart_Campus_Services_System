package modules.printmanager;

// Interface defining Queue Abstract Data Type
public interface QueueOperations<T> {
    void enqueue(T item);   // Add to back of queue
    T dequeue();            // Remove from front of queue
    boolean isEmpty();      // Check if queue is empty
    T peek();               // Look at first item without removing
    boolean isFull();
}
