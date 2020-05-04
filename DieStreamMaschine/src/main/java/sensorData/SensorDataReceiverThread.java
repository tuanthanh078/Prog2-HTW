package sensorData;

import java.io.DataInputStream;
import java.io.IOException;

import DieStreamMaschine.*;
import transmission.DataConnection;

public class SensorDataReceiverThread extends Thread {
    private final DataConnection connection;
    private final DataStorage storage;
    private StreamMachine sm; 

    public SensorDataReceiverThread(DataConnection connection, DataStorage storage) {
        this.connection = connection;
        this.storage = storage;
        this.start();
    }

    public DataStorage getStorage() {
        return storage;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                DataInputStream dis = connection.getDataInputStream();
                
                // reads stream from sender
                String name = dis.readUTF();
                long timeStamp = dis.readLong();
                int numOfValues = dis.readInt();
                float [] values = new float[numOfValues];
                for (int i = 0; i < numOfValues; i++) {
                    values[i] = dis.readFloat();
                }
                
                // generates data set 
                DataSet dataSet = new DataSet(timeStamp, values);
                
                // appends data set to file
                sm = new StreamMachine("src\\main\\resources\\" + name + ".txt");
                sm.openWriter();
                sm.writeData(dataSet);
                sm.closeWriter();
                
                // add data set to memory
                storage.saveData(dataSet);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                if (e instanceof IOException) break;
            } 
        }
    }
    
    public void receiveData() throws Exception {
        
    }
}