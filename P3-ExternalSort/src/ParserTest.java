import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import student.TestCase;

/**
 * Tests the Parser class
 * 
 * @author Aniket Adhikari
 * @version 2 April 2022
 *
 */
public class ParserTest extends TestCase {
    private Parser p;
    private String numOfBlocks;
    private String fileName;
    private String args[];

    /**
     * 
     */
    public void setUp() {
        fileName = "binaryInputTest.bin";
        try {
            p = new Parser(fileName);
        }
        catch (FileNotFoundException e) {
            return;
        }
        numOfBlocks = "1";
        args = new String[2];
        args[0] = fileName;
        args[1] = numOfBlocks;
    }


    /**
     * tests the exceptions that come up with the main method in the Parser
     * class
     */
    public void testConstructorExceptions() {
        Exception d = null;
        try {
            Parser fp = new Parser("FakeSource.bin");
        }
        catch (Exception e) {
            d = e;
        }
        assertNotNull(d);
        assertEquals("Could not find the file: FakeSource.bin", d.getMessage());
    }


    /**
     * Tests that the position of the file pointer is in the right place
     */
    public void testGetBlock() {
        GenBinaryDataFile.main(args);
        byte b[] = null;
        try {
            b = p.getIBuffer();
        }
        catch (IOException e) {
            return;
        }
        assertEquals(8192, b.length);
    }


    /**
     * Tests the getBlock method when an exception is thrown
     */
    public void testGetBlockException() {
        File shortFile = new File("shortBinary.bin");
        Exception d = null;
        try {
            RandomAccessFile rafTest = new RandomAccessFile(shortFile.getName(), "rw");
            rafTest.writeDouble(0);
            Parser badParser = new Parser(shortFile.getName());
            badParser.getIBuffer();
        }
        catch (IOException e) {
            d = e;
        }
        assertNotNull(d);
        assertEquals("End of the file titled, shortBinary.bin, has been reached", d.getMessage());
    }
    
    public void testNumOfBlocks() {
        GenBinaryDataFile.main(args);
        try {
            assertEquals(1, p.numOfBlocks());
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
