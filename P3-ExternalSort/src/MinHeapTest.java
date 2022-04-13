import java.nio.ByteBuffer;
import student.TestCase;

/**
 * Tests the MinHeap class
 * 
 * @author Aniket Adhikari, Chris Koehler
 * @version 04.03.2022
 *
 */
public class MinHeapTest extends TestCase {
    private MinHeap<Record> mh;
    private Record[] r;
    private int numRecords;

    /**
     * set up for the test
     */
    public void setUp() {
        numRecords = 10;
        r = new Record[numRecords];
        mh = new MinHeap<Record>(r, 0, numRecords);
    }


    /**
     * Tests the initialization of an invalid minheap.
     */
    public void testInit() {
        Double d[] = new Double[1];
        MinHeap<Double> mhd = null;
        AssertionError e = null;
        try {
            mhd = new MinHeap<Double>(d, 0, 100);
        }
        catch (AssertionError a) {
            e = a;
        }
        assertNotNull(e);
        e = null;
        try {
            mhd = new MinHeap<Double>(d, 100, 0);
        }
        catch (AssertionError a) {
            e = a;
        }
        assertNotNull(e);
    }


    /**
     * tests both leftChild and rightChild methods
     */
    public void testChildren() {
        ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(0);
        rec.putDouble(10);
        Record r1 = new Record(rec.array());
        mh.insert(r1);

        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(1);
        rec.putDouble(21);
        Record r2 = new Record(rec.array());
        mh.insert(r2);

        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(2);
        rec.putDouble(9);
        Record r3 = new Record(rec.array());
        mh.insert(r3);

        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(3);
        rec.putDouble(37);
        Record r4 = new Record(rec.array());
        mh.insert(r4);

        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(4);
        rec.putDouble(40);
        Record r5 = new Record(rec.array());
        mh.insert(r5);

        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(5);
        rec.putDouble(81);
        Record r6 = new Record(rec.array());
        mh.insert(r6);

        assertEquals(1, MinHeap.leftChild(0));
        assertEquals(3, MinHeap.leftChild(1));
        assertEquals(5, MinHeap.leftChild(2));
        assertEquals(7, MinHeap.leftChild(3));
        assertEquals(9, MinHeap.leftChild(4));
        assertEquals(11, MinHeap.leftChild(5));
        assertEquals(13, MinHeap.leftChild(6));

        assertEquals(2, MinHeap.rightChild(0));
        assertEquals(4, MinHeap.rightChild(1));
        assertEquals(6, MinHeap.rightChild(2));
        assertEquals(8, MinHeap.rightChild(3));
        assertEquals(10, MinHeap.rightChild(4));
        assertEquals(12, MinHeap.rightChild(5));
        assertEquals(14, MinHeap.rightChild(6));
    }


    public void testHeapSize() {
        ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        assertEquals(0, mh.heapSize());
        // placement of record 1
        rec.putLong(1);
        rec.putDouble(81);
        Record firstRecord = new Record(rec.array());
        mh.insert(firstRecord);
        assertEquals(1, mh.heapSize());
        assertTrue(81.0 == r[0].getKey());

        // placement of record 2
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(2);
        rec.putDouble(17);
        Record secondRecord = new Record(rec.array());
        mh.insert(secondRecord);
        assertEquals(2, mh.heapSize());
        assertTrue(17.0 == r[0].getKey());
        assertTrue(81.0 == r[1].getKey());

        // placement of record 3
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(3);
        rec.putDouble(24);
        Record thirdRecord = new Record(rec.array());
        mh.insert(thirdRecord);
        assertEquals(3, mh.heapSize());
        assertTrue(17.0 == r[0].getKey());
        assertTrue(81.0 == r[1].getKey());
        assertTrue(24.0 == r[2].getKey());

        // placement of record 4
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(4);
        rec.putDouble(13);
        Record fourthRecord = new Record(rec.array());
        mh.insert(fourthRecord);
        assertEquals(4, mh.heapSize());
        assertEquals(fourthRecord, mh.getRoot());
        assertEquals(fourthRecord, mh.removeMin());
        assertEquals(secondRecord, mh.getRoot());
        assertEquals(secondRecord, mh.removeMin());
    }


