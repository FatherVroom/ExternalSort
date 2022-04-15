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
    
    public void testOutput8BlockRandom() throws IOException {
        String[] args = { "ExternalSort8BlockRandom.bin", "8", "random" };
        GenBinaryDataFile.main(args);
        args = new String[1];
        args[0] = "ExternalSort8BlockRandom.bin";
        Parser pc1 = new Parser(args[0]);
        Externalsort.main(args);
        //System.out.println(systemOut().getHistory().length());
    }
    
    public void testOutput32BlockRandom() throws IOException {
        String[] args = { "ExternalSort32BlockRandom.bin", "32", "random" };
        GenBinaryDataFile.main(args);
        args = new String[1];
        args[0] = "ExternalSort32BlockRandom.bin";
        Parser pc1 = new Parser(args[0]);
        Externalsort.main(args);
    }
    
    public void testOutput8BlockSorted() throws IOException {
        String[] args = { "ExternalSort8BlockSorted.bin", "8", "sorted" };
        GenBinaryDataFile.main(args);
        args = new String[1];
        args[0] = "ExternalSort8BlockSorted.bin";
        Parser pc1 = new Parser(args[0]);
        Externalsort.main(args);
    }
    
    public void testOutput32BlockSorted() throws IOException {
        String[] args = { "ExternalSort32BlockSorted.bin", "32", "sorted" };
        GenBinaryDataFile.main(args);
        args = new String[1];
        args[0] = "ExternalSort32BlockSorted.bin";
        Parser pc1 = new Parser(args[0]);
        Externalsort.main(args);
    }
    
    public void testOutput8BlockReverseSorted() throws IOException {
        String[] args = { "ExternalSort8BlockReverseSorted.bin", "8", "reverseSorted" };
        GenBinaryDataFile.main(args);
        args = new String[1];
        args[0] = "ExternalSort8BlockReverseSorted.bin";
        Parser pc1 = new Parser(args[0]);
        Externalsort.main(args);
    }
    
    public void testOutput32BlockReverseSorted() throws IOException {
        String[] args = { "ExternalSort32BlockReverseSorted.bin", "32", "reverseSorted" };
        GenBinaryDataFile.main(args);
        args = new String[1];
        args[0] = "ExternalSort32BlockReverseSorted.bin";
        Parser pc1 = new Parser(args[0]);
        Externalsort.main(args);
    }

}