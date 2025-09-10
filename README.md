# Java Chat Application

## Overview

This project is a **Java-based Chat Application** that supports both
**GUI** and **Text-based clients**, allowing multiple users to connect
to a server and exchange messages in real time. The project was designed as a practical exercise in **network
programming, multithreading, exception handling, and GUI development**.

## Features

-   **Client-Server Architecture**: Users connect to a central server
    which manages message delivery.
-   **Two types of clients**:
    -   `GuiClient` -- graphical interface using Swing
    -   `TextClient` -- console-based interface
-   **Multithreaded Communication**: Each client runs in its own thread,
    enabling simultaneous message sending and receiving.
-   **Exception Handling**: Custom exceptions for handling invalid
    formats, missing keys, or unknown configurations.
-   **Configuration Management**: Server configuration is handled
    through external configuration files (`server.conf`).
-   **Private Messaging**: Supports structured private messages between
    clients.

## Project Structure

    ChatClient/
    ├── src/com/chatapp/
    │   ├── client/
    │   │   ├── gui/
    │   │   │   └── GuiClient.java
    │   │   ├── ClientPeer.java
    │   │   └── TextClient.java
    │   ├── server/
    │   │   ├── config/
    │   │   │   └── ServerConfig.java
    │   │   ├── exceptions/
    │   │   │   ├── InvalidFormatException.java
    │   │   │   ├── MissingKeyException.java
    │   │   │   └── UnknownKeyException.java
    │   │   ├── Server.java
    │   │   └── ServerPeer.java
    │   ├── structs/
    │   │   ├── Main.java
    │   │   ├── Message.java
    │   └──private_message/structs/PrivateMessage.java
    └── server.conf

## Key Programming Concepts Demonstrated

This project highlights several **core Java programming techniques**:

1.  **Object-Oriented Programming (OOP)**
    -   Classes and objects are used to model server, client peers, and
        messages.
    -   Packages are structured logically (`client`, `server`,
        `structs`, `exceptions`).
2.  **Multithreading & Concurrency**
    -   Separate threads for listening and sending messages.
    -   Thread-safe communication between multiple clients and the
        server.
3.  **Exception Handling**
    -   Custom exceptions (`InvalidFormatException`,
        `MissingKeyException`, `UnknownKeyException`) showcase advanced
        error handling.
    -   Robustness against invalid configurations or malformed messages.
4.  **Networking with Java Sockets**
    -   Use of `Socket` and `ServerSocket` for TCP-based client-server
        communication.
    -   Handling multiple client connections dynamically.
5.  **GUI Development with Swing**
    -   `GuiClient` demonstrates GUI programming with event listeners
        and layout management.
    -   Separation of concerns between networking and user interface
        logic.
6.  **Configuration Management**
    -   `ServerConfig` loads parameters from `server.conf`, showing file
        I/O and parsing.
7.  **Code Organization**
    -   Modular design with packages for server, client, exceptions, and
        shared data structures.

## How to Run

### 1. Compile the Project

From the `ChatClient` root directory:

``` bash
javac -d bin src/com/chatapp/**/*.java
```

### 2. Start the Server

``` bash
java -cp bin com.chatapp.server.Server
```

### 3. Start a Client

For GUI client:

``` bash
java -cp bin com.chatapp.client.gui.GuiClient localhost 9000 User1
```

For text-based client:

``` bash
java -cp bin com.chatapp.client.TextClient localhost 9000 User2
```

### 4. Chat!

-   Multiple clients can connect to the server.
-   Messages are broadcasted to all connected users.
-   Private messages can be sent with the proper structure.

## Learning Outcomes

By completing this project, the following skills were developed and
demonstrated: - Building a **multithreaded client-server application**
in Java.
- Applying **OOP principles** in a real-world project.
- Designing and implementing **custom exception handling**.
- Creating a **Swing-based GUI** integrated with backend logic.
- Managing configurations and external files.
- Structuring a project into logical **packages and modules**.

This project serves as a foundation for larger-scale distributed
systems, real-time messaging apps, or networking-based software.
