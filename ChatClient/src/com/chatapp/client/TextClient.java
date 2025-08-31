package com.chatapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class TextClient {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
        
        System.out.print("Enter your username: ");
        String username = reader.readLine();

        Socket clientSocket = new Socket("127.0.0.1", 9000);

        ClientPeer client = new ClientPeer(username, clientSocket);

        System.out.println("Type /q to quit, or /w <user> <message> for private messages.");
        String line;
        while((line = reader.readLine()) != null) {
            if(line.equals("/q")) {
                System.out.println("Exiting...");
                break;                
            }
            
            if(line.startsWith("/w")) {
                String[] parts = line.split(" ", 3);
                if(parts.length >= 3) {
                    String recipient = parts[1].trim();
                    String content = parts[2].trim();
                    client.sendMessage(content, recipient);
                } else {
                    System.out.println("Invalid private message format. Use: /w <user> <message>");
                }
            } else {
                client.sendMessage(line.trim());
            }
        }
        client.close();
        } catch (IOException _ioe) {
            System.err.println("An I/O error occurred: " + _ioe.getMessage());
        }
    }
}
