package transmission;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DataConnector implements DataConnection {
    private Socket socket = null;
    /**
     * Create client side - open connection to address / port
     * 
     * @param address IPv4 address of server
     * @param port server port
     */
    public DataConnector(String address, int port) {
        try {
            socket = new Socket(address, port); // Connect to server
            System.out.println("Connected: " + socket);
        } catch (IOException e) {
            System.out.println("Can't connect to server");
        }
    }

    /**
     * Create server side - open port on this port and wait for one client
     * 
     * @param port server port
     */
    public DataConnector(int port) {
        try {
            System.out.println("Binding to port " + port + ", please wait  ...");
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started: " + serverSocket);
            System.out.println("Waiting for a client ...");
            while (true) {
                try {
                    socket = serverSocket.accept();
                    System.out.println("Client accepted: " + socket);
                    break;
                } catch (IOException e) {
                    System.err.println(" Connection Error: " + e);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public DataInputStream getDataInputStream() throws IOException {
        return new DataInputStream(socket.getInputStream()); 
    }

    @Override
    public DataOutputStream getDataOutputStream() throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }
    
}