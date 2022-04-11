import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * Output buffer capable of storing Record objects and converting them
 * back into Byte form for purposes of writing to a File in secondary memory.
 * 
 * @author Christopher Koehler, Aniket Adhikari
 * @version 2022-04-09
 */
public class OutputBuffer {
    // Fields
    private Record[] records;
    private final int BUFFER_CAPACITY = 8192;
    private final int RECORD_CAPACITY = 512;
    private int size;

    // Constructors
    /**
     * Creates new empty OutputBuffer
     */
    public OutputBuffer() {
        records = new Record[RECORD_CAPACITY];
        size = 0;
    }


    // Methods
    /**
     * Adds a Record object to the Record array backing this OutputBuffer
     * 
     * @param r
     *            - The Record to be added
     */
    public boolean addRecord(Record r) {
        if (!isFull()) {
            records[size] = r;
            size++;
            return true;
        }
        return false;
    }


    /**
     * Creates an array of this full OutputBuffer's Records in byte form
     * 
     * @return An array of Records in byte form
     */
    public byte[] convertRecsToByteForm() {
        byte[] recsByteForm = new byte[BUFFER_CAPACITY];
        int byteArrIndex = 0;
        // Loop through all Record objects, storing their byte form
        for (int i = 0; i < RECORD_CAPACITY; i++) {
            Record nextRec = records[i];
            byte[] byteForm = nextRec.getCompleteRecord();
            // Loop through byte form, storing in recsByteForm
            for (int j = 0; j < 16; j++) {
                recsByteForm[byteArrIndex] = byteForm[j];
                byteArrIndex++;
            }
        }
        return recsByteForm;
    }


    /**
     * Writes contents of this OutputBuffer to the specified runFile
     * 
     * @param raf
     *            - The runFile to write the buffer to
     * @throws IOException
     */
    public void writeToRunFile(RandomAccessFile raf) throws IOException {
        byte[] recsByteForm = convertRecsToByteForm();
        raf.write(recsByteForm);
    }


    /**
     * Checks whether this buffer is at capacity.
     * 
     * @return True if this buffer is at capacity
     */
    public boolean isFull() {
        return size == RECORD_CAPACITY;
    }
}
