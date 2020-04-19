package DieStreamMaschine;

import java.util.Iterator;
public interface DataStorage extends Iterable<DataSet>{
    void saveData(DataSet dataSet) throws PersistenceException;
    int size();
    DataSet getLastData() throws PersistenceException;
    DataSet getFirstData() throws PersistenceException;
    DataSet getData(int index) throws PersistenceException;
    void clear();
    Iterator<DataSet> iterator();
}
