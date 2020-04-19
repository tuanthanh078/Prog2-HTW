package DieStreamMaschine;

import static org.junit.Assert.*;

import org.junit.Test;

public class StoreDataTest {
    private StoreData storage;
    
    @Test
    public void testSaveData() throws PersistenceException {
        long timeStamp = System.currentTimeMillis();
        float[] values = {(float) 0.7, (float) 1.2, (float) 2.1};
        DataSet dataSet = new DataSet(timeStamp, values);
        StoreData expectedStorage = new StoreData(dataSet); // expected storage with 1 data set
        storage = new StoreData(); // empty storage
        storage.saveData(dataSet); // add 1 data set into storage
        assertEquals(expectedStorage.getLastData(), storage.getLastData());
    }
    
    @Test (expected = PersistenceException.class)
    public void testSaveDataWithException() throws PersistenceException {
        storage = new StoreData();
        storage.saveData(null);
    }

    @Test
    public void testSize() throws Exception {
        long timeStamp = System.currentTimeMillis();
        float[] values1 = {(float) 0.7, (float) 1.2, (float) 2.1};
        float[] values2 = {(float) 1.5};
        float[] values3 = {(float) 0.7, (float) 1.2};
        DataSet dataSet1 = new DataSet(timeStamp, values1);
        DataSet dataSet2 = new DataSet(timeStamp+1, values2);
        DataSet dataSet3 = new DataSet(timeStamp+1000, values3);
        storage = new StoreData(dataSet1, dataSet2, dataSet3);
        assertEquals(3, storage.size());
    }

    @Test
    public void testGetLastData() throws Exception {
        long timeStamp = System.currentTimeMillis();
        float[] values1 = {(float) 0.7, (float) 1.2, (float) 2.1};
        float[] values2 = {(float) 1.5};
        float[] values3 = {(float) 0.7, (float) 1.2};
        DataSet dataSet1 = new DataSet(timeStamp, values1);
        DataSet dataSet2 = new DataSet(timeStamp+1, values2);
        DataSet dataSet3 = new DataSet(timeStamp+1000, values3);
        storage = new StoreData(dataSet1, dataSet2, dataSet3);
        assertEquals(dataSet3, storage.getLastData());
    }
    
    @Test (expected = PersistenceException.class)
    public void testGetLastDataWithException() throws Exception {
        storage = new StoreData();
        storage.getLastData();
    }

    @Test
    public void testGetFirstData() throws Exception {
        long timeStamp = System.currentTimeMillis();
        float[] values1 = {(float) 0.7, (float) 1.2, (float) 2.1};
        float[] values2 = {(float) 1.5};
        float[] values3 = {(float) 0.7, (float) 1.2};
        DataSet dataSet1 = new DataSet(timeStamp, values1);
        DataSet dataSet2 = new DataSet(timeStamp+1, values2);
        DataSet dataSet3 = new DataSet(timeStamp+1000, values3);
        storage = new StoreData(dataSet1, dataSet2, dataSet3);
        assertEquals(dataSet1, storage.getFirstData());
    }

    @Test (expected = PersistenceException.class)
    public void testGetFirstDataWithException() throws Exception {
        storage = new StoreData();
        storage.getFirstData();
    }
    
    @Test
    public void testGetData() throws Exception {
        long timeStamp = System.currentTimeMillis();
        float[] values1 = {(float) 0.7, (float) 1.2, (float) 2.1};
        float[] values2 = {(float) 1.5};
        float[] values3 = {(float) 0.7, (float) 1.2};
        DataSet dataSet1 = new DataSet(timeStamp, values1);
        DataSet dataSet2 = new DataSet(timeStamp+1, values2);
        DataSet dataSet3 = new DataSet(timeStamp+1000, values3);
        storage = new StoreData(dataSet1, dataSet2, dataSet3);
        assertEquals(dataSet2, storage.getData(1));
    }
    
    @Test (expected = PersistenceException.class)
    public void testGetDataWithException() throws Exception {
        long timeStamp = System.currentTimeMillis();
        float[] values1 = {(float) 0.7, (float) 1.2, (float) 2.1};
        float[] values2 = {(float) 1.5};
        float[] values3 = {(float) 0.7, (float) 1.2};
        DataSet dataSet1 = new DataSet(timeStamp, values1);
        DataSet dataSet2 = new DataSet(timeStamp+1, values2);
        DataSet dataSet3 = new DataSet(timeStamp+1000, values3);
        storage = new StoreData(dataSet1, dataSet2, dataSet3);
        storage.getData(4);
    }

    @Test
    public void testIterator() {
        long timeStamp = System.currentTimeMillis();
        float[] values1 = {(float) 0.7, (float) 1.2, (float) 2.1};
        float[] values2 = {(float) 1.5};
        float[] values3 = {(float) 0.7, (float) 1.2};
        DataSet dataSet1 = new DataSet(timeStamp, values1);
        DataSet dataSet2 = new DataSet(timeStamp+1, values2);
        DataSet dataSet3 = new DataSet(timeStamp+1000, values3);
        DataSet[] dataSets = {dataSet1, dataSet2, dataSet3}; 
        storage = new StoreData(dataSet1, dataSet2, dataSet3);
        int i = 0;
        for (DataSet dataSet: storage) {
            assertEquals(dataSets[i++], dataSet);
        }
    }

    @Test
    public void testClear() throws Exception {
        long timeStamp = System.currentTimeMillis();
        float[] values1 = {(float) 0.7, (float) 1.2, (float) 2.1};
        float[] values2 = {(float) 1.5};
        float[] values3 = {(float) 0.7, (float) 1.2};
        DataSet dataSet1 = new DataSet(timeStamp, values1);
        DataSet dataSet2 = new DataSet(timeStamp+1, values2);
        DataSet dataSet3 = new DataSet(timeStamp+1000, values3);
        storage = new StoreData(dataSet1, dataSet2, dataSet3);
        storage.clear();
        assertEquals(0, storage.size());
    }

}