    /**
     * Tests that insertion fails on a MinHeap that has reached capacity
     */
    public void testInsertFull() {
        for (int i = 0; i < numRecords; i++) {
            ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
            rec.putLong((long)i);
            rec.putDouble((double)i);
            Record record = new Record(rec.array());
            assertTrue(mh.insert(record));
        }
        ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong((long)10);
        rec.putDouble((double)10);
        Record overCapRecord = new Record(rec.array());
        assertFalse(mh.insert(overCapRecord));
    }


    /**
     * Tests that MinHeap properties remain satisfied after inserting new
     * Records
     */
    public void testInsert() {
        ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        // placement of record 1
        rec.putLong(1);
        rec.putDouble(81);
        Record firstRecord = new Record(rec.array());
        mh.insert(firstRecord);
        assertEquals(1, mh.heapSize());
        assertTrue(81.0 == r[0].getKey());

        // placement of record 2
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(2);
        rec.putDouble(17);
        Record secondRecord = new Record(rec.array());
        mh.insert(secondRecord);
        assertEquals(2, mh.heapSize());
        assertTrue(17.0 == r[0].getKey());
        assertTrue(81.0 == r[1].getKey());

        // placement of record 3
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(3);
        rec.putDouble(24);
        Record thirdRecord = new Record(rec.array());
        mh.insert(thirdRecord);
        assertEquals(3, mh.heapSize());
        assertTrue(17.0 == r[0].getKey());
        assertTrue(81.0 == r[1].getKey());
        assertTrue(24.0 == r[2].getKey());

        // placement of record 4
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(4);
        rec.putDouble(13);
        Record fourthRecord = new Record(rec.array());
        mh.insert(fourthRecord);
        assertEquals(4, mh.heapSize());
        assertTrue(13.0 == r[0].getKey());
        assertTrue(17.0 == r[1].getKey());
        assertTrue(24.0 == r[2].getKey());
        assertTrue(81.0 == r[3].getKey());
    }


    /**
     * Tests that MinHeap properties are satisfied after the constructor
     * is called and an unorganized Record array is passed in
     */
    public void testBuildHeap() {
        // Create unorganized Record[]
        ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(1);
        rec.putDouble(81);
        Record firstRecord = new Record(rec.array());
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(2);
        rec.putDouble(17);
        Record secondRecord = new Record(rec.array());
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(3);
        rec.putDouble(24);
        Record thirdRecord = new Record(rec.array());
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(4);
        rec.putDouble(13);
        Record fourthRecord = new Record(rec.array());

        // Record[] == {81, 17, 24, 13}
        r[0] = firstRecord;
        r[1] = secondRecord;
        r[2] = thirdRecord;
        r[3] = fourthRecord;
        MinHeap<Record> testHeap = new MinHeap<Record>(r, 4, 10);

        // Ensure Record[] satisfied MinHeap properties
        assertTrue(13.0 == r[0].getKey());
        assertTrue(17.0 == r[1].getKey());
        assertTrue(24.0 == r[2].getKey());
        assertTrue(81.0 == r[3].getKey());
    }


    /**
     * Tests the MinHeap when there are 4 records added and we want to grab the
     * root of the heap, which is the same thing as the minimum value
     */
    public void testRemoveMin() {
        AssertionError a = null;
        try {
            mh.removeMin();
        }
        catch (AssertionError e) {
            a = e;
        }
        assertNotNull(a);
        // Create unorganized Record[]
        // adds the record k:81, v:1
        ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(1);
        rec.putDouble(81);
        Record firstRecord = new Record(rec.array());
        // adds the record k:17, v:2
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(2);
        rec.putDouble(17);
        Record secondRecord = new Record(rec.array());
        // adds the record k:24, v:3
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(3);
        rec.putDouble(24);
        Record thirdRecord = new Record(rec.array());
        // adds the record k:13, v:4
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(4);
        rec.putDouble(13);
        Record fourthRecord = new Record(rec.array());

        // Record[] == {81, 17, 24, 13}
        r[0] = firstRecord;
        r[1] = secondRecord;
        r[2] = thirdRecord;
        r[3] = fourthRecord;
        MinHeap<Record> testHeap = new MinHeap<Record>(r, 4, numRecords);
        assertEquals(fourthRecord, testHeap.removeMin());

    }


