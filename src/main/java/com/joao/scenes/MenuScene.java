package com.joao.scenes;

import java.io.IOException;
import java.net.InetAddress;
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
import com.joao.manager.NetworkManager;
import com.joao.manager.SceneManager;
import com.joao.manager.ServerManager;
import com.joao.media.Sound;

import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MenuScene extends GameScene {
    private final int BTN_WIDTH = 256;
    private final int BTN_HEIGHT = 256;

    private final double BTN_CONNECT_X = 100;
    private final double BTN_CONNECT_Y = (this.canvas.getHeight() / 2) + 100;

    private final double BTN_HOST_X = this.canvas.getWidth() - BTN_WIDTH - 50;
    private final double BTN_HOST_Y = (this.canvas.getHeight() / 2) + 100;
    // private final double BTN_SINGLE_X = (this.canvas.getWidth() / 2) - 100;
    // private final double BTN_SINGLE_Y = (this.canvas.getHeight() / 2) + 100;
    
    
    private Image imBackground = AssetManager.MENU_BACKGROUND;
    private Image imButton = AssetManager.BTN_ICEBERG;
    private double mouseX;
    private double mouseY;
    ColorAdjust c;
    
    @Override
    public void init() {
        c = new ColorAdjust(); // creating the instance of the ColorAdjust effect.   
        c.setBrightness(0.2); // setting the brightness of the color.   
        c.setContrast(0.1); // setting the contrast of the color  
        c.setHue(0.3); // setting the hue of the color  
        c.setSaturation(0.45); // setting the hue of the color.   
        // c.setSaturation(0.45); // setting the hue of the color.   

        // AudioManager.getInstance().playMusic(Sound.HOMAGE);
        this.initMouse();
        // Map<String, Integer> scores = FileManager.getSavedScores();
    }

    @Override
    public void render(long now) {
        this.gc.setFill(Color.WHITE);
        this.gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getWidth());
        this.gc.drawImage(imBackground, 0, 0, canvas.getWidth(), canvas.getHeight());
        
        gc.setEffect(c);
        if (this.checkMouseCollision(BTN_CONNECT_X, BTN_CONNECT_Y, BTN_CONNECT_X + BTN_WIDTH, BTN_CONNECT_Y + BTN_HEIGHT)) {
            this.gc.drawImage(this.imButton, BTN_CONNECT_X-40, BTN_CONNECT_Y-40, BTN_WIDTH + BTN_WIDTH*0.2, BTN_HEIGHT + BTN_HEIGHT*0.2);
        } else {
            this.gc.drawImage(this.imButton, BTN_CONNECT_X, BTN_CONNECT_Y);
        }
        gc.setEffect(null);

        // (double hue, double saturation, double brightness, double contrast)
        gc.setEffect(new ColorAdjust(0.6, 1, 0.5, 0.5)); 
        if (this.checkMouseCollision(BTN_HOST_X, BTN_HOST_Y, BTN_HOST_X + BTN_WIDTH, BTN_HOST_Y + BTN_HEIGHT)) {
            this.gc.drawImage(this.imButton, BTN_HOST_X-40, BTN_HOST_Y-40, BTN_WIDTH + BTN_WIDTH*0.2, BTN_HEIGHT + BTN_HEIGHT*0.2);
        } else {
            this.gc.drawImage(this.imButton, BTN_HOST_X, BTN_HOST_Y);
        }
        gc.setEffect(null);
        // gc.setEffect(c);
        // if (this.checkMouseCollision()) {
        //     this.gc.drawImage(this.imButton, BTN_CONECT_X-40, BTN_CONNECT_Y-40, BTN_WIDTH + BTN_WIDTH*0.2, BTN_HEIGHT + BTN_HEIGHT*0.2);
        // } else {
        //     this.gc.drawImage(this.imButton, BTN_CONECT_X, BTN_CONNECT_Y);
        // }
        // gc.setEffect(null);
    }

    @Override
    public void cleanUp() {
        AudioManager.getInstance().stop();
        this.canvas.setOnMouseMoved(null);
        this.canvas.setOnMousePressed(null);
    }

    public boolean checkMouseCollision(double x, double y, double w, double h) {
        return
            mouseX > x &&
            mouseX < w &&
            mouseY > y &&
            mouseY < h;
    }

    // public boolean checkMouseCollision() {
    //     return 
    //         mouseX > BTN_CONECT_X &&
    //         mouseX < BTN_CONECT_X + BTN_WIDTH &&
    //         mouseY > BTN_CONNECT_Y &&
    //         mouseY < BTN_CONNECT_Y + BTN_HEIGHT;
    // }

    private void initMouse() {
        EventHandler<MouseEvent> onMouseClick = new EventHandler<>() {
            public void handle(MouseEvent e) {
                if (checkMouseCollision(BTN_CONNECT_X, BTN_CONNECT_Y, BTN_CONNECT_X + BTN_WIDTH, BTN_CONNECT_Y + BTN_HEIGHT)) { // CLIENT
                    AudioManager.getInstance().playSound(Sound.e, 0.5);
                    // SceneManager.getInstance().changeScene( new UrsoGameScene() );
                    String ip = "127.0.0.1";
                    try {
                        NetworkManager.getInstance().connect(ip, 6868);
                        SceneManager.getInstance().changeScene( new LobbyScene() );
                    } catch (IOException ioe) {
                        System.out.println("Erro ao conectar em " + ip + "\n" + ioe.getMessage());
                    }

                } else if (checkMouseCollision(BTN_HOST_X, BTN_HOST_Y, BTN_HOST_X + BTN_WIDTH, BTN_HOST_Y + BTN_HEIGHT)) { // HOST
                    try {
                        ServerManager.getInstance().start();
                    } catch (IOException ioe) {
                        System.out.println("Erro ao criar servidor\n" + ioe.getMessage());
                        // ServerManager.getInstance().stop();
                    }

                    if(ServerManager.getInstance().isActive) {
                        try {
                            NetworkManager.getInstance().connect("127.0.0.1", 6868);
                            SceneManager.getInstance().changeScene( new LobbyScene() );
                        } catch (IOException ioe) {
                            System.out.println("Erro ao conectar em Localhost\n" + ioe.getMessage());
                        }
                    }
                }   
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
