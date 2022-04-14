// Min-heap implementation by Patrick Sullivan, based on OpenDSA Heap code
// Can use `java -ea` (Java's VM arguments) to Enable Assertions
// These assertions will check valid heap positions

/**
 * 
 * MinHeap class which is meant to hold generic types T. A MinHeap's root is the
 * minimum value in the entire heap. It h as extended abilities that allow for
 * replacement selection.
 * 
 * @author Aniket Adhikari, Chris Koehler
 * @version 04.03.2022
 *
 * @param <T>
 *            is the generic type that is held by the MinHeap
 */
public class MinHeap<T extends Comparable<T>> {
    private T[] heap; // Pointer to the heap array
    private int capacity; // Maximum size of the heap
    private int n; // Number of things currently in heap
    private int deactiveSize; // Number of things in deactivated portion

    /**
     * Constructor supporting preloading of heap contents
     * 
     * @param h
     *            the array that is being edited to reflect the actual MinHeap
     * @param heapSize
     *            is the number of elements in the heap
     * @param capacity
     *            is the number of elements allowed to be in the heap. heapSize
     *            is not allowed to surpass this. Capacity is also not allowed
     *            to surpass the size the length of the array passed in earlier
     *            as a parameter
     */
    public MinHeap(T[] h, int heapSize, int capacity) {
        if (h.length < capacity) {
            throw new AssertionError(
                "capacity can't be beyond the array elements");
        }
        if (capacity < heapSize) {
            throw new AssertionError("heap size is beyond max");
        }
// assert capacity <= h.length : "capacity is beyond array limits";
// assert heapSize <= capacity : "Heap size is beyond max";
        heap = h;
        n = heapSize;
        this.capacity = capacity;
        deactiveSize = 0;
        buildHeap();
    }


    /**
     * Returns the root element of the heap without removing it
     * 
     * @return root element of the MinHeap
     */
    public T getRoot() {
        return heap[0];
    }


    /**
     * Return position for left child of pos
     * 
     * @param parentPosition
     *            is the location of the parent of the child we are trying to
     *            determine the position of
     * @return position of the left child
     */
    public static int leftChild(int parentPosition) {
        return 2 * parentPosition + 1;
    }


    /**
     * Return position for right child of pos
     * 
     * @param parentPosition
     *            is the location of the parent of the child we are trying to
     *            determine the position of
     * @return position of the right child
     */
    public static int rightChild(int parentPosition) {
        return 2 * parentPosition + 2;
    }


    /**
     * Return position for the parent of pos
     * 
     * @param childPosition
     *            is the location of the child of the parent we are trying to
     *            determine the position of
     * @return position for the parent of pos
     */
    public static int parent(int childPosition) {
        return (childPosition - 1) / 2;
    }


    /**
     * Gets the number of items in the heap
     * 
     * @return current size of the heap
     */
    public int heapSize() {
        return n;
    }


    /**
     * Return true if pos a leaf position, false otherwise
     * 
     * @param pos
     *            is the position of the heap element that we are determining is
     *            a leaf or not. A leaf would be an element that has no
     *            children, left or right.
     * @return true if the element at the specified position is a leaf, false
     *         otherwise
     */
    public boolean isLeaf(int pos) {
        return (n / 2 <= pos) && (pos < n);
    }


    /**
     * Inserts an element of type T into the heap. The element is placed at the
     * end of the heap (last position) and is subsequently sifted up so that the
     * element is in the right place
     * 
     * @param key
     *            element to be inserted into the heap
     * @return if the insertion was successful or not. An unsuccessful,
     *         insertion would occur if we are trying to insert an element into
     *         the heap
     */
    public boolean insert(T key) {
        if (n >= capacity) {
            return false;
        }
        heap[n] = key;
        n++;
        siftUp(n - 1);
        return true;
    }


    /**
     * Performs an insertion on an empty root according to replacement
     * selection. Inserts key, then handles the cases in which this new
     * key must be swapped and deactivated, or this new key must simply be
     * sifted down to its proper position within the heap.
     * 
     * @precondition removeMinNoUpdate() has been called, leaving the root null
     * @param key
     *            - The new key to be inserted into the root of the MinHeap
     * @param deactivate
     *            - Whether this new key needs to be moved to the
     *            inactive portion of the heap
     */
    public boolean replacementSelectionInsert(T key, boolean deactivate) {
        // Check that conditions for this method are met
        if (heap[0] != null) {
            return false;
        }

        // Insert key into the empty root
        heap[0] = key;
        n++;

        // Case: key must be moved to inactive portion of heap
        if (deactivate) {
            swapAndDeactivate();
            return true;
        }
        // Case: key will remain active in heap, move to correct pos
        siftDown(0);
        return true;
    }


    /**
     * 
     * Reactivates a logically empty heap by converting the deactivated portion
     * to the active portion, then rebuilding the heap
     * 
     * @return whether the reactivation of the heap was successful or not
     */
    public boolean reactivate() {
        if (deactiveSize > 0) {
            n = deactiveSize;
            deactiveSize = 0;
            buildHeap();
            return true;
        }
        return false;
    }


