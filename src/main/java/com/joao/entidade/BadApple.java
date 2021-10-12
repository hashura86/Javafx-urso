package com.joao.entidade;

import com.joao.manager.AssetManager;

public class BadApple extends Collectable {
    public BadApple(double posX, double posY) {
        super(AssetManager.BAD_APPLE, posX, posY, true);
        this.points = 0;
        this.damage = 1;
    }
}
