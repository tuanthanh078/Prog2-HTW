package transmission;

import java.io.DataOutputStream;
import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        DataConnection clientSide = new DataConnector("localhost", 9873);

        DataOutputStream dataOutputStream = clientSide.getDataOutputStream();
        dataOutputStream.writeInt(42);
    }
    
}
