package com.chatapp.structs;

public class Main {
    public static void main(String[] args) {
        Message message1 = new Message("Alice", "Salut!");
        Message message2 = new Message("Bob", "Ce faci?");
        Message message3 = new Message("Charlie", "Hai pe call!");

        System.out.println(message1);
        System.out.println(message2);
        System.out.println(message3);

    }
}
