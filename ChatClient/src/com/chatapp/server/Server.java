package com.chatapp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.chatapp.server.config.ServerConfig;
import com.chatapp.server.exceptions.*;

public class Server {
    public static void main(String[] args) {
        try{
            ServerConfig config = new ServerConfig();
            System.out.println("Server starting on port " + config.getTcpPort());
            ServerSocket serverSocket = new ServerSocket(config.getTcpPort());

            System.out.print("Listening for inc. connections... ");
            Socket socket = serverSocket.accept();
            System.out.println("connected!");

            ServerPeer peer = new ServerPeer(socket);
            peer.run();

            socket.close();
            serverSocket.close();
        } catch(IOException | InvalidFormatException | MissingKeyException | UnknownKeyException e){
            System.out.println("Communication problem: " + e.getMessage());
        }
    }
}
