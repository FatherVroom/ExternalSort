import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * Able to go through the blocks of bytes (which can be
 * thought of as an array of bytes) and read records inside the block. Each
 * block contains 512 records and each record is 16 bytes of memory
 * 
 * @author Aniket Adhikari, Chris Koehler
 * @version 2 April 2022
 *
 */
public class Parser {
    private static final int BLOCK_SIZE = 8192;
    private String fileName;
    private RandomAccessFile raf;

    /**
     * Creates a Parser object, which is meant to go through fileName
     * 
     * @param fileName
     *            the name of the file that is meant to be found
     * @throws FileNotFoundException
     *             if the file we want to parse doesn't exist in our folder
     */
    public Parser(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        try {
            raf = new RandomAccessFile(fileName, "r");
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find the file: "
                + fileName);
        }
// mhRecords = new MinHeap<Record>(null, 0, NUM_RECORDS * NUM_BLOCKS);
    }


    /**
     * Takes the binary input file and constructs a byte array the size of a
     * single block, which is 8192 bytes, and 512 "records" or key-value pairs
     * 
     * @return a block of data as a byte array of size 8192
     * @throws IOException
     *             when EOF has been reached
     */
    public byte[] getBlock() throws IOException {
        // allocate space to create bytebuffer
        ByteBuffer block = ByteBuffer.allocate(BLOCK_SIZE);
        try {
            // fill byte buffer with contents of 1st block from fileName.bin
            for (int i = 1; i <= BLOCK_SIZE; i++) {
                // reads a byte from the file of data blocks
                byte b = raf.readByte();
                // places byte into bytebuffer
                block.put(b);
            }
        }
        catch (EOFException e) {
            throw new EOFException("End of the file titled, " + fileName
                + ", has been reached. File must have at least 1 block of records (512 records).");
        }
        // returns block array after conversion from bytebuffer to byte array
        return block.array();
    }


    /**
     * Gets the number of blocks to be parsed through in a file
     * 
     * @return
     * @throws IOException
     */
    private long getNumOfBlocks() throws IOException {
        try {
            return raf.length() / BLOCK_SIZE;
        }
        catch (IOException e) {
            throw new IOException("EndOfFile");
        }
    }
}
