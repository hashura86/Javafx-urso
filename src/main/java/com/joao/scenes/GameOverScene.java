package com.joao.scenes;

import com.joao.manager.AudioManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joao.manager.AssetManager;
import com.joao.media.Sound;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class GameOverScene extends GameScene {
    private Image imBackground = AssetManager.GAME_OVER_2;
    // private List<String> credits = new ArrayList<String>();
    private Map<String, List<String>> credits = new HashMap<>();

    @Override
    public void init() {
        AudioManager.getInstance().playMusic(Sound.GAME_OVER);
    }

    @Override
    public void render(long now) {
        this.gc.drawImage(imBackground, 0, 0, canvas.getWidth(), canvas.getHeight());
        this.gc.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 50));
        this.gc.setFill(Color.GREEN);
        this.gc.fillText("pontos: 10", 120, 100);
    }

    @Override
    public void cleanUp() {
        AudioManager.getInstance().stop();
    }

    private void initCredits() {
        
        /*
            programmer diretor
            programmers
            sound director
            soound programmer
            music
            
            level design
            urso design

            graphic director
            graphic design
            2D Artists
            
            game director
            game designer
            
            ui design
            menu button effect director
            
            fruta programming
            urso motion director
            urso voice director
            
            playtesters
            special thanks

            all rights, including copyright of game
            scenario, music and program
            reserved by GRUPO JOÃO (c)

            the end
        */
    }

}