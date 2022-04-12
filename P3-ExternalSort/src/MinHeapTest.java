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
     * Tests the isLeaf method
     */
    public void testIsLeaf() {

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
}
