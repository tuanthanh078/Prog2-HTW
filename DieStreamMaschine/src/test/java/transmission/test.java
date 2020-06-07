package transmission;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int PORTNUMBER = 9812;
        final int TEST_INT = 42;
        // open server side
        DataConnectorThread serverSide = new DataConnectorThread(PORTNUMBER);

        // open client side
        DataConnectorThread clientSide = new DataConnectorThread("localhost", PORTNUMBER);
        
        Thread serverThread = new Thread(serverSide);
        Thread clientThread = new Thread(clientSide);
        //serverThread.setPriority(10);
        //clientThread.setPriority(5);
        serverThread.start();
        clientThread.start();
        Thread.sleep(80);


        DataOutputStream dataOutputStream = clientSide.getDataOutputStream();
        dataOutputStream.writeInt(TEST_INT);

        DataInputStream dataInputStream = serverSide.getDataInputStream();
        int readValue = dataInputStream.readInt();
        
        System.out.println(readValue);
        
    }
}