    /**
     * Tests the remove method of the MinHeap when assertion errors are thrown.
     * This happens when either 2 things occur: An attempt is made to remove a
     * Record from a position that is less than 1 OR an attempt is made to
     * remove from a position that is beyond the size of the actual MinHeap
     */
    public void testRemoveAssertionErrors() {
        AssertionError a = null;
        try {
            mh.remove(-1);
        }
        catch (AssertionError e) {
            a = e;
        }
        assertNotNull("Invalid heap position: position must be 0 or more", a);
        a = null;
        try {
            mh.remove(1);
        }
        catch (AssertionError e) {
            a = e;
        }
        assertNotNull("Invalid heap position: position must be less than " + 1,
            a);
    }


    /**
     * Tests the remove method when all of the contents of the MinHeap are
     * removed from the smallest element to the the biggest one
     */
    public void testRemove() {
        // Create unorganized Record[]
        // adds the record k:81, v:1
        ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(1);
        rec.putDouble(81);
        Record firstRecord = new Record(rec.array());
        // adds the record k:17, v:2
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(2);
        rec.putDouble(17);
        Record secondRecord = new Record(rec.array());
        // adds the record k:24, v:3
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(3);
        rec.putDouble(24);
        Record thirdRecord = new Record(rec.array());
        // adds the record k:13, v:4
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(4);
        rec.putDouble(13);
        Record fourthRecord = new Record(rec.array());

        // Record[] == {81, 17, 24, 13}
        r[0] = firstRecord;
        r[1] = secondRecord;
        r[2] = thirdRecord;
        r[3] = fourthRecord;
        MinHeap<Record> testHeap = new MinHeap<Record>(r, 4, numRecords);
        assertEquals(fourthRecord, testHeap.remove(0));
        assertEquals(secondRecord, testHeap.remove(0));
        assertEquals(thirdRecord, testHeap.remove(0));
        assertEquals(firstRecord, testHeap.remove(0));
        assertEquals(0, testHeap.heapSize());
    }


    /**
     * Tests the modify method for a MinHeap when assertion errors are thrown.
     * This happens when either 2 things occur: An attempt is made to modify a
     * Record from a position that is less than 1 OR an attempt is made to
     * modify from a position that is beyond the size of the actual MinHeap
     */
    public void testModifyAssertionErrors() {
        AssertionError a = null;
        ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(6);
        rec.putDouble(7);
        Record modRec = new Record(rec.array());
        try {
            mh.modify(-1, modRec);
        }
        catch (AssertionError e) {
            a = e;
        }
        assertNotNull("Invalid heap position: position must be 0 or more", a);
        a = null;
        try {
            mh.modify(100, modRec);
        }
        catch (AssertionError e) {
            a = e;
        }
        assertNotNull("Invalid heap position: position must be less than " + 1,
            a);
    }


    /**
     * Tests the modify method when we attempt to modify the contents of a
     * MinHeap
     */
    public void testModify() {
        // Create unorganized Record[]
        // adds the record k:81, v:1
        ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(1);
        rec.putDouble(81);
        Record firstRecord = new Record(rec.array());
        // adds the record k:17, v:2
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(2);
        rec.putDouble(17);
        Record secondRecord = new Record(rec.array());
        // adds the record k:24, v:3
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(3);
        rec.putDouble(24);
        Record thirdRecord = new Record(rec.array());
        // adds the record k:13, v:4
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(4);
        rec.putDouble(13);
        Record fourthRecord = new Record(rec.array());

        // Record[] == {81, 17, 24, 13}
        r[0] = firstRecord;
        r[1] = secondRecord;
        r[2] = thirdRecord;
        r[3] = fourthRecord;
        MinHeap<Record> testHeap = new MinHeap<Record>(r, 4, numRecords);

        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(6);
        rec.putDouble(7);
        Record modRec = new Record(rec.array());
        // changes the root of the MinHeap to be k:7, v:6
        testHeap.modify(0, modRec);
        assertEquals(modRec, testHeap.remove(0));
    }


