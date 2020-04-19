package DieStreamMaschine;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;


import org.junit.Before;
import org.junit.Test;





public class WriteStreamTest {
	private WriteStream ws;
	@Before
	public void setUp() throws Exception {
		ws = new WriteStream();
	}

	@Test (expected = FileNotFoundException.class)
	public void testOpenWithException() throws FileNotFoundException {
		ws.open("");
	}
	
	@Test (expected = PersistenceException.class)
	public void testWriteDataWithException() throws IOException, PersistenceException {
		DataSet dataSet = null;
		ws.open("src\\test\\resources\\test.txt");
		ws.writeData(dataSet);
	}
	
	@Test
	public void testWriteData() throws IOException, PersistenceException {
	    long timeStamp = System.currentTimeMillis();
	    float[] values = {(float) 0.7, (float) 1.2, (float) 2.1};
        DataSet ds = new DataSet(timeStamp, values);
        byte[] expecteds = concatAll(longToBytes(timeStamp), intToBytes(values.length), floatToBytes(values[0]), floatToBytes(values[1]), floatToBytes(values[2]));
        ws.open("src\\test\\resources\\test.txt");
        ws.writeData(ds);
        ws.close();
        InputStream is = new FileInputStream("src\\test\\resources\\test.txt");
        byte[] actuals = new byte[24]; // 8(long) + 4(int) + 4(float)*3 = 24
        is.read(actuals);
        is.close();
        assertArrayEquals(expecteds, actuals);
	}
	
	@Test
	public void testWriteString() throws IOException {
	    String s = "Hello World";
	    byte[] expecteds = s.getBytes();
	    ws.open("src\\test\\resources\\test.txt");
        ws.writeString(s);
        ws.close();
        InputStream is = new FileInputStream("src\\test\\resources\\test.txt");
        byte[] utfByteArray = new byte[s.length()+2]; // the first two bytes do not encode string 
        is.read(utfByteArray);
        is.close();
        byte[] actuals = new byte[s.length()];
        for (int i = 0; i < actuals.length; i++) {
            actuals[i] = utfByteArray[i+2];
        }
        assertArrayEquals(expecteds, actuals);
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
