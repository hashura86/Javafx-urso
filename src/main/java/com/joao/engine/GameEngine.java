package com.joao.engine;

import com.joao.manager.GraphicsManager;
import com.joao.manager.SceneManager;
import com.joao.scenes.GameOverScene;
import com.joao.scenes.MenuScene;
import com.joao.scenes.UrsoGameScene;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class GameEngine {
    private final int CANVAS_WIDTH = 1280;
    private final int CANVAS_HEIGHT = 720;

    private Canvas canvas;

    public GameEngine(Stage stage) {
        this.canvas = new Canvas();
        this.canvas.setWidth(this.CANVAS_WIDTH);
        this.canvas.setHeight(this.CANVAS_HEIGHT); 
        GraphicsManager.gc = this.canvas.getGraphicsContext2D();
        GraphicsManager.canvas = this.canvas;
        
        Group root = new Group();
        Scene scene = new Scene(root, this.canvas.getWidth(), this.canvas.getHeight()); 

        // SceneManager.getInstance().changeScene( new GameOverScene());
        SceneManager.getInstance().changeScene( new MenuScene() );
        // SceneManager.getInstance().changeScene( new UrsoGameScene() );

        root.getChildren().add( this.canvas );
        stage.setScene(scene);
        stage.show();
    }

    public void run() {
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                SceneManager.getInstance().renderCurrentScene(now);
            }
        }.start();
    }
}