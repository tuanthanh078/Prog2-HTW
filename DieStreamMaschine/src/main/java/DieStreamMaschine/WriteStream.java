package DieStreamMaschine;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class WriteStream implements StreamWritingMachine {
    private OutputStream os;
    private DataOutputStream dos;
      
    @Override
    public void open(String fileName) throws FileNotFoundException {
        os = new FileOutputStream(fileName, true);
        dos = new DataOutputStream(os);
    }
    
    @Override
    public void writeData(DataSet dataSet) throws IOException, PersistenceException {
    	if (dataSet == null) throw new PersistenceException("Data set is empty");
        dos.writeLong(dataSet.getTimeStamp());
        float[] values = dataSet.getValues();
        int numOfValues = values.length;
        dos.writeInt(numOfValues);
        for (int i = 0; i < numOfValues; i++) {
            dos.writeFloat(values[i]);
        }
    }
      
    @Override
    public void close() throws IOException {
        dos.close();
    }

    @Override
    public void writeString(String s) throws IOException {
        dos.writeUTF(s);
    }
}
