package com.joao;

import com.joao.engine.GameEngine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("URSO");
        GameEngine engine = new GameEngine(stage);
        engine.run();
        
        // Desabilita a maximização da janela
        stage.maximizedProperty().
            addListener((observable, oldValue, newValue) -> {
                if (newValue)
                    stage.setMaximized(false);
                }
            );
    
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
