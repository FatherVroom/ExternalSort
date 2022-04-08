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


    public void testHeapSize() {
        assertEquals(0, mh.heapSize());
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
        //Create unorganized Record[]
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
        
        //Record[] == {81, 17, 24, 13}
        r[0] = firstRecord;
        r[1] = secondRecord;
        r[2] = thirdRecord;
        r[3] = fourthRecord;
        MinHeap<Record> testHeap = new MinHeap<Record>(r, 4, 10);
        
        //Ensure Record[] satisfied MinHeap properties
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(1);
        rec.putDouble(81);
        assertTrue(13.0 == r[0].getKey());
        assertTrue(17.0 == r[1].getKey());
        assertTrue(24.0 == r[2].getKey());
        assertTrue(81.0 == r[3].getKey());
    }
}