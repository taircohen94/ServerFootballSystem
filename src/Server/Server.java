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

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    public void start() {
        runServer();
//        new Thread(() -> {
//            runServer();
//        }).start();
        //new Thread(this::runServer).start();
    }

    private void runServer() {
        try {
            int counter = 0;
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            while (!stop) {
                try {
                    counter++;
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    handleClient(clientSocket);
//                    new Thread(() -> {
//                        handleClient(clientSocket);
//                    }).start();
                } catch (SocketTimeoutException e) {

                }
                if(counter == 1000){
                    JDBCConnector jdbcConnector = new JDBCConnector();
                    jdbcConnector.connectDBSaveData();
                    jdbcConnector.connectDBUploadData();
                    counter = 0;
                }
            }
            serverSocket.close();
        } catch (IOException e) {
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    public void stop() {
        stop = true;
    }
}
