// File: assignmentplanner/CustomLinkedList.java
// Description: A custom implementation of a Doubly Linked List.

package modules.assignmentplanner;

/**
 * CustomLinkedList.java
 * This is an internal implementation detail of our module, so it is package-private.
 */
class CustomLinkedList<E> implements CustomList<E> {
    // A 'private static class' is a helper class completely hidden within its outer
    // class. This is a strong form of encapsulation.
    private static class Node<E> {
        E data; Node<E> next; Node<E> prev;
        Node(E data, Node<E> prev, Node<E> next) {
            this.data = data; this.prev = prev; this.next = next;
        }
    }

    private Node<E> head; // A reference to the first node.
    private Node<E> tail; // A reference to the last node for efficient O(1) additions.
    private int size = 0; // Caching the size makes the size() method an O(1) operation.

    @Override
    public void add(E element) {
        final Node<E> last = tail;
        final Node<E> newNode = new Node<>(element, last, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return node(index).data;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> nodeToRemove = node(index);
        final E element = nodeToRemove.data;
        final Node<E> next = nodeToRemove.next;
        final Node<E> prev = nodeToRemove.prev;

        // Re-wire the pointers of the surrounding nodes to bypass the removed node.
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            nodeToRemove.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            nodeToRemove.next = null;
        }

        nodeToRemove.data = null; // Help garbage collection.
        size--;
        return element;
    }

    // Private helper to find and return the node at a specific index.
    private Node<E> node(int index) {
        Node<E> current = head;
        // This loop demonstrates the O(n) access time for a linked list.
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    /**
     * Replaces the element at the specified position. This is an O(n) operation
     * for a linked list because it must first traverse to the node.
     * @param index The index of the element to replace.
     * @param element The new element to be stored at the position.
     */
    @Override
    public void set(int index, E element) {
        checkIndex(index);
        Node<E> nodeToUpdate = node(index);
        nodeToUpdate.data = element;
    }

    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }
    // A private helper method to handle a repeated task (validating an index).
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            // 'throw' is used to signal a critical, unrecoverable error to the caller.
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}