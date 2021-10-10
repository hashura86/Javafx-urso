package com.joao.entidade;

import com.joao.manager.AssetManager;

public class Fish extends Collectable {
    public Fish(double posX, double posY) {
        super(AssetManager.FISH, posX, posY, false);
        this.points = 100;
        this.damage = 0;
    }
}
