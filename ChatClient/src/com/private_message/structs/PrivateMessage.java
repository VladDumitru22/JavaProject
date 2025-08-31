package com.private_message.structs;

import com.chatapp.structs.Message;

public final class PrivateMessage extends Message{
    private final String recipient;

    public PrivateMessage(String recipient, String sender, String content) {
        super(sender, content);
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    @Override
    public String toString() {
        return "(priv) " + getSender() + ": " + getContent(); 
    }
}
