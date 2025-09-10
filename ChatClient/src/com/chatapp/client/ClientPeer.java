package com.chatapp.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.chatapp.structs.Message;
import com.private_message.structs.PrivateMessage;

public class ClientPeer extends Thread {
    private String username;
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private JTextArea outputPane;

    public ClientPeer(String username, Socket socket) throws IOException {
        this.username = username;
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(String message) {
        try {
            Message msg = new Message(username, message);
            outputStream.writeObject(msg);
            outputStream.flush();
        } catch (IOException ioe) {
            appendToPane("Unable to send message: " + ioe.getMessage());
        }
    }

    public void sendMessage(String message, String recipient) {
        try {
            PrivateMessage pvmsg = new PrivateMessage(recipient, username, message);
            outputStream.writeObject(pvmsg);
            outputStream.flush();
        } catch (IOException ioe) {
            appendToPane("Unable to send private message: " + ioe.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = inputStream.readObject();
                if (obj instanceof Message) {
                    Message msg = (Message) obj;
                    appendToPane(msg.getSender() + ": " + msg.getContent());
                } else if (obj instanceof PrivateMessage) {
                    PrivateMessage pvmsg = (PrivateMessage) obj;
                    appendToPane("[Private] " + pvmsg.getSender() + " -> " + pvmsg.getRecipient() + ": " + pvmsg.getContent());
                } else {
                    appendToPane("Unknown object received from server");
                }
            }
        } catch (IOException e) {
            appendToPane("Connection closed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            appendToPane("Unknown object received: " + e.getMessage());
        } finally {
            close();
        }
    }

    private void appendToPane(final String text) {
        if (outputPane != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    outputPane.append(text + "\n");
                }
            });
        } else {
            System.out.println(text);
        }
    }

    public void close() {
        try {
            outputStream.close();
        } catch (IOException ioe) {
            System.out.println("Unable to close ObjectOutputStream: " + ioe.getMessage());
        }
        try {
            inputStream.close();
        } catch (IOException ioe) {
            System.out.println("Unable to close ObjectInputStream: " + ioe.getMessage());
        }
        try {
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Unable to close Socket: " + ioe.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOutputPane(JTextArea pane) {
        this.outputPane = pane;
    }
}
