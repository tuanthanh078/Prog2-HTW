package DieStreamMaschine;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface StreamReadingMachine {
    void open(String fileName) throws FileNotFoundException;
    String readString() throws IOException;
    DataSet readData() throws IOException;
    void close() throws IOException;
}