    /**
     * Testing part of the replacement selection in which we have a file of 8 or
     * more blocks and are attempting to fill the minheap with as much records
     * as we can. The main thing being tested here is if the minheap retains its
     * status as a minheap by ensuring parents are always greater than the
     * children
     */
    public void testMinHeapFilling() {
        Double[] d = new Double[10];
        MinHeap<Double> mhd = new MinHeap<Double>(d, 0, d.length);
        mhd.insert(6.0);
        mhd.insert(5.0);
        mhd.insert(2.0);
        mhd.insert(1.0);
        mhd.insert(4.0);
        mhd.insert(3.0);
        mhd.insert(0.0);
        assertEquals(0.0, mhd.getRoot(), 0.01);
        for (int i = 0; i < mhd.heapSize(); i++) {
            if (!mhd.isLeaf(i)) {
                double parent = d[i];
                assertTrue(parent < d[MinHeap.leftChild(i)]);
                assertTrue(parent < d[MinHeap.rightChild(i)]);
            }
        }
    }


    /**
     * tests the removeMinNoUpdate method, which basically removes the minimum
     * value of a minheap but it doesn't make an adjustment for the empty root
     */
    public void testRemoveMinNoUpdate() {
        Double[] d = new Double[10];
        MinHeap<Double> mhd = new MinHeap<Double>(d, 0, d.length);
        AssertionError a = null;
        try {
            mhd.removeMinNoUpdate();
        }
        catch (AssertionError e) {
            a = e;
        }
        assertNotNull("Heap is empty; cannot remove", a);
        mhd.insert(6.0);
        mhd.insert(5.0);
        mhd.insert(2.0);
        mhd.insert(1.0);
        mhd.insert(4.0);
        mhd.insert(3.0);
        mhd.insert(0.0);
        assertEquals(7, mhd.heapSize());
        assertEquals(0.0, mhd.getRoot(), 0.01);
        assertEquals(0.0, mhd.removeMinNoUpdate(), 0.01);
        assertNull(mhd.getRoot());
    }


    /**
     * tests the removeMin method when there is case where there is 1 item left
     * so there is no need to sift down after removal
     */
    public void testRemoveMinEdgeCase() {
        Double[] d = new Double[7];
        MinHeap<Double> mhd = new MinHeap<Double>(d, 0, d.length);
        mhd.insert(6.0);
        mhd.insert(5.0);
        mhd.insert(2.0);
        mhd.insert(1.0);
        mhd.insert(4.0);
        mhd.insert(3.0);
        mhd.insert(0.0);
        int hSize = mhd.heapSize();
        assertEquals(7, hSize);
        for (int i = 0; i < hSize - 1; i++) {
            mhd.removeMin();
        }
        assertEquals(mhd.getRoot(), mhd.removeMin(), 0.01);
    }


    /**
     * Tests the isLeaf method
     */
    public void testIsLeaf() {
        Double[] d = new Double[7];
        MinHeap<Double> mhd = new MinHeap<Double>(d, 0, d.length);
        mhd.insert(6.0);
        mhd.insert(5.0);
        mhd.insert(2.0);
        mhd.insert(1.0);
        mhd.insert(4.0);
        mhd.insert(3.0);
        mhd.insert(0.0);
        int hSize = mhd.heapSize();
        assertEquals(7, hSize);
        assertFalse(mhd.isLeaf(0));
        assertFalse(mhd.isLeaf(1));
        assertFalse(mhd.isLeaf(2));
        assertTrue(mhd.isLeaf(3));
        assertTrue(mhd.isLeaf(4));
        assertTrue(mhd.isLeaf(5));
        assertTrue(mhd.isLeaf(6));
        assertFalse(mhd.isLeaf(7));
    }


