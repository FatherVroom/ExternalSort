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
}
