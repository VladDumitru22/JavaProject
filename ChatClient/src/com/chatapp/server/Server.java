package com.chatapp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.chatapp.server.config.ServerConfig;
import com.chatapp.server.exceptions.*;
import com.chatapp.structs.Message;
import com.private_message.structs.PrivateMessage;

public class Server {
    private final int port;
    private final int maxClients;
    private final ServerSocket serverSocket;
    private final ArrayList<ServerPeer> clients = new ArrayList<>();

    public Server(int port, int maxClients) throws IOException {
        this.port = port;
        this.maxClients = maxClients;
        this.serverSocket = new ServerSocket(port);
    }

    public void listen() {
        System.out.println("Server listening on port " + port);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                synchronized(this) {
                    if (clients.size() >= maxClients) {
                        System.out.println("Max clients reached. Connection rejected.");
                        socket.close();
                        continue;
                    }
                }
                ServerPeer peer = new ServerPeer(this, socket);
                synchronized(this) { clients.add(peer); }
                peer.start();
                System.out.println("Client connected.");
            } catch (IOException e) {
                System.out.println("Error accepting client: " + e.getMessage());
            }
        }
    }

    public synchronized void dispatch(Message msg, ServerPeer senderPeer) {
        for (ServerPeer peer : clients) {
            if (msg instanceof PrivateMessage pm) {
                if (peer.getUsername().equals(pm.getRecipient()) || peer.getUsername().equals(pm.getSender())) {
                    peer.sendMessage(pm);
                }
            } else {
                peer.sendMessage(msg);
            }
        }
    }

    public synchronized void removeClient(ServerPeer peer) {
        clients.remove(peer);
        System.out.println("Client removed: " + peer.getUsername());
    }

    public static void main(String[] args) {
        try {
            ServerConfig config = new ServerConfig();
            Server server = new Server(config.getTcpPort(), config.getMaxClients());
            server.listen();
        } catch (IOException | InvalidFormatException | UnknownKeyException | MissingKeyException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
