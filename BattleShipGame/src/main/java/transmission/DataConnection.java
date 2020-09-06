package transmission;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This class is an interface for the connection between client and server.
 */
public interface DataConnection {
    
    /**
     * Returns an object of DataInputStream from the socket
     * 
     * @return an object of DataInputStream
     * @throws IOException
     */
    DataInputStream getDataInputStream() throws IOException;
    
    /**
     * Returns an object of DataInputStream from the socket
     * 
     * @return an object of DataOutputStream
     * @throws IOException
     */
    DataOutputStream getDataOutputStream() throws IOException;
}