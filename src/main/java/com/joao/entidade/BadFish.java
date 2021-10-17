package com.joao.entidade;

import com.joao.manager.AssetManager;

public class BadFish extends Collectable {
    public BadFish(double posX, double posY) {
        super(AssetManager.BAD_FISH, posX, posY, false);
        this.points = 0;
        this.damage = 1;
    }
}
