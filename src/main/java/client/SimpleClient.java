package client;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class SimpleClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // Example command:
            String command = "ADD_DISASTER|Fire|Sydney|High|Sarah Connor|Fire at CBD|2025-06-12T20:00";

            writer.println(command);

            String response = reader.readLine();
            System.out.println("Server response: " + response);

            // Optionally, close connection:
            writer.println("bye");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
