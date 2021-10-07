package com.joao.entidade;

import javafx.scene.canvas.GraphicsContext;

public class Urso extends Sprite {
    // private final String URSO_PATH = "assets/urso_01.png";
    public int hp = 1;

    public Urso(GraphicsContext gc, double posX, double posY) {
        super(gc, posX, posY, "assets/urso_01.png");
        this.speed = 25;
        this.height = 111;
        this.width = 80;
    }
    
    // public void move(CharacterDirection direction) {
    //     this.posX += this.speed * (direction == CharacterDirection.LEFT? -1 : 1);
    // }
}
