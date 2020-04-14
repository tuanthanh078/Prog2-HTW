import java.io.*;

public class WriteAndReadDataSet {
    public static void main(String[] args) {
        // three example data sets
        String sensorName = "MyGoodOldSensor"; // does not change

        long timeStamps[] = new long[3];
        timeStamps[0] = System.currentTimeMillis();
        timeStamps[1] = timeStamps[0] + 1; // milli sec later
        timeStamps[2] = timeStamps[1] + 1000; // second later

        float[][] values = new float[3][];
        // 1st measure .. just one value
        float[] valueSet = new float[1];
        values[0] = valueSet;
        valueSet[0] = (float) 1.5; // example value 1.5 degrees

        // 2nd measure .. just three values
        valueSet = new float[3];
        values[1] = valueSet;
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;
        valueSet[2] = (float) 2.1;

        // 3rd measure .. two values
        valueSet = new float[2];
        values[2] = valueSet;
        valueSet[0] = (float) 0.7;
        valueSet[1] = (float) 1.2;

        // write three data set into a file
        // TODO: your job. use DataOutputStream / FileOutputStream
        String fileName = "sensordata.txt";
        OutputStream os = null;
        DataOutputStream dos = null;
        try {
            os = new FileOutputStream(fileName);
            dos = new DataOutputStream(os);
            dos.writeUTF(sensorName); // writes name of sensor
            int numOfTimeStamps = timeStamps.length;
            dos.writeInt(numOfTimeStamps); // writes number of time stamps 
            for (int i = 0; i < numOfTimeStamps; i++) {
                dos.writeLong(timeStamps[i]); // writes time stamp
                int numOfValues = values[i].length;
                dos.writeInt(numOfValues); // writes number of values 
                for (int j = 0; j < numOfValues; j++) {
                    dos.writeFloat(values[i][j]); // writes value
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("couldn’t open file - fatal");
            System.exit(0); // brutal exception handling
        } catch (IOException ex) {
            System.err.println("couldn’t write data (fatal)");
            System.exit(0);
        }
        // closes
        try {
            dos.close();
        } catch (IOException ex) {
            System.err.println("couldn’t close file - fatal");
            System.exit(0);
        }

        // read data from file and print to System.out
        // TODO: your job use DataInputStream / FileInputStream
        InputStream is = null;
        DataInputStream dis = null;
        String sensorName_ = ""; 
        long[] timeStamps_ = null; // storage of time stamps
        float[][] values_ = null; // storage of values
        boolean successful = false; // completely read?
        try {
            is = new FileInputStream(fileName);
            dis = new DataInputStream(is);
            sensorName_ = dis.readUTF(); // reads name of sensor
            int numOfTimeStamps_ = dis.readInt(); // reads number of time stamps
            timeStamps_ = new long[numOfTimeStamps_];
            values_ = new float[numOfTimeStamps_][];
            for (int i = 0; i < numOfTimeStamps_; i++) {
                timeStamps_[i] = dis.readLong(); // reads time stamps
                int numOfValues_ = dis.readInt(); // reads number of values
                float[] valueSet_ = new float[numOfValues_];
                for (int j = 0; j < numOfValues_; j++) {
                    valueSet_[j] = dis.readFloat(); // reads values
                }
                values_[i] = valueSet_; // saves values into storage
            }
            successful = true;
        } catch (FileNotFoundException ex) {
            System.err.println("couldn’t open file - fatal");
            System.exit(0);
        } catch (IOException ex) {
            System.err.println("couldn’t read data (fatal)");
            System.exit(0);
        }
        // closes
        try {
            dis.close();
        } catch (IOException ex) {
            System.err.println("couldn’t close file - fatal");
            System.exit(0);
        }

        if (successful && timeStamps_ != null && values_ != null) { // checks and prints data
            System.out.println("Name of the sensor: " + sensorName_);
            for (int i = 0; i < timeStamps_.length; i++) {
                System.out.println("Time stamp " + i + ":" + timeStamps_[i]);
                for (int j = 0; j < values_[i].length; j++) {
                    System.out.println(j + 1 + ". Values: " + values_[i][j]);
                }
            }
        }
    }
}
