package com.joao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.joao.entidade.CharacterDirection;
import com.joao.entidade.Collectable;
import com.joao.entidade.Urso;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UrsoGame {
    private final int CANVAS_WIDTH = 816;
    private final int CANVAS_HEIGHT = 638;
    private final String BACKGROUND_PATH = "/assets/bg.png";
    
    private Stage stage;
    private Canvas canvas;
    private GraphicsContext gc;
    
    private Image imBackground = new Image(BACKGROUND_PATH);
    private Urso urso;
    int seconds = 0;
    List<Collectable> collectables = new ArrayList<Collectable>();

    public UrsoGame(Stage stage) {
        this.stage = stage;
        this.canvas = new Canvas();
        this.canvas.setWidth(this.CANVAS_WIDTH);
        this.canvas.setHeight(this.CANVAS_HEIGHT);
        this.gc = this.canvas.getGraphicsContext2D();
        this.urso = new Urso(this.gc, 100, 480);
        // GameEngine.gc = this.canvas.getGraphicsContext2D();
        // GraphicsManager.getInstance().gc = this.canvas.getGraphicsContext2D();
        // GraphicsManager.getInstance().canvas = this.canvas;

        Group root = new Group();
        Scene scene = new Scene(root, this.canvas.getWidth(), this.canvas.getHeight()); 
        // SceneManager.getInstance().changeScene( new MenuScene() );

        root.getChildren().add( this.canvas );
        stage.setScene(scene);
        stage.show();

        this.initKeyboard(scene);

    }

    public void run() {
        Random rand = new Random();
        int offsetWidth = 80;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;

            int nextPosX = (int) Math.round( rand.nextDouble() * canvas.getWidth() - offsetWidth );
            collectables.add(new Collectable(gc, nextPosX , 100, rand.nextBoolean()));
            collectables.get(collectables.size() - 1).speed = (int) Math.round( rand.nextDouble() * (30 - 10) + 10 );
        }));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        new AnimationTimer() {
            double lastUpdate = 0; // última vez que o "handle()" foi chamado (Lembrando que ele se chama em 60 fps)
            double delta = 0;
            double elapsedTime = 0; // Quando tempo passou entre o último frame (lastUpdate) e o atual (now)
            int dir = 1; // direção
            int speed = 100;
            boolean isGameover = false;
            // List<Collectable> collectables = new ArrayList<Collectable>() {{
            //     add(new Collectable(gc, 100, 100, false));
            //     add(new Collectable(gc, 150, 100, true));
            //     add(new Collectable(gc, 200, 100, false));
            // }};

            
            @Override
            public void handle(long now) { // now é dado em nanosegundos
                if (isGameover)
                    return;
                
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getWidth());
                gc.drawImage(imBackground, 0, 0, canvas.getWidth(), canvas.getHeight());
                urso.render(now);
                
                for (Collectable c : collectables) {
                    if (!c.visible)
                        return;
                    
                    c.render(now);
                    c.move(CharacterDirection.DOWN);

                    if (c.checkCollision(urso)) {
                        c.visible = false;

                        if(c.isBad)
                            urso.hp -= c.damage;
                    } else {
                        if (c.posY >= canvas.getHeight())
                        c.visible = false;
                        // c.posY = 100;
                    }
                }

                if (urso.hp <= 0) {
                    gc.setFont(new Font(32));
                    gc.fillText("si fudeu", 100, 100);
                    isGameover = true;
                }
                
                collectables.removeIf(c -> !c.visible);
                gc.setFont(new Font(32));
                gc.fillText("Vida:" + urso.hp, 320, 36);
                // gc.fillText(String.valueOf(seconds), 320, 36);

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
                    this.urso.move(CharacterDirection.LEFT);
                    break;
                case D:
                    this.urso.move(CharacterDirection.RIGHT);
                    break;
            }
        });
    }

}
