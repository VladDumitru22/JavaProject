package com.chatapp.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.chatapp.structs.Message;
import com.private_message.structs.PrivateMessage;

public class ClientPeer extends Thread{
    private final String username;
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    

    public ClientPeer(String username, Socket socket) throws IOException {
        this.username = username;
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(String message) {
        try{
            Message _msg = new Message(username, message);
            outputStream.writeObject(_msg);
            outputStream.flush();
        }catch(IOException _ioe){
            System.out.println("Unable to write object: " + _ioe.getMessage());
        }
    }

    public void sendMessage(String message, String recipient) {
        try {
            PrivateMessage _pvmsg = new PrivateMessage(recipient, username, message);
            outputStream.writeObject(_pvmsg);
            outputStream.flush();
        } catch (IOException _ioe) {
            System.out.println("Unable to write object: " + _ioe.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                Object obj = inputStream.readObject();
                if(obj instanceof Message _msg) {
                    System.out.println(_msg);
                }
            }
        } catch (IOException e) {
            System.out.println("Connection closed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Unknown object received: " + e.getMessage());
        }
    }

    public void close() {
        try {
            outputStream.close();
        } catch (IOException _ioe) {
            System.out.println("Unable to close ObjectOutputStream: " + _ioe.getMessage());
        }
        try {
            inputStream.close();
        } catch (IOException _ioe) {
            System.out.println("Unable to close ObjectInputStream: " + _ioe.getMessage());
        }
        try {
            socket.close();
        } catch (IOException _ioe) {
            System.out.println("Unable to close Socket: " + _ioe.getMessage());
        }
    }

    public String getSender() {
        return username;
    }

}
