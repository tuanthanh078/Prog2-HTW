package DieStreamMaschine;

public class DataSet {
    private long timeStamp;
    private float[] values;
    
    public DataSet(long timeStamp, float [] values) throws PersistenceException{
        if (timeStamp < 0 || values == null) throw new PersistenceException("Invalid values");
        this.timeStamp = timeStamp;
        this.values = values;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public float[] getValues() {
        return values;
    }
}
