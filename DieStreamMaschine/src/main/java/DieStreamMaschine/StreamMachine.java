package DieStreamMaschine;

import java.io.FileNotFoundException;
import java.io.IOException;

public class StreamMachine {
    private StreamReadingMachine reader;
    private StreamWritingMachine writer;
    private boolean activateReader = false;
    private boolean activateWriter = false;
    private String fileName;
    
    public StreamMachine(String fileName) {
        reader = new ReadStream();
        writer = new WriteStream();
        this.fileName = fileName;
    }
    
    public void openReader() throws FileNotFoundException {
        activateReader = true;
        reader.open(fileName);
    }
    
    public void openWriter() throws FileNotFoundException {
        activateWriter = true;
        writer.open(fileName);
    }
    
    public void writeString(String s) throws Exception {
        if (!activateWriter) throw new PersistenceException("Please open the stream machine.");
        writer.writeString(s);
    }
    
    public void writeData(DataSet dataSet) throws Exception {
        if (!activateWriter) throw new PersistenceException("Please open the stream machine.");
        if (dataSet == null) throw new PersistenceException("Data set is empty");
        writer.writeData(dataSet);
    }
    
    public String readString() throws Exception {
        if (!activateReader) throw new PersistenceException("Please open the stream machine.");
        String s = reader.readString();
        return s;
    }
    
    public DataSet readData() throws Exception {
        if (!activateReader) throw new PersistenceException("Please open the stream machine.");
        DataSet dataSet = reader.readData();
        return dataSet;
    }
    
    public void closeReader() throws IOException {
    	reader.close();
    }
    
    public void closeWriter() throws IOException {
        writer.close();
    }
}
