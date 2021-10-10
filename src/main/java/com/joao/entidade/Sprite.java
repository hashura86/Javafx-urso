package com.joao.entidade;

import com.joao.manager.GraphicsManager;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Sprite {
    private GraphicsContext gc;
    private Image sprite;

    public double posX;
    public double posY;
    public double speed;
    public double height;
    public double width;

    public boolean visible = true;

    private double lastUpdate = 0; // última vez que o "handle()" foi chamado (Lembrando que ele se chama em 60 fps)
    private double delta = 0;
    private double elapsedTime = 0; // Quando tempo passou entre o último frame (lastUpdate) e o atual (now)

    public Color hitboxColor = Color.BLACK;

    public Sprite(double posX, double posY, Image sprite) {
        this.gc =  GraphicsManager.gc;
        this.posX = posX;
        this.posY = posY;
        this.sprite = sprite;

        this.height = this.sprite.getHeight();
        this.width = this.sprite.getWidth();
    }
    
    public void drawHitbox() {
        gc.setStroke(this.hitboxColor);
        gc.strokeRect(
            this.posX, 
            this.posY, 
            this.width != 0 ? this.width : this.sprite.getWidth(), 
            this.height != 0 ? this.height : this.sprite.getHeight() 
        );
    }

    public void render(long now) {
        if (this.lastUpdate != 0) {
            this.elapsedTime  = (now - this.lastUpdate) / 1_000_000_000.0; // 1 second = 1,000,000,000 (1 billion) nanoseconds
            this.delta = this.elapsedTime * this.speed;
            // this..posX += this.delta * dir; 
        }
        this.lastUpdate = now;

        if (this.width == 0 || this.height == 0)
            gc.drawImage(this.sprite, this.posX, this.posY);
        else
            gc.drawImage(this.sprite, this.posX, this.posY, this.width, this.height);

        
        // DEBUG
        this.drawHitbox();
        gc.fillText(String.valueOf(this.speed), (this.posX + this.width) / 2, this.posY - 30);
    }

    public void move(CharacterDirection direction) {
        if (direction == CharacterDirection.DOWN)
            this.posY += this.speed * this.delta;
        else
            this.posX += this.speed * (direction == CharacterDirection.LEFT? -1 : 1) * this.delta;
    }

    public boolean checkCollision(Sprite other) {
        return 
            other.posX < this.posX + this.width &&
            other.posX + other.width > this.posX &&
            other.posY < this.posY + this.height &&
            other.posY + other.height > this.posY;
    }
}
