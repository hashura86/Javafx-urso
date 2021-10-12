package com.joao;

import java.util.Random;

import com.joao.entidade.CharacterDirection;
import com.joao.entidade.Collectable;
import com.joao.entidade.Urso;
import com.joao.manager.AssetManager;
import com.joao.manager.AudioManager;
import com.joao.manager.CollectableManager;
import com.joao.manager.GraphicsManager;
import com.joao.media.Sound;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UrsoGame {
    private final int CANVAS_WIDTH = 816;
    private final int CANVAS_HEIGHT = 638;
    
    private Canvas canvas;
    
    private Image imBackground = AssetManager.BACKGROUND;
    private Urso urso;
    int seconds = 0;
    private Timeline timeline;
    private boolean isGameover = false;

    private final double SPEED_MULTIPLIER = 0.2;
    private final int SPEED_CHANGE_TIME = 10;

    public UrsoGame(Stage stage) {
        this.canvas = new Canvas();
        GraphicsManager.gc = this.canvas.getGraphicsContext2D();
        this.canvas.setWidth(this.CANVAS_WIDTH);
        this.canvas.setHeight(this.CANVAS_HEIGHT); 
        this.urso = new Urso(100, 480);
        
        Group root = new Group();
        Scene scene = new Scene(root, this.canvas.getWidth(), this.canvas.getHeight()); 
        // SceneManager.getInstance().changeScene( new MenuScene() );

        root.getChildren().add( this.canvas );
        stage.setScene(scene);
        stage.show();

        this.initKeyboard(scene);
        AudioManager.getInstance().playMusic(Sound.CATS_ON_MARS10S);
    }

    public void run() {
        int offsetWidth = 80;
        Random rand = new Random();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
            if (seconds % SPEED_CHANGE_TIME == 0) {
                CollectableManager.getInstance().setSpeedMultiplier(CollectableManager.getInstance().speedMultiplier + SPEED_MULTIPLIER);
            }

            for(int i = 0; i < rand.nextInt(3); i++)
                CollectableManager.getInstance().createRandomCollectable(0, (int) canvas.getWidth() - offsetWidth);
        }));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        new AnimationTimer() {
            
            @Override
            public void handle(long now) { // now é dado em nanosegundos
                if (isGameover)
                    return;
                
                GraphicsManager.gc.setFill(Color.WHITE);
                GraphicsManager.gc.fillRect(0, 0, canvas.getWidth(), canvas.getWidth());
                GraphicsManager.gc.drawImage(imBackground, 0, 0, canvas.getWidth(), canvas.getHeight());
                urso.render(now);
                
                for (Collectable c : CollectableManager.getInstance().getCollectables()) {
                    if (!c.visible)
                        return;
                    
                    c.render(now);
                    c.move(CharacterDirection.DOWN);

                    if (c.checkCollision(urso)) {
                        c.visible = false;
                        urso.hitboxColor = Color.RED;

                        new Thread() {

                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);
                                    urso.hitboxColor = Color.BLACK;
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                
                            }
                          }.start();

                        urso.hp -= c.damage;
                        urso.score += c.points;
    
                        if (c.isBad) {
                            AudioManager.getInstance().playSound(Sound.BONK);
                        }

                    } else {
                        if (c.posY >= canvas.getHeight())
                            c.visible = false;
                    }
                }

                if (urso.hp <= 0) {
                    GraphicsManager.gc.setFont(new Font(32));
                    GraphicsManager.gc.fillText("Perdeu", canvas.getWidth()/2, canvas.getHeight()/2);
                    AudioManager.getInstance().stop();
                    isGameover = true;
                }
                
                CollectableManager.getInstance().getCollectables().removeIf(c -> !c.visible);

                for (int i = 0; i < 3; i++)  {
                    GraphicsManager.gc.drawImage(AssetManager.WHITE_HEART, ( 40 * i ) + 10, 36, 40, 40);
                }

                for (int i = 0; i < urso.hp; i++)  {
                    GraphicsManager.gc.drawImage(AssetManager.RED_HEART, ( 40 * i ) + 10, 36, 40, 40);
                }


                GraphicsManager.gc.setFont(new Font(32));
                // GraphicsManager.gc.fillText("Vida:" + urso.hp, 320, 36);
                GraphicsManager.gc.fillText("Pontuação:" + urso.score, 320, 36);

                GraphicsManager.gc.fillText("Tempo: " + String.valueOf(seconds), 320, 68);

                // if (lastUpdate != 0) {
                //     elapsedTime  = (now - lastUpdate) / 1_000_000_000.0; // 1 second = 1,000,000,000 (1 billion) nanoseconds
                //     delta = elapsedTime * speed;

                //     if (urso.posX >= canvas.getWidth() - 80) {
                //         dir = -1;
                //         speed *= 2;
                //     }
                //     if (urso.posX <= 0) {
                //         dir = 1;
                //         speed *= 2;
                //     }
                //     urso.posX += delta * dir; 
                // }
                // lastUpdate = now;
            }

        }.start();
    }

    private void initKeyboard(Scene scene) {
        this.canvas.setFocusTraversable(true); // Necessário para fazer com que o evento de teclado funcione no canvas

        this.canvas.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            switch(e.getCode()) {
                case A:
                    if (this.urso.posX >= 0)
                        this.urso.move(CharacterDirection.LEFT);
                    break;
                case D:
                    if (!(this.urso.posX + this.urso.width >= this.CANVAS_WIDTH))
                        this.urso.move(CharacterDirection.RIGHT);
                    break;
                default:
                    break;
            }
        });
    }

}
