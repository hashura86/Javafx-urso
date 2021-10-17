package com.joao.manager;

import com.joao.scenes.*;

public class SceneManager {
    private static SceneManager instance;
    private GameScene curScene;

    private SceneManager() {}

    public static SceneManager getInstance() {
        if(instance == null)
            instance = new SceneManager();
        
       return instance;
    }

    public void changeScene(GameScene scene) {
        if(curScene != null)
            curScene.cleanUp();
        
        curScene = scene;
        curScene.init();
        
    }
    
    public GameScene getCurrentScene() {
        return curScene;
    }

    public void renderCurrentScene(long now) {
        if (this.getCurrentScene() != null)
            this.getCurrentScene().render(now);
    }
}
