import java.io.*;
import java.net.*;

public class Server {

    ServerSocket server;
    Socket sock;

    BufferedReader br; // To read data from the client
    PrintWriter wr;    // To write data to the client

    // Constructor
    public Server() {
        try {
            // Create a new ServerSocket on port 7777 to listen for incoming connections
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept");

            // Accept incoming client connection and store it in sock
            sock = server.accept();

            // Set up input and output streams to communicate with the client
            br = new BufferedReader(new InputStreamReader(sock.getInputStream())); // Read data from the client
            wr = new PrintWriter(sock.getOutputStream());                          // Write data to the client

            // Start a thread to read data from the client
            startReading();

            // Start a thread to write data to the client
            startWriting();

        } catch (Exception e) {
            e.printStackTrace(); // If any exception occurs during setup, print the stack trace
        }
    }

    // We have to read data with the help of BufferedReader every time to achieve multithreading
    public void startReading() {
        // This thread will read data from the user (client)
        Runnable r1 = () -> {
            System.out.println("Reader started");
            try {
                while (true) {
                    // Read the whole line sent by the client and store it in the msg string
                    String msg = br.readLine();
                    if (msg == null || msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        break; // If the client sends "exit" or connection is closed, break the loop and close the reader
                    }
                    System.out.println("Client: " + msg); // Display the message sent by the client
                }
            } catch (IOException e) {
                e.printStackTrace(); // If any exception occurs during reading, print the stack trace
            }
        };
        // Start the reading thread
        new Thread(r1).start();
    }

    // We have to write data with the help of BufferedReader every time
    public void startWriting() {
        // This thread will write data to the client
        Runnable r2 = () -> {
            System.out.println("Writer started..");
            try {
                // Read data from the terminal (console) to be sent to the client
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content;
                while (true) {
                    content = br1.readLine(); // Read a line of input from the console
                    if (content == null || content.equalsIgnoreCase("exit")) {
                        System.out.println("Closing server...");
                        break; // If the server admin enters "exit" or connection is closed, break the loop and close the writer
                    }
                    wr.println(content); // Send the message to the client
                    wr.flush();          // Flush the output stream to ensure the data is sent to the client
                }
                br1.close();
            } catch (IOException e) {
                e.printStackTrace(); // If any exception occurs during writing, print the stack trace
            } finally {
                try {
                    sock.close(); // Close the socket when writing is done
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        // Start the writing thread
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is the server");
        // Create a new instance of the Server class to start the server
        new Server();
    }
}
