package DieStreamMaschine;

public class DataSet {
    private long timeStamp;
    private float[] values;
    
    DataSet(long timeStamp, float [] values) {
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
