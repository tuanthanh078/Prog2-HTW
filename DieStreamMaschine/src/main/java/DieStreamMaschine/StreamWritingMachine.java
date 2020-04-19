package DieStreamMaschine;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface StreamWritingMachine {
    void open(String fileName) throws FileNotFoundException;
    void writeString(String s) throws IOException;
    void writeData(DataSet dataSet) throws IOException, PersistenceException;
    void close() throws IOException;
}
