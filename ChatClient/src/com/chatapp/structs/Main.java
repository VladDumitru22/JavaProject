package com.chatapp.structs;

import com.private_message.structs.PrivateMessage;

import java.io.IOException;

import com.chatapp.server.exceptions.*;
import com.chatapp.server.config.ServerConfig;

public class Main {
    public static void main(String[] args) {
        Message message1 = new Message("Alice", "Salut!");
        Message message2 = new Message("Bob", "Ce faci?");
        Message message3 = new Message("Charlie", "Hai pe call!");

        System.out.println(message1);
        System.out.println(message2);
        System.out.println(message3);

        PrivateMessage privateMessage1 = new PrivateMessage("Bob", "Alice", "Salut doar tie!");
        PrivateMessage privateMessage2 = new PrivateMessage("Charlie", "Bob", "Info confidential");
        PrivateMessage privateMessage3 = new PrivateMessage("Alice", "Charlie", "Mesaj secret");

        System.out.println(privateMessage1);
        System.out.println(privateMessage2);
        System.out.println(privateMessage3);

        try {
            ServerConfig config = new ServerConfig("server.conf");

            System.out.println("TCP_PORT: " + config.getTcpPort());
            System.out.println("MAX_CLIENTS: " + config.getMaxClients());
        } catch (IOException | InvalidFormatException | UnknownKeyException | MissingKeyException e){
            System.err.println("ERROR: " + e);
        }
    }
}
