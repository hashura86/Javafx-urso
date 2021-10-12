package com.joao.entidade;

import com.joao.manager.AssetManager;

public class Apple extends Collectable {
    public Apple(double posX, double posY) {
        super(AssetManager.APPLE, posX, posY, false);
        this.points = 100;
        this.damage = 0;
    }
}
