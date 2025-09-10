package com.chatapp.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

import com.chatapp.client.ClientPeer;

public class GuiClient {

    private JLabel mSenderNameLabel;
    private JTextField mSenderTextField;
    private JButton mChangeSenderButton;
    private JTextArea mMessageDisplayTextArea;
    private JTextField mMessageTextField;
    private JButton mSendMessageButton;
    private JScrollPane mMessageAreaScrollPane;

    private JFrame frame;
    private ClientPeer clientPeer;

    public GuiClient(Socket socket, String initialUsername) throws IOException {
        clientPeer = new ClientPeer(initialUsername, socket);

        frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel topPanel = new JPanel();
        mSenderNameLabel = new JLabel("Name:");
        mSenderTextField = new JTextField(initialUsername, 10);
        mChangeSenderButton = new JButton("Change Name");
        topPanel.add(mSenderNameLabel);
        topPanel.add(mSenderTextField);
        topPanel.add(mChangeSenderButton);
        frame.add(topPanel, BorderLayout.NORTH);

        mMessageDisplayTextArea = new JTextArea(15, 30);
        mMessageDisplayTextArea.setEditable(false);
        mMessageAreaScrollPane = new JScrollPane(mMessageDisplayTextArea);
        frame.add(mMessageAreaScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        mMessageTextField = new JTextField(20);
        mSendMessageButton = new JButton("Send");
        bottomPanel.add(mMessageTextField);
        bottomPanel.add(mSendMessageButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        clientPeer.setOutputPane(mMessageDisplayTextArea);

        mChangeSenderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newName = mSenderTextField.getText().trim();
                if (!newName.isEmpty()) {
                    clientPeer.setUsername(newName);
                }
            }
        });

        mSendMessageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessageFromField();
            }
        });

        mMessageTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessageFromField();
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void sendMessageFromField() {
        String text = mMessageTextField.getText().trim();
        if (text.isEmpty()) return;

        if (text.equals("/q")) {
            clientPeer.close();
            System.exit(0);
        } else if (text.startsWith("/w ")) {
            String[] parts = text.split(" ", 3);
            if (parts.length >= 3) {
                clientPeer.sendMessage(parts[2], parts[1]);
            }
        } else {
            clientPeer.sendMessage(text);
        }

        mMessageTextField.setText("");
    }

    public void display() {
        clientPeer.start();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java GuiClient <hostname> <port> <username>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String username = args[2];

        try {
            Socket socket = new Socket(host, port);
            GuiClient client = new GuiClient(socket, username);
            client.display();
        } catch (IOException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
