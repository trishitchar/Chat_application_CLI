import java.io.*;
import java.net.*;

public class Client {

    Socket sock;

    BufferedReader br; // To read data from the server
    PrintWriter wr;    // To write data to the server

    // Constructor
    public Client() {
        try {
            // Create a new Socket and connect to the server running on localhost at port 7777
            sock = new Socket("localhost", 7777);

            // Set up input and output streams to communicate with the server
            br = new BufferedReader(new InputStreamReader(sock.getInputStream())); // Read data from the server
            wr = new PrintWriter(sock.getOutputStream());                          // Write data to the server

            // Start a thread to read data from the server
            startReading();

            // Start a thread to write data to the server
            startWriting();

        } catch (Exception e) {
            e.printStackTrace(); // If any exception occurs during setup, print the stack trace
        }
    }

    // We have to read data with the help of BufferedReader every time to achieve multithreading
    public void startReading() {
        // This thread will read data from the server
        Runnable r1 = () -> {
            System.out.println("Reader started");
            try {
                while (true) {
                    // Read the whole line sent by the server and store it in the msg string
                    String msg = br.readLine();
                    if (msg == null || msg.equals("exit")) {
                        System.out.println("Server closed the connection");
                        break; // If the server sends "exit" or connection is closed, break the loop and close the reader
                    }
                    System.out.println("Server: " + msg); // Display the message sent by the server
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
        // This thread will write data to the server
        Runnable r2 = () -> {
            System.out.println("Writer started..");
            try {
                // Read data from the terminal (console) to be sent to the server
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content;
                while (true) {
                    content = br1.readLine(); // Read a line of input from the console
                    if (content == null || content.equalsIgnoreCase("exit")) {
                        System.out.println("Closing client...");
                        break; // If the client user enters "exit" or connection is closed, break the loop and close the writer
                    }
                    wr.println(content); // Send the message to the server
                    wr.flush();          // Flush the output stream to ensure the data is sent to the server
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
        System.out.println("This is the client");
        // Create a new instance of the Client class to start the client
        new Client();
    }
}
