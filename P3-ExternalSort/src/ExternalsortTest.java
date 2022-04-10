import java.io.FileNotFoundException;
import java.io.IOException;
import student.TestCase;

/**
 * @author Aniket Adhikari, Chris Koehler
 * @version 21 March 2022
 */
public class ExternalsortTest extends TestCase {
    private String[] args;

    /**
     * set up for tests
     */
    public void setUp() {
        args = new String[2];
        args[0] = "sampleInput16.bin";
        args[1] = "8";
    }
    
    public void testExternalSortException() {
        Exception d = null;
        String[] fakeArgs = { "FakeFile.bin" };
        Externalsort sorter = new Externalsort();
        assertNotNull(sorter);
        try {
            Externalsort.main(fakeArgs);
        }
        catch (IOException e) {
            d = e;
        }
        assertNotNull(d);
    }


    /**
     * Get code coverage of the class declaration.
     */
    public void testExternalsortInit() {
        
    }

}
