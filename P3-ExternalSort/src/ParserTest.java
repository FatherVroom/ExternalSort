import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
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
    private String genType;
    private String args[];

    /**
     * Sets up the ParserTest so that we have a parser with the file
     * "binaryInputTest.bin", which is a single block of 512 records
     * 
     * @throws IOException
     */
    public void setUp() throws IOException {
        // "binaryInputTest.bin" is the name of the binary data file being
        // created
        fileName = "binaryInputTest.bin";
        badFileName = "FakeSource.bin";
        genType = "random";
        // "binaryInputTest.bin" will only contain 1 block of data, or 512
        // records
        numOfBlocks = "8";
        args = new String[3];
        args[0] = fileName;
        args[1] = numOfBlocks;
        args[2] = genType;
        // creates binaryInputTest.bin, which is 1 block of 512 records
        GenBinaryDataFile.main(args);
        // creation of Parser that goes through "binaryInputTest.bin"
        p = new Parser(fileName);
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
        byte[] block2 = null;
        Exception d = null;
        try {
            // creates a byte array of the block in the binary file.
            block = p.getNextByteBlock();
            block2 = p.getNextByteBlock();
        }
        catch (IOException e) {
            d = e;
        }
        assertNull(d);
        assertEquals(8192, block.length);
        assertEquals(8192, block2.length);
        assertNotSame(block, block2);

    }


    public void testGetBlockAgain() throws IOException {
        for (int i = 0; i < 8; i++) {
            p.getNextByteBlock();
        }
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
            bp.getNextByteBlock();
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


    /**
     * Tests the replacementSelection method in Parser in a case where there is
     * 8 blocks of data or less to sort. This should be as simple as getting the
     * minimum record from the top of the heap and putting it into an output
     * buffer
     * 
     * @throws IOException
     *             for something
     */
    public void testReplacementSelectionCaseOne() throws IOException {
        String[] args = { "caseOneFile.bin", "8", "random" };
        GenBinaryDataFile.main(args);
        Parser pc1 = new Parser(args[0]);
        assertTrue(pc1.replacementSelection());
        RandomAccessFile raf = new RandomAccessFile(args[0], "r");
        assertEquals(0, pc1.numErrors(raf));

    }
    
    public void testReplacementSelectionCaseOneOrderly() throws IOException {
        String[] args = { "caseOneFile.bin", "8", "reverseSorted" };
        GenBinaryDataFile.main(args);
        Parser pc1 = new Parser(args[0]);
        assertTrue(pc1.replacementSelection());
        //RandomAccessFile raf = new RandomAccessFile(args[0], "r");
        //assertEquals(0, pc1.numErrors(raf));

    }


    /**
     * tests replacement
     * 
     * @throws IOException
     */
    public void testReplacementSelectionCaseTwo() throws IOException {
        String[] args = { "caseOneFile.bin", "32", "random" };
        GenBinaryDataFile.main(args);
        Parser pc1 = new Parser(args[0]);

        pc1.replacementSelection();

    }


    /**
     * Tests the isSorted method when a file is already in order. meaning it is
     * in ascending order and thus needs no sorting
     * 
     * @throws IOException
     *             for something
     */
    public void testIsSortedAlready() throws IOException {
        String[] args = { "sortedBinary.bin", "8", "sorted" };
        GenBinaryDataFile.main(args);
        Parser pc1 = new Parser(args[0]);
        RandomAccessFile raf = new RandomAccessFile(args[0], "r");
        assertEquals(0, pc1.numErrors(raf));
    }


    /**
     * Tests the isSorted method when a file is completely reversed, meaning it
     * is in descending order and thus is completely out of order
     * 
     * @throws IOException
     *             for something
     */
    public void testIsSortedReversed() throws IOException {
        String[] args = { "unsortedBinary.bin", "32", "reverseSorted" };
        GenBinaryDataFile.main(args);
        Parser pc1 = new Parser(args[0]);
        RandomAccessFile raf = new RandomAccessFile("unsortedBinary.bin", "r");
        assertEquals(16352, pc1.numErrors(raf));
    }

}