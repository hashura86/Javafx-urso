package com.joao.manager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import com.joao.network.Message;

// import com.shido.communication.Message;

public class NetworkManager {
    private static NetworkManager instance;
    
    public Socket socket;
    
    private int port = 6868;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public boolean isConnected = false;
    private String ip;

    public static NetworkManager getInstance() {
        if(instance == null)
            instance = new NetworkManager();
        
       return instance;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public void connect() throws IOException {
        this.connect(this.ip, this.port);
    }

    public void connect(String ip) throws IOException {
        this.connect(ip, this.port);
    }

    public void connect(String ip, int port) throws IOException {
        // InetAddress host = InetAddress.getLocalHost();
        // System.out.println(host); // DESKTOP-81C04ND/192.168.0.12
        // System.out.println(InetAddress.getByName(null)); // localhost/127.0.0.1

        this.socket = new Socket(ip, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.isConnected = true;
        System.out.println("Conectado em: " + ip);
    }

    public void sendMessage(Message message) throws IOException {
        this.out.reset();
        this.out.writeObject(message);
        this.out.flush();
    }

    @SuppressWarnings("unchecked")
    public <T extends Message> T readMessage() throws IOException, ClassNotFoundException{
        if (this.in == null)
            this.in = new ObjectInputStream(this.socket.getInputStream());
        return (T)this.in.readObject();
    }
}