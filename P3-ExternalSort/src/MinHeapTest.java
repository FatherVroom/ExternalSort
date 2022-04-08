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
     * 
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
     * 
     */
    public void testInsert() {
        ByteBuffer rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        // placement of record 1
        rec.putLong(1);
        rec.putDouble(81);
        Record firstRecord = new Record(rec.array());
        mh.insert(firstRecord);
        assertEquals(1, mh.heapSize());

        // placement of record 2
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(2);
        rec.putDouble(17);
        Record secondRecord = new Record(rec.array());
        mh.insert(secondRecord);
        assertEquals(2, mh.heapSize());

        // placement of record 3
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(3);
        rec.putDouble(24);
        Record thirdRecord = new Record(rec.array());
        mh.insert(thirdRecord);
        assertEquals(3, mh.heapSize());

        // placement of record 4
        rec = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
        rec.putLong(4);
        rec.putDouble(13);
        Record fourthRecord = new Record(rec.array());
        mh.insert(fourthRecord);
        assertEquals(4, mh.heapSize());
    }
}
