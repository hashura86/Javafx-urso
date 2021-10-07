package com.joao.entidade;

import javafx.scene.canvas.GraphicsContext;

public class Collectable extends Sprite {
    public int points;
    public int damage;
    public boolean isBad;

    public Collectable(GraphicsContext gc, double posX, double posY, boolean isBad) {
        super(gc, posX, posY, isBad? "assets/maçãPodre.png" : "assets/maçã.png");
        this.isBad = isBad;
        // this.speed = isBad? 5 : 10;
        this.points = isBad? 0 : 100;
        this.damage = isBad? 1 : 0;

    }
    
    
    // public void move(CharacterDirection direction) {
    //     this.posX += this.speed * (direction == CharacterDirection.LEFT? -1 : 1);
    // }
}
