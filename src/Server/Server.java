package Server;

import DAL.JDBCConnector;
import Server.Strategies.IServerStrategy;
import org.junit.rules.Timeout;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private int counter = 0;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    public void start() {
        runServer();
    }

    private void runServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    handleClient(clientSocket);
                } catch (SocketTimeoutException e) {

                }
            }
            serverSocket.close();
        } catch (IOException e) {
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            counter++;
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
            if(counter == 3){
                JDBCConnector jdbcConnector = new JDBCConnector();
                jdbcConnector.connectDBSaveData();
                jdbcConnector.connectDBUploadData();
                counter = 0;
            }
        } catch (IOException e) {
        }
    }

    public void stop() {
        stop = true;
    }
}
