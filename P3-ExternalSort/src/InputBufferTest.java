import java.io.FileNotFoundException;
import java.io.IOException;
import student.TestCase;

/**
 * Tests the InputBuffer class
 * 
 * @author Aniket Adhikari, Chris Koehler
 * @version 9 April 2022
 *
 */
public class InputBufferTest extends TestCase {
    private Parser p;
    private InputBuffer ib;

    /**
     * Set up for the test
     */
    public void setUp() {
        String args[] = { "sampleInput16.bin", "8" };
        GenBinaryDataFile.main(args);
        try {
            p = new Parser(args[0]);
            ib = new InputBuffer(p.getBlock());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void testFillRecords() {
        assertTrue(ib.isEmpty());
        ib.fillRecords();
        Record[] r = ib.getRecords();
        assertEquals(512, r.length);
        assertFalse(ib.isEmpty());
    }
}
