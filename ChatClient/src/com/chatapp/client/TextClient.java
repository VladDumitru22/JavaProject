package com.chatapp.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class TextClient {
public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            Socket socket = new Socket("127.0.0.1", 9000);
            ClientPeer client = new ClientPeer(username, socket);
            client.start();

            while (true) {
                String line = scanner.nextLine();
                if (line.equals("/q")) break;

                if (line.startsWith("/w ")) {
                    String[] parts = line.split(" ", 3);
                    if (parts.length == 3) {
                        client.sendMessage(parts[2], parts[1]);
                    } else {
                        System.out.println("Invalid private message format: /w <recipient> <message>");
                    }
                } else {
                    client.sendMessage(line);
                }
            }

            client.close();
            scanner.close();
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
