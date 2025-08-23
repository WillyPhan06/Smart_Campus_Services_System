// File: assignmentplanner/CustomArrayList.java
// Description: A custom implementation of a Dynamic Array (like Python's list).

package modules.assignmentplanner;

/**
 * CustomArrayList.java
 * This is also an internal implementation detail, so it is package-private.
 */
class CustomArrayList<E> implements CustomList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    // The internal storage is an array of 'Object', the most generic type in Java.
    // This is necessary because we cannot create a generic array like 'new E[]'.
    private Object[] elements;
    private int size = 0;

    public CustomArrayList() { this.elements = new Object[DEFAULT_CAPACITY]; }

    @Override
    public void add(E element) {
        if (size == elements.length) resize();
        elements[size++] = element;
    }

    // This private helper method encapsulates the expensive resizing logic.
    private void resize() {
        int newCapacity = elements.length * 2;
        Object[] newElements = new Object[newCapacity];
        // This loop is an O(n) operation, which is why array list additions
        // have an "amortized" constant time complexity.
        for (int i = 0; i < size; i++) newElements[i] = elements[i];
        elements = newElements;
    }

    @Override
    // This annotation tells the compiler to ignore a specific, known warning.
    // We get a warning because the compiler can't verify the cast from Object to E
    // is safe, but we know it is due to our class design.
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        // We must cast the element from Object back to its specific type E.
        return (E) elements[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E) elements[index];
        // Loop through the rest of the element and shift them backwards
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        size--; // Decrement the size
        elements[size] = null; // Clear the last slot
        return oldValue;
    }

    public void set(int index, E element) { checkIndex(index); elements[index] = element; }
    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
}