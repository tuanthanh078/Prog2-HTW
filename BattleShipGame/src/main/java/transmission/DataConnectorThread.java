package transmission;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DataConnectorThread extends Thread
                                 implements DataConnection {
    private Socket socket = null;
    private String address = null;
    private int port;
    /**
     * Create client side - open connection to address / port
     * 
     * @param address IPv4 address of server
     * @param port server port
     */
    public DataConnectorThread(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Create server side - open port on this port and wait for one client
     * 
     * @param port server port
     */
    public DataConnectorThread(int port) {
        this.port = port;
    }
    
    @Override
    public void run() {
        if (address == null) {
            try {
                System.out.println("Binding to port " + port + ", please wait  ...");
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Server started: " + serverSocket);
                System.out.println("Waiting for a client ...");
                while (true) {
                    try {
                        socket = serverSocket.accept();
                        Thread.sleep(50);
                        System.out.println("Client accepted: " + socket);
                        break;
                    } catch (IOException | InterruptedException e) {
                        System.err.println(" Connection Error: " + e);
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else {
            try {
                socket = new Socket(address, port); // Connect to server
                System.out.println("Connected: " + socket);
            } catch (IOException e) {
                System.out.println("Can't connect to server");
            }
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