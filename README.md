# Chat_application_CLI

# Simple Java Chat Application
This is a simple Java-based chat application that allows clients to connect to a server and exchange messages in real-time.

# Server Description
The server is implemented using Java's ServerSocket and Socket classes. It listens for incoming client connections on port 7777 and handles communication with multiple connected clients.

# Functionality
The server can accept multiple client connections simultaneously.
It displays messages received from connected clients.
The server admin can also send messages to all connected clients.

# How to Run
Compile the server code using any Java compiler (e.g., javac Server.java).
Run the server using java Server. The server will start listening for incoming connections.
The server admin can type messages in the console to send them to all connected clients.

# Client Description
The client is implemented using Java's Socket class. It can connect to the server on localhost and port 7777 to participate in the chat.

# Functionality
The client can send messages to the server and receive messages from other connected clients and the server admin.
It displays the messages received from the server.

# How to Run
Compile the client code using any Java compiler (e.g., javac Client.java).
Run the client using java Client. The client will connect to the server.
Type messages in the console to send them to the server and other clients.
To exit the chat, type "exit" in the console.

# Note
This is a basic chat application for educational purposes and doesn't use encryption or authentication.
The server's IP address is set to localhost for demonstration purposes. In a real-world scenario, the server's IP would be the public IP or domain name where the server is hosted.
Feel free to explore and modify the code according to your needs. Happy chatting!
