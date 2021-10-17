package com.joao.scenes;

import com.joao.manager.GraphicsManager;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameScene {
    protected GraphicsContext gc;
    protected Canvas canvas;

    public GameScene() {
        this.gc = GraphicsManager.gc;
        this.canvas = GraphicsManager.canvas;
    }

    /**
     * Chamado quando uma GameScene é carregada
     */
    public abstract void init();

    /**
     * Método principal de renderização da GameScene. O Loop de renderização
     */
    public abstract void render(long now); 

    /**
     * Chamado quando a GameScene é removida
     */
    public abstract void cleanUp();
}
