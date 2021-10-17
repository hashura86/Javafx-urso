package com.joao.scenes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.joao.manager.AssetManager;
import com.joao.manager.AudioManager;
import com.joao.manager.FileManager;
import com.joao.manager.SceneManager;
import com.joao.media.Sound;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MenuScene extends GameScene {
    private final double BTN_X = (this.canvas.getWidth() / 2) - 100;
    private final double BTN_Y = (this.canvas.getHeight() / 2) + 100;
    private final int BTN_WIDTH = 256;
    private final int BTN_HEIGHT = 256;
    
    private Image imBackground = AssetManager.MENU_BACKGROUND;
    private Image imButton = AssetManager.BTN_ICEBERG;
    private double mouseX;
    private double mouseY;

    @Override
    public void init() {
        AudioManager.getInstance().playMusic(Sound.HOMAGE);
        this.initMouse();
        // Map<String, Integer> scores = FileManager.getSavedScores();
    }

    @Override
    public void render(long now) {
        this.gc.setFill(Color.WHITE);
        this.gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getWidth());
        this.gc.drawImage(imBackground, 0, 0, canvas.getWidth(), canvas.getHeight());
        
        if (this.checkMouseCollision()) {
            this.gc.drawImage(this.imButton, BTN_X-40, BTN_Y-40, BTN_WIDTH + BTN_WIDTH*0.2, BTN_HEIGHT + BTN_HEIGHT*0.2);
        } else {
            this.gc.drawImage(this.imButton, BTN_X, BTN_Y);
        }
    }

    @Override
    public void cleanUp() {
        AudioManager.getInstance().stop();
    }

    public boolean checkMouseCollision() {
        return 
            mouseX > BTN_X &&
            mouseX < BTN_X + BTN_WIDTH &&
            mouseY > BTN_Y &&
            mouseY < BTN_Y + BTN_HEIGHT;
    }

    private void initMouse() {
        EventHandler<MouseEvent> onMouseClick = new EventHandler<>() {
            public void handle(MouseEvent e) {
                if (checkMouseCollision()) {
                    AudioManager.getInstance().playSound(Sound.e, 0.5);
                }

                SceneManager.getInstance().changeScene( new UrsoGameScene() );
            }
       };

        EventHandler<MouseEvent> onMouseMouve = new EventHandler<>() {
            public void handle(MouseEvent e) {
               mouseX = e.getX();
               mouseY = e.getY();
           }
       };

       this.canvas.setOnMouseMoved(onMouseMouve);
       this.canvas.setOnMousePressed(onMouseClick);
    }
    
}
