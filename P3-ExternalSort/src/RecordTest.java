
import java.nio.ByteBuffer;
import student.TestCase;

/**
 * Holds a single record
 * 
 * @author CS Staff, Aniket Adhikari, Chris Koehler
 * @version 2020-10-15
 */
public class RecordTest extends TestCase {

    private byte[] aBite;

    /**
     * The setup for the tests
     */
    public void setUp() {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES + Double.BYTES);
        buffer.putLong(7); // place value of 7 at start of buffer
        buffer.putDouble(8, 2); // place value of 2 after long
        aBite = buffer.array();
    }


    /**
     * Tests the first constructor
     */
    public void testConstruct1() {
        Record rec = new Record(aBite);
        assertEquals((double)2, rec.getKey(), 0.00);
        assertEquals(aBite, rec.getCompleteRecord());
        assertTrue(rec.toString().equals("2.0"));
    }


    /**
     * Tests the first constructor
     */
    public void testCompareTo() {
        Record rec = new Record(aBite);
        Record recToBeCompared = new Record(aBite);
        assertEquals(rec.compareTo(recToBeCompared), 0);
    }


    /**
     * Tests the getValue() method for the Record class
     */
    public void testGetValue() {
        Record rec = new Record(aBite);
        assertEquals((long)7, rec.getValue(), 0.00);
    }


    /**
     * Tests the setKey() method for the Record class
     */
    public void testSetKey() {
        Record rec = new Record(aBite);
        assertEquals((double)2, rec.getKey(), 0.00);
        rec.setKey(45);
        assertEquals((double)45, rec.getKey(), 0.00);
        assertEquals((double)45, ByteBuffer.wrap(rec.getCompleteRecord())
            .getDouble(8), 0.0);
    }


    /**
     * Tests the setValue() method for the Record class
     */
    public void testSetValue() {
        Record rec = new Record(aBite);
        assertEquals((long)7, rec.getValue(), 0.00);
        rec.setValue(95843925);
        assertEquals((long)95843925, rec.getValue(), 0.00);
        assertEquals((long)95843925, ByteBuffer.wrap(rec.getCompleteRecord())
            .getLong(), 0.0);
    }
}