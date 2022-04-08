import student.TestCase;

/**
 * @author Aniket Adhikari, Chris Koehler
 * @version 21 March 2022
 */
public class ExternalsortTest extends TestCase {
    
    
    /**
     * set up for tests
     */
    public void setUp() {
        //nothing to set up.
    }
    
    /**
     * Get code coverage of the class declaration.
     */
    public void testExternalsortInit() {
        Externalsort sorter = new Externalsort();
        assertNotNull(sorter);
        Externalsort.main(null);
    }

}
