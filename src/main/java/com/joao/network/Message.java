package com.joao.network;

import java.io.Serializable;

public abstract class Message implements Serializable {
    public static final long serialVersionUID = 1L;

    // public MessageType type;   
    // public String playerID;

    // public Message(MessageType type) {
    //     this.type = type;
    // }

    // public Message(MessageType type, String playerID) {
    //     this.type = type;
    //     this.playerID = playerID;
    // }

    // @Override
    // public String toString() {
    //     return String.format("[Type] %s", this.type.toString());
    // }
}
