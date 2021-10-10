package com.joao.entidade;

import com.joao.manager.AssetManager;

import javafx.scene.image.Image;

public class Collectable extends Sprite {
    public int points;
    public int damage;
    public boolean isBad;

    public Collectable(Image sprite, double posX, double posY, boolean isBad) {
        super(posX, posY, sprite);
        this.isBad = isBad;
        this.speed = 10;
    }
    
    
    // public void move(CharacterDirection direction) {
    //     this.posX += this.speed * (direction == CharacterDirection.LEFT? -1 : 1);
    // }
}
