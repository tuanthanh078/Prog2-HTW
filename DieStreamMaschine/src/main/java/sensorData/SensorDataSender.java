package sensorData;

import transmission.DataConnection;

import java.io.DataOutputStream;
import java.io.IOException;

public class SensorDataSender {
    private final DataConnection connection;

    public SensorDataSender(DataConnection connection) {
        this.connection = connection;
    }

    public void sendData(String name, long timeStamp, float[] values) throws IOException {
        DataOutputStream dos = connection.getDataOutputStream();
        
        // writes values
        dos.writeUTF(name);
        dos.writeLong(timeStamp);
        int numOfValues = values.length;
        dos.writeInt(numOfValues);
        for(float value: values) {
            dos.writeFloat(value);
        }
    }
}