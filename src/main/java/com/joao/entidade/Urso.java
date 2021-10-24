package com.joao.entidade;

import com.joao.manager.AssetManager;

import javafx.scene.image.Image;

public class Urso extends Sprite {
    public int hp = 3;
    public int score = 0;
    
    public Urso(double posX, double posY, UrsoColor color) {
        super(posX, posY, null);
        this.speed = 20;
        this.height = 111;
        this.width = 80;

        Image[] ursos = AssetManager.getUrsoByColor(color);
        
        this.sprite = ursos[0];

        this.addAnimation("left", new Image[] {
            ursos[0],
            ursos[1],
        });

        this.addAnimation("right", new Image[] {
            ursos[2],
            ursos[3],
        });

        // this.addAnimation("left", new Image[] {
        //     AssetManager.URSO_LEFT_1,
        //     AssetManager.URSO_LEFT_2
        // });
        // this.addAnimation("right", new Image[] {
        //     AssetManager.URSO_RIGHT_1,
        //     AssetManager.URSO_RIGHT_2,
        // });
    }

    public Urso(double posX, double posY) {
        this(posX, posY, UrsoColor.DEFAULT);
    }
}
   

