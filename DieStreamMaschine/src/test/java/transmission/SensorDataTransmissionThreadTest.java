package transmission;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import DieStreamMaschine.DataSet;
import DieStreamMaschine.DataStorage;
import DieStreamMaschine.StoreData;
import sensorData.SensorDataReceiver;
import sensorData.SensorDataReceiverThread;
import sensorData.SensorDataSender;

public class SensorDataTransmissionThreadTest {
    private static final int PORTNUMBER = 9876;
    private static final long TIMESTAMP = 1111111111;
    private static final float[] VALUES = {4.5f, 33.4f, 2.3f, -54.2f};
    private static final String NAME = "SENSOR1";

    @Test
    public void test() throws Exception {
        // open server side
        DataConnectorThread serverSide = new DataConnectorThread(PORTNUMBER);

        // open client side
        DataConnectorThread clientSide = new DataConnectorThread("localhost", PORTNUMBER);
        
        Thread serverThread = new Thread(serverSide);
        Thread clientThread = new Thread(clientSide);
        
        serverThread.start();
        clientThread.start();
        Thread.sleep(80);
        
        SensorDataSender sender = new SensorDataSender(clientSide);
        
        DataStorage storage = new StoreData();
        SensorDataReceiverThread receiver = new SensorDataReceiverThread(serverSide, storage);
        
        sender.sendData(NAME, TIMESTAMP, VALUES);
        
        Thread.sleep(50);
        DataSet data = storage.getLastData();
        assertEquals(TIMESTAMP, data.getTimeStamp());
        for (int i = 0; i < VALUES.length; i++) {
            assertEquals(VALUES[i], data.getValues()[i], 0.01);
        }
    }
}
