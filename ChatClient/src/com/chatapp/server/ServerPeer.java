package com.chatapp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.chatapp.structs.Message;

public class ServerPeer extends Thread{
    private final Server server;
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private String username;

    public ServerPeer(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void run() {
        try {
            while (true) {
                Object obj = inputStream.readObject();
                if (obj instanceof Message msg) {
                    this.username = msg.getSender();
                    server.dispatch(msg, this);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client disconnected: " + username);
        } finally {
            server.removeClient(this);
            try { socket.close(); } catch (IOException e) {}
        }
    }

    public synchronized void sendMessage(Message msg) {
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Unable to send message to " + username + ": " + e.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }
}
