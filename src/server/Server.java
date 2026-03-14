package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 1235;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on  127.0.0.1:" + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();

                ServerThread serverThread = new ServerThread(clientSocket);
                serverThread.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
