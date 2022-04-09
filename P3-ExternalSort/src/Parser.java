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
     * 
     * 
     * @throws IOException
     */
//    public void replacementSelectionOLD() throws IOException {
//        //PHASE 1: Putting very first block into MinHeap
//        
//        //Read first block, place into inputBuffer
//        byte[] nextBlock = getBlock();
//        InputBuffer inBuf = new InputBuffer(nextBlock);
//        //Convert bytes of Input Buffer to Records
//        inBuf.fillRecords();
//        //Fill MinHeap with this first block of records in Input Buffer
//        MinHeap<Record> recHeap = new MinHeap<Record>(inBuf.getRecords(), 
//            NUM_RECORDS, NUM_RECORDS);
//        
//        //PHASE 2: Replacement Selection
//        
//        //Open a new File to write the runs to, create OutputBuffer
//        File runFile = new File("runFile.bin");
//        runFile.createNewFile();
//        RandomAccessFile runRaf = new RandomAccessFile(runFile, "rw");
//        OutputBuffer outBuf = new OutputBuffer();
//        //Reinitialize Input Buffer with next block of input file
//        nextBlock = getBlock();
//        inBuf = new InputBuffer(nextBlock);
//        //Convert bytes of Input Buffer to Records, store reference
//        inBuf.fillRecords();
//        Record[] inBufRecords = inBuf.getRecords();
//        int currRecord = 0;
//        //Send first Record (root) to output buffer
//        Record removedRec = recHeap.removeMin();
//        outBuf.addRecord(removedRec);
//        //Perform Replacement Selection until block 8 has been finished or E.O.F.
//        while ((raf.getFilePointer() != BLOCK_SIZE * 8) || 
//            (raf.getFilePointer() != raf.length())) {
//            
//            //If Input Buffer is at end, refill it
//            if (currRecord == NUM_RECORDS) {
//                //Reinitialize Input Buffer with next block of input file
//                nextBlock = getBlock();
//                inBuf = new InputBuffer(nextBlock);
//                //Convert bytes of Input Buffer to Records, store reference
//                inBuf.fillRecords();
//                inBufRecords = inBuf.getRecords();
//                currRecord = 0;
//            }
//            
//            //If next Record >= last Record removed, insert as normal
//            if (inBufRecords[currRecord].compareTo(removedRec) >= 0) {
//                recHeap.insert(inBufRecords[currRecord]);
//                currRecord++;
//            }
//            
//            //Else, insert next Record and deactivate it
//            else {
//                recHeap.insertAndDeactivate(inBufRecords[currRecord]);
//                currRecord++;
//            }
//            
//            
//        }
//    }
    
    public void replacementSelection() throws IOException {
        //PHASE 1: Fill 8 Blocks into MinHeap, create 8 block run
        byte[] nextBlock;
        InputBuffer inBuf;
        Record[] heapArray = new Record[MAX_HEAP_SIZE];
        int heapArrayIndex = 0;
        
        //Get block with input buffer, convert to Records, add to heapArray
        for (int i = 0; i < 8; i++) {
            nextBlock = getBlock();
            inBuf = new InputBuffer(nextBlock);
            inBuf.fillRecords();
            Record[] blockRecords = inBuf.getRecords();
            for (int j = 0; j < NUM_RECORDS; j++) {
                heapArray[heapArrayIndex] = blockRecords[j];
                heapArrayIndex++;
            }
        }
        
        //heapArray populated with 8 blocks, create MinHeap with it
        MinHeap<Record> recHeap = new MinHeap<Record>(heapArray, 
            MAX_HEAP_SIZE, MAX_HEAP_SIZE);
        recHeap.buildHeap();
        
        //Create Run File and Output Buffer
        File runFile = new File("runFile.bin");
        runFile.createNewFile();
        RandomAccessFile runRaf = new RandomAccessFile(runFile, "rw");
        OutputBuffer outBuf = new OutputBuffer();
        
        //CASE: Input File <= 8 Blocks, so just write from the heap
        if (getNumOfBlocks() <= 8.0) {
            while (recHeap.heapSize() != 0) {
                //Flush output buffer if necessary
                if (outBuf.isFull()) {
                    outBuf.writeToRunFile(runRaf);
                    outBuf = new OutputBuffer();
                }
                //Write next minimum from Heap to Output Buffer
                Record removedRec = recHeap.removeMin();
                outBuf.addRecord(removedRec);
            }
            //runFile should be sorted, calling code will rename file and 
            //terminate program
            return;
        }
        
        //CASE: Input File >= 8 Blocks, replacement selection necessary
        else {
            //Implement replacement selection here
        }
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