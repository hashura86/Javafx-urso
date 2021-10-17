package com.joao.entidade;

import java.util.HashMap;
import java.util.Map;

import com.joao.manager.GraphicsManager;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Sprite {
    private GraphicsContext gc;
    private Image sprite;
    // private Image animations[];
    private Map<String, Image[]> animations = new HashMap<>();

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

    boolean isAnimating = false;
    int frameDelay = 5;
    int frameCount = 0;
    int curFrame = 0;
    int maxFrame = 2;
    CharacterDirection dir;

    String currentAnimation = "";

    public Sprite(double posX, double posY, Image sprite) {
        this.gc =  GraphicsManager.gc;
        this.posX = posX;
        this.posY = posY;
        this.sprite = sprite;

        this.height = this.sprite.getHeight();
        this.width = this.sprite.getWidth();
    }

    // public Sprite(double posX, double posY, Map<String, Image[]> animations) {
    //     this(posX, posY, (Image) animations.values().toArray()[0]);
    //     this.animations = animations;
    //     this.currentAnimation = (String) animations.keySet().toArray()[0];

    //     System.out.println(this.currentAnimation);
    //     System.out.println(this.sprite.getUrl());
    // }

    // public void setAnimations(Image sprites[]) {
    //     this.animations = sprites;
    // }
    
    public void drawHitbox() {
        gc.setStroke(this.hitboxColor);
        gc.strokeRect(
            this.posX, 
            this.posY, 
            this.width,
            this.height
        );
    }

    public void render(long now) {
        // TODO: fazer o delta
        if (this.lastUpdate != 0) {
            this.elapsedTime  = (now - this.lastUpdate) / 1_000_000_000.0; // 1 second = 1,000,000,000 (1 billion) nanoseconds
            this.delta = this.elapsedTime * this.speed;
            // this..posX += this.delta * dir; 
        }

        if(this.animations.size() > 0 && this.isAnimating) { //Tô em fase de animação?
            if(++this.frameCount >= this.frameDelay) {   //Delay para não percorrer todas as animações de uma vez
                this.curFrame += 1;     //
                if(this.curFrame >= this.maxFrame)
                    this.curFrame = 0;
            
                this.frameCount = 0;
            }

            // if (!this.currentAnimation.isEmpty())
            this.sprite = this.animations.get(this.currentAnimation)[this.curFrame];
            // this.sprite = this.animations[this.curFrame];
        }

        this.lastUpdate = now;
        
        gc.drawImage(this.sprite, this.posX, this.posY, this.width, this.height);

        this.isAnimating = false;
        
        // DEBUG
        // this.drawHitbox();
        // gc.fillText(String.valueOf(this.speed), (this.posX + this.width) / 2, this.posY - 30);
    }

    public void move(CharacterDirection direction) {
        this.isAnimating = true;

        switch (direction) {
            case DOWN:
                this.posY += this.speed * this.delta;
                break;
            case LEFT:
                this.posX -= this.speed * this.delta;
                this.currentAnimation = "left";
                break;
                case RIGHT:
                this.posX += this.speed * this.delta;
                this.currentAnimation = "right";
                break;
            default:
                break;
        }
        this.dir = direction;

    }

    public boolean checkCollision(Sprite other) {
        return 
            other.posX < this.posX + this.width &&
            other.posX + other.width > this.posX &&
            other.posY < this.posY + this.height &&
            other.posY + other.height > this.posY;
    }

    public void addAnimation(String animationKey, Image sprites[]) {
        this.animations.put(animationKey, sprites);

        if (this.currentAnimation.isEmpty())
            this.currentAnimation = animationKey;
    }

}
