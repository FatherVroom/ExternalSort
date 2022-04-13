import java.io.EOFException;
import java.io.File;
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
    private static final int NUM_RECORDS = 512;
    private static final int MAX_HEAP_SIZE = 4096;
    private static final int MIN_BLOCKS = 8;
    private String fileName;
    private RandomAccessFile raf;
    private int currentPos;
    private int runCount;

    /**
     * Creates a Parser object, which is meant to go through fileName
     * 
     * @param fileName
     *            the name of the file that is meant to be found
     * @throws IOException
     */
    public Parser(String fileName) throws IOException {
        this.fileName = fileName;
        // current position begins at 0, and will be incremented by 8191 each
        // time getNextByteBlock()() is called on the parser
        currentPos = 0;
        try {
            raf = new RandomAccessFile(fileName, "r");
            // start the RandomAccessFile at the beginning of the file
            raf.seek(currentPos);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find the file: "
                + fileName);
        }
        catch (IOException e) {
            throw new IOException("Seeking out of bounds of this file");
        }
    }


    /**
     * Takes the binary input file and constructs a byte array the size of a
     * single block, which is 8192 bytes, and 512 "records" or key-value pairs
     * 
     * @return a block of data as a byte array of size 8192
     * @throws IOException
     *             when EOF has been reached
     */
    public byte[] getNextByteBlock() throws IOException {
        // allocate space to create bytebuffer
        ByteBuffer block = ByteBuffer.allocate(BLOCK_SIZE);
        try {
            // fill byte buffer with contents of 1st block from fileName.bin
            for (int i = 0; i < BLOCK_SIZE; i++) {
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
        // increment the current positon by 8191 so that each time
        // getNextByteBlock()() is
        // called, we move the RandomAccessFile over to the next block
        int blockIncrease = BLOCK_SIZE - 1;
        currentPos += blockIncrease;
        raf.seek(currentPos);

        // returns block array after conversion from bytebuffer to byte array
        return block.array();
    }


    /**
     * Performs replacement selection. Performed through the following steps:
     * 1. Fill the MinHeap to capacity, which is 4096 records
     * 2. Immediately the root of the MinHeap (AKA the minimum record) and put
     * it into the OutputBuffer
     * 3. Look at the latest InputBuffer record Ri and place that record at the
     * root of the MinHeap. Compare Ri to the latest OutputBuffer record Ro
     * 3a. If Ri >= Ro, place Ri at the root of MinHeap, sift down, and place
     * the minimum record (AKA root) into OutputBuffer
     * 3b. If Ri < Ro, place Ri at the root of the MinHeap, swap with the last
     * element in the MinHeap, decrement heap size, and place the minimum record
     * into the OutputBuffer
     * 
     * @throws IOException
     */
    public boolean replacementSelection() throws IOException {

        // PHASE 1: Fill 8 Blocks into MinHeap
        InputBuffer inBuf = null;
        Record[] heapArray = new Record[MAX_HEAP_SIZE];
        MinHeap<Record> mh = new MinHeap<Record>(heapArray, 0, MAX_HEAP_SIZE);

        // Get block with input buffer, convert to Records, add to heapArray
        // Maybe replace with helper method called fillHeapArray(InputBuffer)
        for (int i = 0; i < MIN_BLOCKS; i++) {
            inBuf = new InputBuffer(getNextByteBlock());
            inBuf.fillRecords();
            Record[] blockRecords = inBuf.getRecords();
            for (int j = 0; j < NUM_RECORDS; j++) {
                mh.insert(blockRecords[j]);
            }
        }
        // organize heap
        mh.buildHeap();

        // PHASE 2: Replacement selection
        // Create Run File and Output Buffer
        File runFile = new File("runFile.bin");
// runFile.createNewFile();
        RandomAccessFile runRaf = new RandomAccessFile(runFile, "rw");
        OutputBuffer outBuf = new OutputBuffer();

        // CASE: Input File <= 8 Blocks, so just write from the heap
        if (getNumOfBlocks() <= 8.0) {
            while (mh.heapSize() >= 1) {
                // Flush output buffer if necessary
                if (outBuf.isFull()) {
                    outBuf.writeToRunFile(runRaf);
                    outBuf = new OutputBuffer();
                }
                // Write next minimum from Heap to Output Buffer

                outBuf.addRecord(mh.removeMin());
            }
            // runFile should now be sorted, calling code will rename file and
            // terminate program
            outBuf.writeToRunFile(runRaf);
            if (isSorted(runRaf) == 0) {
                return true;
            }

        }

        // CASE: Input File >= 8 Blocks, replacement selection necessary
        // Refill input buffer with next block (9th block)
        inBuf = new InputBuffer(getNextByteBlock());
        inBuf.fillRecords();
        Record[] inBufRecords = inBuf.getRecords();
        int inBufIndex = 0; // Index of the next record to be inserted into heap

        // Send first Record to outputBuffer, leaving root empty (null)
        Record removedRec = mh.removeMinNoUpdate();
        outBuf.addRecord(removedRec);

        // Loop replacement selection until InputBuffer cannot receive
        // more input
        // TODO: Make this while loop terminate when getNextByteBlock()()
        // exception thrown
        // (this works but might as well)
        long blockCounter = 8; // Already processed first 8 in MinHeap
        long totalBlocks = getNumOfBlocks();

        // While there are more blocks in input file, continue
        while (blockCounter != totalBlocks) {
            // If at end of inBuf Record array, refill inBuf w/ next block
            if (inBufIndex == NUM_RECORDS) {
                inBuf = new InputBuffer(getNextByteBlock());
                inBuf.fillRecords();
                inBufRecords = inBuf.getRecords();
                inBufIndex = 0;
                blockCounter++;
            }
            // Perform replacement selection on current block
            while (inBufIndex < NUM_RECORDS) {
                // Flush output buffer if necessary
                if (outBuf.isFull()) {
                    outBuf.writeToRunFile(runRaf);
                    outBuf = new OutputBuffer();
                }

                // Reactivate Heap if empty
                if (mh.heapSize() == 0) {
                    // If no inactive portion, we are done
                    if (!mh.reactivate()) {
                        return false;
                    }
                }

                // Get next record in input buffer
                Record nextRec = inBufRecords[inBufIndex];

                // Determine whether this next record should be moved to
                // inactive portion of heap
                boolean deactivate = (nextRec.compareTo(removedRec) < 0);

                // Insert nextRec into MinHeap accordingly
                mh.replacementSelectionInsert(nextRec, deactivate);

                // Increment to next record of inBufRecords
                inBufIndex++;

                // Remove root, send to outBuf, repeat process
                removedRec = mh.removeMinNoUpdate();
                outBuf.addRecord(removedRec);
            }
        }

        // PHASE 3: Empty the heap without inserting since inBuf is finished
        // No more blocks in input file, empty active portion of heap
        while (mh.heapSize() != 0) {
            // Flush output buffer if necessary
            if (outBuf.isFull()) {
                outBuf.writeToRunFile(runRaf);
                outBuf = new OutputBuffer();
            }
            // Write next minimum from Heap to Output Buffer
            removedRec = mh.removeMin();
            outBuf.addRecord(removedRec);
        }

        // Reactivate and empty heap if necessary
        if (mh.reactivate()) {
            while (mh.heapSize() != 0) {
                // Flush output buffer if necessary
                if (outBuf.isFull()) {
                    outBuf.writeToRunFile(runRaf);
                    outBuf = new OutputBuffer();
                }
                // Write next minimum from Heap to Output Buffer
                removedRec = mh.removeMin();
                outBuf.addRecord(removedRec);
            }
        }
        return false;
    }


    /**
     * Gets the number of blocks to be parsed through in a file
     * 
     * @return number of blocks that we will be parsing through for sorting
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


    /**
     * Checks if the file passed in as a parameter raf is sorted in ascending
     * order. Meaning, the first thing read from the file is the smallest key
     * value
     * 
     * @param raf
     *            is the run file being checked for sorting
     * @return If the
     * @throws IOException
     */
    public int isSorted(RandomAccessFile raf) throws IOException {
        // go to start of file
        raf.seek(0);

        // stores amount of records to compare
        int count = 0;
        // get a count of how many records to compare and store it in count
        while (raf.read() != -1) {
            raf.seek(raf.getFilePointer() - 1);
            raf.readDouble();
            raf.readLong();
            count++;
        }
        // go to start of file
        raf.seek(0);
        // get a count of how many errors there are in the run
        int errorCount = 0;
        for (int i = 0; i < count - 1; i++) {
            double d = raf.readDouble();
            raf.readLong();
            double compareD = raf.readDouble();
            raf.readLong();
            if (d > compareD) {
                errorCount++;
            }
            raf.seek(raf.getFilePointer() - (Long.BYTES + Double.BYTES));
        }
        return errorCount;
    }

}
