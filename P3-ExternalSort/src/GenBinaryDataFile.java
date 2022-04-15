// WARNING: This program uses the Assertion class. When it is run,
// assertions must be turned on. For example, under Linux, use:
// java -ea GenBinaryDataFile

/**
 * Generate a data file. The size is a multiple of 8192 bytes.
 * Each record is one long and one double.
 */

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Generates binary data files representing Records (long ID followed by
 * double key) according to specified size (in blocks of 512 records) and
 * type (random, sorted min to max, or reverse sorted max to min.
 * 
 * @author CS Staff, Aniket Adhikari, Chris Koehler
 * @version 2022-04-14
 */
public class GenBinaryDataFile {

    /** Number of records per block */
    static final int NumRecs = 512; // Because they are short ints

    /** Initialize the random variable */
    static private Random value = new Random(); // Hold the Random class object

    /**
     * Generates a random long value
     * 
     * @return Random long value
     */
    static long randLong() {
        return value.nextLong();
    }

    /**
     * Generates a random double value
     * 
     * @return Random double value
     */
    static double randDouble() {
        return value.nextDouble();
    }


    /**
     * main method which creates the generic binary data file.
     * 
     * First param is filename of File to be created
     * Second param is number of blocks to be created in file
     * Third param is one of the following types of files to be created:
     * "random" - randomly populates file
     * "sorted" - populates the file in sorted order
     * "reverseSorted" - populates the file in reverse sorted order
     * 
     * @param args - {filename, numBlocks, fileType}
     */
    public static void main(String args[]) {
        try {
            long val = 0;
            double val2 = 0;
            assert (args.length == 3) : 
                "\nUsage: GenBinaryDataFile <filename> <size>"
                + " <genType>\nOptions \nSize is measured in blocks" + 
                "of 8192 bytes";

            int filesize = Integer.parseInt(args[1]); // Size of file in blocks
            DataOutputStream file = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(args[0])));

            // Generate random file
            if (args[2].equals("random")) {
                for (int i = 0; i < filesize; i++)
                    for (int j = 0; j < NumRecs; j++) {
                        val2 = (double)(randDouble());
                        file.writeDouble(val2);
                        file.writeDouble(val2);
                    }
            }

            // Generate sorted file
            else if (args[2].equals("sorted")) {
                val = 0;
                val2 = 0.0;
                for (int i = 0; i < filesize; i++)
                    for (int j = 0; j < NumRecs; j++) {
                        file.writeLong(val);
                        val++;
                        file.writeDouble(val2);
                        val2 += 0.0001;
                    }
            }

            // Generate reverse sorted file
            else if (args[2].equals("reverseSorted")) {
                val = 0;
                Double d = 100000.0;
                val2 = d.doubleValue();
                for (int i = 0; i < filesize; i++)
                    for (int j = 0; j < NumRecs; j++) {
                        file.writeDouble(val2);
                        file.writeDouble(val2);
                        val++;
                        val2 -= 1;
                    }
            }

            file.flush();
            file.close();
        }
        catch (IOException e) {
            System.err.println("I/O error has occured: " + args[0]);
        }
    }
}