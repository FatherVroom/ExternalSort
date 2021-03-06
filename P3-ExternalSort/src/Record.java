import java.nio.ByteBuffer;

/**
 * Holds a single record object. A record object contain a key (double) and
 * value (long)
 * 
 * @author CS Staff, Aniket Adhikari, Chris Koehler
 * @version 2020-10-15
 */
public class Record implements Comparable<Record> {

    private byte[] completeRecord;
    private long value;
    private double key;

    /**
     * The constructor for the Record class
     * 
     * @param record
     *            The byte for this object
     */
    public Record(byte[] record) {
        completeRecord = record;
        ByteBuffer bb = ByteBuffer.wrap(completeRecord);
        value = bb.getLong();
        key = bb.getDouble(8);
    }


    /**
     * Another constructor for the Record class
     * 
     * @param k
     *            key for the record
     * @param v
     *            value for the record
     */
    public Record(double k, long v) {
        key = k;
        value = v;
        ByteBuffer bb = ByteBuffer.allocate(16);
        bb.putLong(v);
        bb.putDouble(k);
        completeRecord = bb.array();
    }


    /**
     * returns the complete record
     * 
     * @return complete record
     */
    public byte[] getCompleteRecord() {
        return completeRecord;
    }


    /**
     * Returns the object's key
     * 
     * @return the key
     */
    public double getKey() {
        return key;
    }


    /**
     * Return's the object's value
     * 
     * @return the value
     */
    public long getValue() {
        return value;
    }


    /**
     * Setter method that sets the key portion of the record object. Also alters
     * the completeRecord attribute.
     * 
     * @param k
     *            the new key
     */
    public void setKey(double k) { // ** complete in constructor **
        ByteBuffer bb = ByteBuffer.allocate(Double.BYTES);
        bb.putDouble(k);
        byte[] kb = bb.array();
        int start = 8;
        for (int i = 0; i < bb.capacity(); i++) {
            completeRecord[start] = kb[i];
            start++;
        }
        key = k;
    }


    /**
     * Setter method that sets the value portion of the record object. Also
     * alters the completeRecord attribute.
     * 
     * @param v
     *            the new value
     */
    public void setValue(long v) { // ** complete in constructor **
        ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
        bb.putLong(v);
        byte[] vb = bb.array();
        for (int i = 0; i < bb.capacity(); i++) {
            completeRecord[i] = vb[i];
        }
        value = v;
    }


    /**
     * Compare Two Records based on their keys
     * 
     * @param toBeCompared
     *            The Record to be compared.
     * @return A negative integer, zero, or a positive integer as this record
     *         is less than, equal to, or greater than the supplied record
     *         object.
     */
    @Override
    public int compareTo(Record toBeCompared) {
        return Double.compare(this.getKey(), toBeCompared.getKey());
    }


    /**
     * Outputs the record as a String
     * 
     * @return a string of what the record contains
     */
    public String toString() {
        return "" + this.getKey();
    }

}
