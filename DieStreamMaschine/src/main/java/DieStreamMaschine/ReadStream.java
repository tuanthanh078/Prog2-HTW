package DieStreamMaschine;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReadStream implements StreamReadingMachine {
    private InputStream is;
    private DataInputStream dis;
       
    @Override
    public void open(String fileName) throws FileNotFoundException {
        is = new FileInputStream(fileName);
        dis = new DataInputStream(is);   
    }
    
    @Override
    public String readString() throws IOException { 
    	String s = dis.readUTF();
        return s;   
    }
    
    @Override
    public DataSet readData() throws IOException {
        long timeStamp = dis.readLong();
        int numOfValues = dis.readInt();
        float [] values = new float[numOfValues];
        for (int i = 0; i < numOfValues; i++) {
            values[i] = dis.readFloat();
        }
        return new DataSet(timeStamp, values);
    }
    
    @Override
    public void close() throws IOException {
        dis.close();
    }
    
    
}
