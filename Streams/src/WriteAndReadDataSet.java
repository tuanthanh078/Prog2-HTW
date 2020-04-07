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
            try {
            	dos.writeUTF(sensorName);
            } catch (IOException ex) {
                System.err.println("couldn’t write name of sensor (fatal)");
                System.exit(0);
            }
            for (int i = 0; i < values.length; i++) {
                try {
                    dos.writeUTF(Long.toString(timeStamps[i]));
                } catch (IOException ex) {
                    System.err.println("couldn’t write data (fatal)");
                    System.exit(0);
                }
                for (int j = 0; j < values[i].length; j++) {
                    try {
                        dos.writeUTF(Float.toString(values[i][j]));
                    } catch (IOException ex) {
                        System.err.println("couldn’t write data (fatal)");
                        System.exit(0);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("couldn’t open file - fatal");
            System.exit(0); // brutal exception handling
        }
		try {
			dos.close();
		} catch (IOException ex) {
			System.err.println("couldn’t close file - fatal");
            System.exit(0); // brutal exception handling
		}
        
        // read data from file and print to System.out
        // TODO: your job use DataInputStream / FileInputStream
        InputStream is = null;
        DataInputStream dis = null;
        try {
            is = new FileInputStream(fileName);
            dis = new DataInputStream(is);
            System.out.println(dis.readUTF());
            while (dis.available() > 0) {
            	System.out.println(dis.readUTF());
            }
            
        } catch (FileNotFoundException ex) {
            System.err.println("couldn’t open file - fatal");
            System.exit(0);
        } catch (IOException ex) {
        	System.err.println("couldn’t read data (fatal)");
        	System.exit(0);
		}
        try {
			dis.close();
		} catch (IOException ex) {
			System.err.println("couldn’t close file - fatal");
            System.exit(0); // brutal exception handling
		}
    }
}
