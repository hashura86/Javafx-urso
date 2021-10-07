package com.joao;

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
import javafx.scene.paint.Color;
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

    public UrsoGame(Stage stage) {
        this.stage = stage;
        this.canvas = new Canvas();
        this.canvas.setWidth(this.CANVAS_WIDTH);
        this.canvas.setHeight(this.CANVAS_HEIGHT);
        this.gc = this.canvas.getGraphicsContext2D();
        this.urso = new Urso(this.gc);
        // GameEngine.gc = this.canvas.getGraphicsContext2D();
        // GraphicsManager.getInstance().gc = this.canvas.getGraphicsContext2D();
        // GraphicsManager.getInstance().canvas = this.canvas;

        Group root = new Group();
        Scene scene = new Scene(root, this.canvas.getWidth(), this.canvas.getHeight()); 
        // SceneManager.getInstance().changeScene( new MenuScene() );

        root.getChildren().add( this.canvas );
        stage.setScene(scene);
        stage.show();


    }

    public void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
        }));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        new AnimationTimer() {
            double posX = 100;
            double posY = 480;
            double lastUpdate = 0; // última vez que o "handle()" foi chamado (Lembrando que ele se chama em 60 fps)
            double delta = 0;
            double elapsedTime = 0; // Quando tempo passou entre o último frame (lastUpdate) e o atual (now)
            int dir = 1; // direção
            int speed = 100;
            
            @Override
            public void handle(long now) { // now é dado em nanosegundos
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getWidth());
                gc.drawImage(imBackground, 0, 0, canvas.getWidth(), canvas.getHeight());
                urso.render(posX, posY);

                gc.fillText(String.valueOf(seconds), 320, 36);

                if (lastUpdate != 0) {
                    elapsedTime  = (now - lastUpdate) / 1_000_000_000.0; // 1 second = 1,000,000,000 (1 billion) nanoseconds
                    delta = elapsedTime * speed;

                    if (posX >= canvas.getWidth() - 80) {
                        dir = -1;
                        speed *= 2;
                    }
                    if (posX <= 0) {
                        dir = 1;
                        speed *= 2;
                    }
                    posX += delta * dir; 
                }
                lastUpdate = now;
            }

        }.start();
    }

}
