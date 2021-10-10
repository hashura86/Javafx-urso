package com.joao.entidade;

import com.joao.manager.AssetManager;

public class Urso extends Sprite {
    // private final String URSO_PATH = "assets/urso_01.png";
    public int hp = 3;
    public int score = 0;

    public Urso(double posX, double posY) {
        super(posX, posY, AssetManager.URSO);
        this.speed = 25;
        this.height = 111;
        this.width = 80;
    }
    
    // public void move(CharacterDirection direction) {
    //     this.posX += this.speed * (direction == CharacterDirection.LEFT? -1 : 1);
    // }
}

