package transmission;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataConnectorThreadTest {

    private static final int PORTNUMBER = 9876;
    private static final int TEST_INT = 42;

    @Test
    public void test() throws IOException, InterruptedException {
        // open server side
        DataConnectorThread serverSide = new DataConnectorThread(PORTNUMBER);

        // open client side
        DataConnectorThread clientSide = new DataConnectorThread("localhost", PORTNUMBER);
        
        Thread serverThread = new Thread(serverSide);
        Thread clientThread = new Thread(clientSide);
        
        serverThread.start();
        clientThread.start();
        Thread.sleep(80);

        DataOutputStream dataOutputStream = clientSide.getDataOutputStream();
        dataOutputStream.writeInt(TEST_INT);

        DataInputStream dataInputStream = serverSide.getDataInputStream();
        int readValue = dataInputStream.readInt();

        Assert.assertEquals(TEST_INT, readValue);
    }

}
