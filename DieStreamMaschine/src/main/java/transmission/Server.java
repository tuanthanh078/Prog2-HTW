package transmission;

import java.io.DataInputStream;
import java.io.IOException;

import DieStreamMaschine.DataStorage;
import DieStreamMaschine.StoreData;
import sensorData.SensorDataReceiver;

public class Server {
    public static void main(String[] args) throws Exception {
        DataConnection connection = new DataConnector(12345);
        DataStorage storage = new StoreData();
        SensorDataReceiver sdr = new SensorDataReceiver(connection, storage);
        while(true) {
            sdr.receiveData();
            System.out.println(storage.getLastData().getTimeStamp());
            System.out.println(storage.getLastData().getValues()[0]);
            System.out.println(storage.getLastData().getValues()[1]);
            System.out.println(storage.getLastData().getValues()[2]);
        }

    }
}
