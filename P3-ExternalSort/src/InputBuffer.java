import java.nio.ByteBuffer;

/**
 * InputBuffer class which takes in a byte array
 * and is meant to convert this byte array into an array of Records of size 512.
 * The input buffer only has space for 512 records, or 1 block of records. These
 * records can be replaced by other blocks.
 * 
 * @author Aniket Adhikari, Chris Koehler
 * @version 6 April 2022
 */
public class InputBuffer {
    private Record[] records;
    private static final int BUFFER_CAPACITY = 8192;
    private static final int NUM_RECORDS = 512;
    private byte[] block;
    private int size;

    /**
     * Constructor for an InputBuffer
     * 
     * @param block
     *            is what is being converted into an array of records
     */
    public InputBuffer(byte[] block) {
        this.block = block;
        size = NUM_RECORDS;
        records = new Record[NUM_RECORDS];
    }


    /**
     * Fills the InputBuffer with an array of Records.
     */
    public void fillRecords() {
        // make bytebuffer out of the block of code that is passed in
        ByteBuffer b = ByteBuffer.wrap(block);
        int recCounter = 0;
        // cycle through the block of 8,192 bytes and convert to record objects
        for (int i = 0; i < BUFFER_CAPACITY; i += 16) {
            byte[] newRecord = new byte[16]; // create byte array to store a
                                             // record
            b.get(newRecord, i, 16); // store block contents into the byte array
                                     // newRecord
            Record r = new Record(newRecord); // store the byte array into an
                                              // actual Record
            records[recCounter] = r; // place the record in the record array
            recCounter++;
        }
    }


    /**
     * Gets the Records in an array format
     * 
     * @precondition fillRecords() has been called on byte[] block
     * 
     * @return records inside the Buffer
     */
    public Record[] getRecords() {
        // make a precondition to ensure record field has been populated
        return records;
    }


    /**
     * Determines whether there is space in the Buffer for blocks of records to
     * be placed inside of
     * 
     * @return true of the InputBuffer is empty
     */
    public boolean isEmpty() {
        // use for when we want to start filling up input buffer again
        return size == 0;
    }
}