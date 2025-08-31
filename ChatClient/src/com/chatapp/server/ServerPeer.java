package com.chatapp.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.chatapp.structs.Message;
import com.private_message.structs.PrivateMessage;

public class ServerPeer {
    private final Socket socket;

    public ServerPeer(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            while (true) {
                try {
                    Object obj = in.readObject();
                    if (obj instanceof Message) {
                        Message msg = (Message) obj;
                        System.out.println(msg.toString());
                            
                        if (msg instanceof PrivateMessage) {
                            PrivateMessage pvmsg = (PrivateMessage) msg;
                            System.out.println("Recipient: " + pvmsg.getRecipient());
                        }
                    } else {
                        System.out.println("Unknown object type received");
                    }
                } catch (EOFException _eof) {
                    System.out.println("Client disconnected.");
                    break;
                } catch (ClassNotFoundException _cnfe) {
                    System.err.println("Received unknown object type: " + _cnfe.getMessage());
                }
            }
        } catch (IOException _ioe) {
            System.err.println("I/O error: " + _ioe.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException _ioe) {
                System.err.println("Failed to close socket: " + _ioe.getMessage());
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
