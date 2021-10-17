package com.joao.entidade;

import java.util.HashMap;
import java.util.Map;

import com.joao.manager.AssetManager;

import javafx.scene.image.Image;

public class Urso extends Sprite {
    public int hp = 3;
    public int score = 0;

    public Urso(double posX, double posY) {
        super(posX, posY, AssetManager.URSO_RIGHT_1);
        this.speed = 25;
        this.height = 111;
        this.width = 80;

        this.addAnimation("left", new Image[] {
            AssetManager.URSO_LEFT_1,
            AssetManager.URSO_LEFT_2
        });
        this.addAnimation("right", new Image[] {
            AssetManager.URSO_RIGHT_1,
            AssetManager.URSO_RIGHT_2,
        });
    }
}
   

