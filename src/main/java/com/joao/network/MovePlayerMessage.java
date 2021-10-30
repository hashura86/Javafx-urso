package com.joao.network;

import com.joao.entidade.CharacterDirection;

// import com.shido.characterutils.CharacterDirection;

public class MovePlayerMessage extends Message {
    public static final long serialVersionUID = 1L;

    public String playerID;
    public CharacterDirection direction;
    public double speed;

    public MovePlayerMessage(String playerID) {
        // super(MessageType.MOVE_PLAYER);
        this.playerID = playerID;
    }

    public MovePlayerMessage(String id, CharacterDirection direction) {
        // super(MessageType.MOVE_PLAYER);
        this.playerID = id;
        this.direction = direction;
    }

    @Override
    public String toString() {
        String messageString = super.toString();
        return String.format(messageString + " [PlayerID] %s [Direction] %s" , this.playerID, this.direction.name());
    }
}
