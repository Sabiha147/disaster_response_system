package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DRSServer {

    public static void main(String[] args) {
         // Now proceed to server start:
        int port = 12345; // you can choose any free port

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("DRS Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
