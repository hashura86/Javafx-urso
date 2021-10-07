package com.joao.entidade;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Urso {
    private final String URSO_PATH = "assets/urso_01.png";
    private GraphicsContext gc;
    private Image imUrso = new Image(URSO_PATH);
    
    public Urso(GraphicsContext gc) {
        this.gc = gc;
    }
    
    public void render(double posX, double posY) {
        gc.drawImage(this.imUrso, posX, posY, 80, 111);
    }
}
