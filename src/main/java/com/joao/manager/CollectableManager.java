package com.joao.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.joao.entidade.Apple;
import com.joao.entidade.BadApple;
import com.joao.entidade.Collectable;

public class CollectableManager {
    private static CollectableManager instance;
 
    private final int Y_SPAWN = -50; // Local onde vai spawnar os collectables
    private final double BASE_SPEED = 10;

    private List<Collectable> collectables;
    public double speedMultiplier = 0;

    private CollectableManager() {
        this.collectables = new ArrayList<>();
    }
    
    public static CollectableManager getInstance() {
        if(instance == null)
            instance = new CollectableManager();
        
       return instance;
    }
    
    public void reset() {
        this.collectables.clear();
        this.speedMultiplier = 0;
    }

    public void addCollectable(Collectable collectable) {
        this.collectables.add(collectable);
    }

    public List<Collectable> getCollectables() {
        return this.collectables;
    }

    public void createRandomCollectable(int minValue, int maxValue) {
        Random rand = new Random();
        double goodCollectableTreshold = 0.6;
        Collectable collectable;
        
        int nextPosX = (int) Math.round( rand.nextDouble() * ( maxValue - minValue ) + minValue );
        if (rand.nextDouble() <= goodCollectableTreshold) // spawnou boa
            collectable = new Apple(nextPosX , Y_SPAWN);
        else
            collectable = new BadApple(nextPosX , Y_SPAWN);
        // collectables.get(collectables.size() - 1).speed = (int) Math.round( rand.nextDouble() * (30 - 10) + 10 );
        // collectable.speed = this.speed;
        // collectable.speed *= this.speedMultiplier;
        collectable.speed += collectable.speed * this.speedMultiplier;
        this.addCollectable(collectable);
    }

    public void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
        this.collectables.forEach(c -> c.speed = BASE_SPEED + BASE_SPEED * this.speedMultiplier);
        double rate = AudioManager.getInstance().getMediaPlayer().getRate();

        AudioManager.getInstance().getMediaPlayer().setRate(rate + 0.02);
    }
}
