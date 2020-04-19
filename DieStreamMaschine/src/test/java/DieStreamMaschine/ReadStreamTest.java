package DieStreamMaschine;

import static org.junit.Assert.*;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class ReadStreamTest {
    private ReadStream rs;
    
    @Before
    public void setUp() throws Exception {
        rs = new ReadStream();
    }

    @Test (expected = FileNotFoundException.class)
    public void testOpenWithException() throws FileNotFoundException {
        rs.open("");
    }

    @Test
    public void testReadString() throws IOException {
        String s = "Hello World";
        byte[] expecteds = s.getBytes();
        OutputStream os = new FileOutputStream("src\\test\\resources\\test.txt");
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(s);
        dos.close();
        rs.open("src\\test\\resources\\test.txt");
        byte[] actuals = rs.readString().getBytes();
        rs.close();
        assertArrayEquals(expecteds, actuals);
    }
    
    @Test (expected = IOException.class)
    public void testReadStringWithException() throws IOException {
        int x = 6666666;
        OutputStream os = new FileOutputStream("src\\test\\resources\\test.txt");
        os.write(intToBytes(x));
        os.close();
        rs.open("src\\test\\resources\\test.txt");
        rs.readString();
        rs.close();
    }
    
    @Test
    public void testReadData() throws IOException {
        long timeStamp = System.currentTimeMillis();
        float[] values = {(float) 0.7, (float) 1.2, (float) 2.1};
        byte[] expecteds = concatAll(longToBytes(timeStamp), intToBytes(values.length), floatToBytes(values[0]), floatToBytes(values[1]), floatToBytes(values[2]));
        OutputStream os = new FileOutputStream("src\\test\\resources\\test.txt");
        os.write(expecteds);
        os.close();
        rs.open("src\\test\\resources\\test.txt");
        DataSet actualDataSet = rs.readData();
        byte[] actuals = concatAll(longToBytes(actualDataSet.getTimeStamp()), intToBytes(actualDataSet.getValues().length),
                                   floatToBytes(actualDataSet.getValues()[0]), floatToBytes(actualDataSet.getValues()[1]),
                                   floatToBytes(actualDataSet.getValues()[2]));
        rs.close();
        assertArrayEquals(expecteds, actuals);
    }
    
    @Test (expected = IOException.class)
    public void testReadDataWithException() throws IOException {
        int x = 6666666;
        OutputStream os = new FileOutputStream("src\\test\\resources\\test.txt");
        os.write(intToBytes(x));
        os.close();
        rs.open("src\\test\\resources\\test.txt");
        rs.readData();
        rs.close();
    }
    
 // convert long value to byte array
    private static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }
    
    // convert int value to byte array
    private static byte[] intToBytes(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(x);
        return buffer.array();
    }
    
    // convert float value to byte array
    private static byte[] floatToBytes(float x) {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        buffer.putFloat(x);
        return buffer.array();
    }
    
    // concatenates byte arrays
    private static byte[] concatAll(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
          totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

}
