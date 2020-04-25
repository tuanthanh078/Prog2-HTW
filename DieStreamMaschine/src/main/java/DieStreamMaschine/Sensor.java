package DieStreamMaschine;

import java.io.IOException;
import java.util.Scanner;

import sensorData.SensorDataSender;
import transmission.DataConnection;
import transmission.DataConnector;

public class Sensor {
    public static void main(String[] args) throws IOException {
        DataConnection connection = new DataConnector("localhost", 12345);
        SensorDataSender sds = new SensorDataSender(connection);
        long timeStamp = 46324783264l;
        float[] values = {2.3f, 1.5f, 3.4f};
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter: ");
        int n = sc.nextInt();
        while (n != 0) {
            sds.sendData("Sensor1", timeStamp, values);
            System.out.print("Enter: ");
            n = sc.nextInt();
        }
    }
}