    /**
     * Organize contents of array to satisfy the heap structure
     */
    public void buildHeap() {
        for (int i = parent(n - 1); i >= 0; i--) {
            siftDown(i);
        }
    }


    /**
     * Moves an element down to its correct place
     * 
     * @param pos
     *            position of element that is being sifted down
     */
    public void siftDown(int pos) {
        if (pos < 0) {
            throw new AssertionError("Position called was " + pos
                + " but must be greater than or equal to 0");
        }
        else if (pos >= n) {
            throw new AssertionError("Position called was " + pos
                + " but it must be less than " + n);
        }
        while (!isLeaf(pos)) {
            int child = leftChild(pos);
            if ((child + 1 < n) && isLessThan(child + 1, child)) {
                child = child + 1; // child is now index with the smaller value
            }
            if (!isLessThan(child, pos)) {
                return; // stop early
            }
            swap(pos, child);
            pos = child; // keep sifting down
        }
    }


    /**
     * Moves an element up to its correct place
     * 
     * @param pos
     *            position of element that is being sifted down
     */
    public void siftUp(int pos) {
// assert (0 <= pos && pos < n) : "Invalid heap position";
        if (pos < 0) {
            throw new AssertionError("Position called was " + pos
                + " but must be greater than or equal to 0");
        }
        else if (pos >= n) {
            throw new AssertionError("Position called was " + pos
                + " but it must be less than " + n);
        }
        while (pos > 0) {
            int parent = parent(pos);
            if (isLessThan(parent, pos)) {
                return; // stop early
            }
            swap(pos, parent);
            pos = parent; // keep sifting up
        }
    }


    /**
     * Remove and return minimum value
     * 
     * @return the minimum value of the MinHeap, which is also the root
     */
    public T removeMin() {
        if (n > 0) {
            n--;
            if (n == 0) {
                return heap[0];
            }
            swap(0, n); // Swap minimum with last value
            siftDown(0); // Put new heap root val in correct place
            return heap[n];
        }
        else {
            throw new AssertionError("Heap is empty; cannot remove");
        }
    }


    /**
     * Remove and return minimum value, leaving the root empty. Preps the
     * MinHeap for an iteration of replacement selection
     * 
     * @return The minimum (root) value of the MinHeap
     */
    public T removeMinNoUpdate() {
        if (n > 0) {
            T removed = heap[0];
            heap[0] = null;
            n--;
            return removed;
        }
        else {
            throw new AssertionError("Heap is empty; cannot remove");
        }
    }


    /**
     * Remove and return element at specified position
     * 
     * @param pos
     *            the position of the element that is being removed
     * @return the element at the specified positiion
     */
    public T remove(int pos) {
        if (pos < 0) {
            throw new AssertionError(
                "Invalid heap position: position must be 0 or more");
        }
        if (pos >= n) {
            throw new AssertionError(
                "Invalid heap position: position must be less than " + n);
        }
        n--;
        if (n > 0) {
            swap(pos, n); // Swap with last value
            update(pos); // Move other value to correct position
        }
        return heap[n];
    }


    /**
     * Modify the value at the given position
     * 
     * @param pos
     *            is the position of the element being modified
     * @param newVal
     *            is the value we're placing into the specified position
     */
    public void modify(int pos, T newVal) {
        if (pos < 0) {
            throw new AssertionError(
                "Invalid heap position: position must be 0 or more");
        }
        if (pos >= n) {
            throw new AssertionError(
                "Invalid heap position: position must be less than " + n);
        }
        heap[pos] = newVal;
        update(pos);
    }


    /**
     * Swaps the root with the last element in the Heap, decrements size
     * "deactivating" that new last element (making it effectively not a part
     * of the heap anymore), and sifts the new root down to its correct
     * position in the heap.
     */
    private void swapAndDeactivate() {
        if (n == 1) {
            n--;
            deactiveSize++;
        }
        else if (n > 0) {
            n--;
            swap(0, n); // Swap minimum with last value
            deactiveSize++;
            siftDown(0); // Put new heap root val in correct place
        }
        else {
            throw new AssertionError("Heap is empty; cannot remove");
        }
    }


    /**
     * The value at pos has been changed, restore the heap property
     * 
     * @param pos
     *            is the position of the element being updated
     */
    private void update(int pos) {
        siftUp(pos); // priority goes up
        siftDown(pos); // unimportant goes down
    }


    /**
     * swaps the elements at two positions
     * 
     * @param pos1
     *            position of element being placed at position pos2
     * 
     * @param pos2
     *            position of element being placed at position pos1
     */
    public void swap(int pos1, int pos2) {
        T temp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = temp;
    }


    /**
     * does fundamental comparison used for checking heap validity
     * 
     * @param pos1
     *            check if this value is less than pos2
     * @param pos2
     *            checks if this value is more than pos1
     * @return if pos1 is less than pos2
     */
    private boolean isLessThan(int pos1, int pos2) {
        return heap[pos1].compareTo(heap[pos2]) < 0;
    }


    /**
     * Gets the number of elements in the minheap that have been deactivated for
     * use in the next run
     * 
     * @return number of deactivated elements
     */
    public int getDeactiveSize() {
        return deactiveSize;
    }

}
