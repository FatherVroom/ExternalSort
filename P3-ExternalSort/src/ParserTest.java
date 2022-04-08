import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import student.TestCase;

/**
 * Tests the Parser class
 * 
 * @author Aniket Adhikari, Chris Koehler
 * @version 2 April 2022
 *
 */
public class ParserTest extends TestCase {
    private Parser p;
    private String numOfBlocks;
    private String fileName;
    private String badFileName;
    private String args[];

    /**
     * Sets up the ParserTest so that we have a parser with the file
     * "binaryInputTest.bin", which is a single block of 512 records
     */
    public void setUp() {
        // "binaryInputTest.bin" is the name of the binary data file being
        // created
        fileName = "binaryInputTest.bin";
        badFileName = "FakeSource.bin";
        // "binaryInputTest.bin" will only contain 1 block of data, or 512
        // records
        numOfBlocks = "1";
        args = new String[2];
        args[0] = fileName;
        args[1] = numOfBlocks;
        // creates binaryInputTest.bin, which is 1 block of 512 records
        GenBinaryDataFile.main(args);
        try {
            // creation of Parser that goes through "binaryInputTest.bin"
            p = new Parser(fileName);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace(); // should not execute
        }
    }


    /**
     * tests the exceptions that come up with the main method in the Parser
     * class. Pass in a fake file name so that a FileNotFoundException is
     * thrown.
     */
    public void testConstructorExceptions() {
        Exception d = null;
        String noFile = "asdfghjkl.bin";
        try {
            // creation of a fake file which should result in a
            // FileNotFoundException to be thrown
            new Parser(noFile);
        }
        catch (Exception e) {
            d = e;
        }
        // asserts that there is an exception thrown and it is the right message
        // thrown
        assertNotNull("Could not find the file: " + noFile, d);
    }


    /**
     * Tests the getBlock() method
     */
    public void testGetBlock() {
        byte[] block = null;
        Exception d = null;
        try {
            // creates a byte array of the block in the binary file.
            block = p.getBlock();
        }
        catch (IOException e) {
            e.printStackTrace();
            d = e;
        }
        assertNull(d);
        assertEquals(8192, block.length);
    }


    /**
     * Tests the getBlock() method when an exception is thrown. An exception
     * would be thrown because the file being read in is not actually 8192 bytes
     * long.
     */
    public void testGetBlockException() {
        Exception d = null;
        try {
            // creation of fake file
            File f = new File(badFileName);
            // allows for fake file to be writable
            FileWriter fw = new FileWriter(f);
            // puts in fake file into a parser
            Parser bp = new Parser(badFileName);
            bp.getBlock();
        }
        catch (Exception e) {
            d = e;
        }
        // asserts that there is an exception thrown and it is the right message
        // thrown
        assertNotNull("End of the file titled, " + badFileName
            + ", has been reached. File must have at least 1 block of records (512 records).",
            d);
    }


//    /**
//     * Tests the getNumOfBlocks() method when there is just 1 block of binary
//     * and also when there is 8 blocks of binary
//     */
//    public void testGetNumOfBlocks() {
//        Exception d = null;
//        // creation of a binary file which has 8 blocks of data
//        String[] bigArgs = { "bigFile.bin", "8" };
//        GenBinaryDataFile.main(bigArgs);
//        Parser bigParser = null;
//        // creation of a parser that would go through the 8 block binary file
//        try {
//            bigParser = new Parser("bigFile.bin");
//        }
//        catch (FileNotFoundException e1) {
//            d = e1;
//        }
//        assertNull(d);
//        long blockSize1 = 0;
//        long blockSize8 = 0;
//        try {
//            // gets block size of a file with 1 block
//            blockSize1 = p.getNumOfBlocks();
//            // gets block size of a file with 8 blocks
//            blockSize8 = bigParser.getNumOfBlocks();
//        }
//        catch (IOException e2) {
//            d = e2;
//            e2.printStackTrace();
//        }
//        assertNull(d);
//        assertEquals(1, blockSize1);
//        assertEquals(8, blockSize8);
//    }
    
}
