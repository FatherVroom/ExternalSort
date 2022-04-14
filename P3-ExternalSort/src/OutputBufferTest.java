import java.io.IOException;
import java.nio.ByteBuffer;
import student.TestCase;

public class OutputBufferTest extends TestCase {

    private OutputBuffer outBuff;
    private Record[] r;
    private int rSize;

    /**
     * runs everytime there is a test run
     */
    public void setUp() {
        outBuff = new OutputBuffer();
        rSize = 512;
        r = new Record[rSize];

    }


    /**
     * Tests the addRecord method by filling the output buffer to capacity.
     * Checks are made each time a record is added and then a final check is
     * done to make sure false is returned in the instance a record is added
     * when the output buffer is filled
     */
    public void testAddRecord() {
        assertFalse(outBuff.isFull());
        double k = 1;
        long v = 1;
        // fill a Record[] with records
        for (int i = 0; i < rSize; i++) {
            k = k + 0.01 * i; // increase key
            v = v + 10 * i; // increase value
            assertTrue(outBuff.addRecord(new Record(k, v)));
        }
        assertTrue(outBuff.isFull());
        assertFalse(outBuff.addRecord(new Record(0.0, 10394203)));
    }


    /**
     * Tests that the convertRecsToByteForm method converts the records added
     * into byte array format.
     * 
     * @throws IOException
     *             occurs if the file can't be found
     */
    public void testConvertRecsToByteForm() throws IOException {
        // creation of a new binary file called binaryInputTest.bin
        String[] args = { "binaryInputTest.bin", "8", "random" };
        GenBinaryDataFile.main(args);
        Parser p = new Parser(args[0]);
        // convert binary to bytes
        byte[] originalBytes = p.getNextByteBlock();
        InputBuffer inBuff = new InputBuffer(originalBytes);
        // convert bytes to records
        inBuff.fillRecords();
        Record[] originalRecords = inBuff.getRecords();
        // fills output buffer with records
        for (int i = 0; i < originalRecords.length; i++) {
            outBuff.addRecord(originalRecords[i]);
        }
        // makes sure output buffer is filled
        assertTrue(outBuff.isFull());
        // converts records in output buffer back into byte form
        byte[] b = outBuff.convertRecsToByteForm();
        // ensures conversion got ALL of the records converted
        assertEquals(8192, b.length);
        // make sure the original byte array matches the converted one
        for (int i = 0; i < b.length; i++) {
            assertEquals(b[i], originalBytes[i]);
        }
    }
    
    public void testConvertRecsToByteFormNew() {
        //Populate minHeap with 8 blocks of Records
        Record[] heapArray = new Record[4096];
        double d = 4096.0;
        for (int i = 0; i < 4096; i++) {
            ByteBuffer recBuf = ByteBuffer.allocate(Double.BYTES + Long.BYTES);
            recBuf.putLong(i);
            recBuf.putDouble(d);
            Record r = new Record(recBuf.array());
            heapArray[i] = r;
            d -= 1.0;
        }
        MinHeap<Record> mhr = new MinHeap<Record>(heapArray, 4096, 4096);
        
        //Populate outputBuffer with one block
        OutputBuffer ob = new OutputBuffer();
        while (!ob.isFull()) {
            ob.addRecord(mhr.removeMin());
        }
        
        //Confirm that records of output buffer are in correct order
        Record[] obRecs = ob.getRecords();
        for (int i = 0; i < 512 - 1; i++) {
            assertTrue(obRecs[i].compareTo(obRecs[i + 1]) <= 0);
        }
        
        //Check that conversion to byte form is working properly
        //Old convertRecsToByteForm
        byte[] thisRec = new byte[16];
        byte[] blockRecs = new byte[8192];
        long expectedID = 4095;
        double expectedKey = 1.0;
        blockRecs = ob.convertRecsToByteForm();
        ByteBuffer bb = ByteBuffer.wrap(blockRecs);
        for (int i = 0; i < 512; i++) {
            assertEquals(expectedID, bb.getLong());
            assertEquals(expectedKey, bb.getDouble(), 0.01);
            expectedID--;
            expectedKey += 1.0;
        }
    }

// public void testWriteToRunFile() {
//
// }
//
//
// public void testIsFull() {
//
// }
}