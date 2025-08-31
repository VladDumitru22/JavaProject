package com.chatapp.server.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.chatapp.server.exceptions.*;

public final class ServerConfig {
    private final int tcpPort;
    private final int maxClients;

    public ServerConfig(String configFile) throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
        Integer tcpPort = null;
        Integer maxClients = null;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            int lineNumber = 0;
            while((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if(line.isEmpty() || line.startsWith("#")) continue;
                
                String[] parts = line.split("=", 2);
                if(parts.length != 2) {
                    throw new InvalidFormatException("Invalid format at line " + lineNumber + ": " + line);
                }
                String key = parts[0].trim();
                String value = parts[1].trim();

                if(key.equals("TCP_PORT")) {
                    try {
                        tcpPort = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        throw new InvalidFormatException("Invalid TCP_PORT at line " + lineNumber);
                    }
                } else if (key.equals("MAX_CLIENTS")) {
                    try {
                        maxClients = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        throw new InvalidFormatException("Invalid MAX_CLIENTS at line " + lineNumber);
                    }
                } else {
                    throw new UnknownKeyException("Unknown config parameter at line " + lineNumber + ": " + key);
                }
            }
        }

        if(tcpPort == null) {
            throw new MissingKeyException("Missing TCP_PORT config parameter");
        }
        if(maxClients == null) {
            throw new MissingKeyException("Missing MAX_CLIENTS config parameter");
        }

        this.tcpPort = tcpPort;
        this.maxClients = maxClients;
    }

    public ServerConfig() throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
        this("server.conf");
    }

    public int getTcpPort() {
        return tcpPort;
    }
    
    public int getMaxClients() {
        return maxClients;
    }
}
