package com.chatapp.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.chatapp.structs.Message;
import com.private_message.structs.PrivateMessage;

public class ClientPeer {
    private final String sender;
    private final Socket socket;
    private final ObjectOutputStream objectStream;
    

    public ClientPeer(String sender, Socket socket) throws IOException {
        this.sender = sender;
        this.socket = socket;
        this.objectStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void sendMessage(String message) {
        try{
            Message _msg = new Message(sender, message);
            objectStream.writeObject(_msg);
            objectStream.flush();
        }catch(IOException _ioe){
            System.out.println("Unable to write object: " + _ioe.getMessage());
        }
    }

    public void sendMessage(String message, String recipient) {
        try {
            PrivateMessage _pvmsg = new PrivateMessage(recipient, sender, message);
            objectStream.writeObject(_pvmsg);
            objectStream.flush();
        } catch (IOException _ioe) {
            System.out.println("Unable to write object: " + _ioe.getMessage());
        }
    }

    public void close() {
        try {
            objectStream.close();
        } catch (IOException _ioe) {
            System.out.println("Unable to close ObjectOutputStream: " + _ioe.getMessage());
        }
        try {
            socket.close();
        } catch (IOException _ioe) {
            System.out.println("Unable to close Socket: " + _ioe.getMessage());
        }
    }

    public String getSender() {
        return sender;
    }

    public Socket getSocket() {
        return socket;
    }
}
