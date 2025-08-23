// File: assignmentplanner/CustomList.java
// Description: The Abstract Data Type (ADT) for our list structures.

package modules.assignmentplanner;

/**
 * CustomList.java
 * This interface defines the Abstract Data Type (ADT) for a list. It specifies
 * the essential operations (the "what") without dictating the implementation (the "how").
 * <E> makes this interface generic, able to work with any object type.
 */
interface CustomList<E> {
    void add(E element);
    E get(int index);
    int size();
    boolean isEmpty();
    E remove(int index);
    void set(int index, E element);
}