    /**
     * Tests assertion errors for the method siftDown(). One error occurs if the
     * position called is greater than or equal to the size of the minheap.
     * Another error occurs when the position called for a sift is less than 0
     * 
     */
    public void testSiftDownErrors() {
        Double[] d = new Double[7];
        MinHeap<Double> mhd = new MinHeap<Double>(d, 0, d.length);
        AssertionError a = null;
        try {
            mhd.siftDown(0);
        }
        catch (AssertionError e) {
            a = e;
        }
        assertNotNull(a);
        a = null;
        try {
            mhd.siftDown(-1);
        }
        catch (AssertionError e) {
            a = e;
        }
        assertNotNull(a);
    }


    /*
     * tests the siftUp method when assertions are thrown
     */
    public void testSiftUpErrors() {
        Double[] d = new Double[7];
        MinHeap<Double> mhd = new MinHeap<Double>(d, 0, d.length);
        AssertionError a = null;
        try {
            mhd.siftUp(0);
        }
        catch (AssertionError e) {
            a = e;
        }
        assertNotNull(a);
        a = null;
        try {
            mhd.siftUp(-1);
        }
        catch (AssertionError e) {
            a = e;
        }
        assertNotNull(a);
    }


    /**
     * Tests the replacementSelectionInsert. If there is an element in the
     * root of the minheap that is not null, then false is returned because it
     * basically didn't pass the precondition of having removeMinNoUpdate
     * called. If it is called and you do not want to deactivate the element
     * being inserted, then it is simply inserted at the root and sifted down to
     * the correct position and true is returned. In the case where you do
     * deactivate the inserted element, then you call swap and deactivate.
     */
    public void testReplacementSelectionInsert() {
        Double[] d = new Double[7];
        MinHeap<Double> mhd = new MinHeap<Double>(d, 0, d.length);
        mhd.insert(6.0);
        mhd.insert(5.0);
        mhd.insert(2.0);
        mhd.insert(1.0);
        mhd.insert(4.0);
        mhd.insert(3.0);
        mhd.insert(0.0);
        assertFalse(mhd.replacementSelectionInsert(0.0, false));
        mhd.removeMinNoUpdate();
        assertTrue(mhd.replacementSelectionInsert(7.0, false));
        assertEquals(7, mhd.heapSize());
        assertEquals(1, d[0], 0.01);
        assertEquals(7, d[mhd.heapSize() - 1], 0.01);
        assertEquals(0, mhd.getDeactiveSize());
        mhd.removeMinNoUpdate();
        assertTrue(mhd.replacementSelectionInsert(3.5, true));
        assertEquals(1, mhd.getDeactiveSize());
        assertEquals(6, mhd.heapSize());
    }


    /**
     * tests the reactivate method
     */
    public void testReactivate() {
        Double[] d = new Double[7];
        MinHeap<Double> mhd = new MinHeap<Double>(d, 0, d.length);
        mhd.insert(6.0);
        mhd.insert(5.0);
        mhd.insert(2.0);
        mhd.insert(1.0);
        mhd.insert(4.0);
        mhd.insert(3.0);
        mhd.insert(0.0);
        assertEquals(7, mhd.heapSize());
        assertFalse(mhd.reactivate());
        mhd.removeMinNoUpdate();
        assertTrue(mhd.replacementSelectionInsert(3.5, true));
        assertEquals(1, mhd.getDeactiveSize());
        assertEquals(6, mhd.heapSize());
        mhd.removeMinNoUpdate();
        assertTrue(mhd.replacementSelectionInsert(3.5, true));
        assertEquals(2, mhd.getDeactiveSize());
        assertEquals(5, mhd.heapSize());
        mhd.removeMinNoUpdate();
        assertTrue(mhd.replacementSelectionInsert(3.5, true));
        assertEquals(3, mhd.getDeactiveSize());
        assertEquals(4, mhd.heapSize());
        // NEEDS TO BE REWORKED
        assertTrue(mhd.reactivate());
        assertEquals(0, mhd.getDeactiveSize());
        assertEquals(7, mhd.heapSize());
    }
}
