package DieStreamMaschine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class StoreData implements DataStorage{
    private LinkedList<DataSet> dataStorage = new LinkedList<DataSet>();
    

    public StoreData(DataSet dataSet1, DataSet... rest) {
        dataStorage.add(dataSet1);
        for (DataSet dataSet: rest) {
            dataStorage.add(dataSet);
        }
    }
    
    public StoreData() {}

    @Override
    public void saveData(DataSet dataSet) throws PersistenceException {
        if (dataSet == null) throw new PersistenceException("Data set is empty");
        dataStorage.add(dataSet);
    }

    @Override
    public int size() {
        return dataStorage.size();
    }

    @Override
    public DataSet getLastData() throws PersistenceException {
        if (dataStorage.size() == 0) throw new PersistenceException("Empty storage");
        return dataStorage.get(size()-1);
    }

    @Override
    public DataSet getFirstData() throws PersistenceException {
        if (dataStorage.size() == 0) throw new PersistenceException("Empty storage");
        return dataStorage.get(0);
    }

    @Override
    public DataSet getData(int index) throws PersistenceException {
        if (index < 0 || index >= dataStorage.size()) throw new PersistenceException("Invalid index");
        return dataStorage.get(index);
    }

    @Override
    public Iterator<DataSet> iterator() {
        return new DataStorageIterator();
    }
    
    private class DataStorageIterator implements Iterator<DataSet> {
        private int i;
        int n = dataStorage.size();
        public DataStorageIterator() {
            i = 0;
        }
        public boolean hasNext() {
            return i < n;
        }
        public DataSet next() {
            if (!hasNext()) throw new NoSuchElementException();
            DataSet ds = dataStorage.get(i);
            i++;
            return ds;
        }
    }

    @Override
    public void clear() {
        dataStorage.clear();
    }

}
