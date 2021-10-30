package com.joao.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public String name;
    public String id;

    public Player(Socket client) {
        this.client = client;
    }

    public void setStreams() throws IOException {
        this.in = new ObjectInputStream(client.getInputStream());
        this.out = new ObjectOutputStream(client.getOutputStream());
    }

    public Message readPlayer() throws IOException, ClassNotFoundException{
        return (Message) this.in.readObject();
    }
    
    public synchronized void writePlayer(Message message) throws IOException{
        // this.out.reset();
        this.out.writeObject(message);
        this.out.flush();
    }
    
    
    public void disconnect() {
        try {
            this.client.close();
        } catch(IOException e) {
            System.out.println("Jogador disconectou");
        }
    }
    
    public Socket getSocket() {
        return this.client;
    }
}
