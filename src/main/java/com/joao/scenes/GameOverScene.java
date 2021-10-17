package com.joao.scenes;

import com.joao.manager.AudioManager;
import com.joao.manager.FileManager;
import com.joao.manager.GraphicsManager;
import com.joao.manager.SceneManager;
import com.joao.manager.ScoreManager;

import java.util.Optional;

import com.joao.manager.AssetManager;
import com.joao.media.Sound;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class GameOverScene extends GameScene {
    private final double SCROLL_SPEED = 0.5;
    
    private Image imBackground = AssetManager.GAME_OVER_2;
    private Image creditsBackground =  AssetManager.CREDITS_BACKGROUND;
    private Image btnBack =  AssetManager.BTN_BACK;

    private double posY = 0;
    private Paint p;
    private boolean hasEndedCredits = false;
    private double theEndAlpha = 0f;
    private double mouseX;
    private double mouseY;

    @Override
public void init() {
        AudioManager.getInstance().playMusic(Sound.GAME_OVER);
        this.posY = GraphicsManager.canvas.getHeight();
        this.p = this.gc.getFill();
        this.initMouse();

        
        // É uma thread controlada pelo JavaFX para não dar problema com a thread de desenho
        // Platform.runLater(() -> { 
        //     String name = "";
        //     TextInputDialog dialog = new TextInputDialog("");
        //     dialog.setTitle("Digite o seu nome");
        //     dialog.setHeaderText(null);
        //     dialog.setGraphic(null);
        //     dialog.setContentText("Seu nome");


        //     Optional<String> result = dialog.showAndWait();

        //     if (result.isPresent()){
        //         name = result.get();

        //         if (name.isEmpty())
        //             name = "<Unknown>";
        //         else if(name.length() > 20)
        //             name = name.substring(0,20);
        //     } 

        //     FileManager.addScore(name, ScoreManager.score);
        // });
    }

    @Override
    public void render(long now) {
        this.gc.drawImage(imBackground, 0, 0, canvas.getWidth(), canvas.getHeight());
        
        // this.gc.setFill(Color.BLACK);
        // this.gc.fillRect(0, 0, GraphicsManager.canvas.getWidth(), GraphicsManager.canvas.getHeight());
        // this.gc.setFill(this.p);

        if(!this.hasEndedCredits) {
            this.gc.drawImage(creditsBackground, 0, this.posY, canvas.getWidth(), creditsBackground.getHeight());
            this.posY -= this.SCROLL_SPEED;
        } else {
            this.gc.setFill(Color.WHITE);
            this.gc.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 50));
            this.gc.setGlobalAlpha(this.theEndAlpha);
            this.gc.fillText("The End...?", (GraphicsManager.canvas.getWidth()/2) - 150, GraphicsManager.canvas.getHeight()/2);
            this.gc.setGlobalAlpha(1);
            this.gc.setFill(this.p);

            if(this.theEndAlpha <= 1) 
                this.theEndAlpha += 0.005;
        }
        this.gc.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 24));
        this.gc.setFill(Color.AQUAMARINE);
        this.gc.fillText("Pontos: " + ScoreManager.score, this.canvas.getWidth() - 200, 50);

        if(this.posY <= -1460) {
            this.hasEndedCredits = true;
        }

        this.drawBackButton();
    }

    @Override
    public void cleanUp() {
        AudioManager.getInstance().stop();
       this.canvas.setOnMouseMoved(null);
       this.canvas.setOnMousePressed(null);
    }

    private void drawBackButton() {
        if (checkMouseCollision(
            canvas.getWidth() - btnBack.getWidth(), 
            (canvas.getHeight()/2) + 230, 
            btnBack.getWidth(), 
            btnBack.getHeight()
        )) {
            this.gc.setEffect(new BoxBlur());
        }        
        this.gc.drawImage(this.btnBack, this.canvas.getWidth() - this.btnBack.getWidth(), (this.canvas.getHeight()/2) + 230);
        this.gc.setEffect(null);
    }

    public boolean checkMouseCollision(double x, double y, double w, double h) {
        return 
            mouseX > x &&
            mouseX < x + w &&
            mouseY > y &&
            mouseY < y + h;
    }

    private void initMouse() {
        EventHandler<MouseEvent> onMouseClick = new EventHandler<>() {
            public void handle(MouseEvent e) {
                if (checkMouseCollision(
                    canvas.getWidth() - btnBack.getWidth(), 
                    (canvas.getHeight()/2) + 230, 
                    btnBack.getWidth(), 
                    btnBack.getHeight()
                )) {
                    SceneManager.getInstance().changeScene(new MenuScene());
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