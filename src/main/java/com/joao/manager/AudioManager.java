package com.joao.manager;

import java.util.HashMap;

import com.joao.media.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class AudioManager {
    private final String audioPath = "assets/audio/";
    private static AudioManager instance;
    private HashMap<Sound, Media> audioList;
    private MediaPlayer player;

    private AudioManager() {
        this.audioList = new HashMap<>();
        this.audioList.put(Sound.HOMAGE, this.loadMusic("Mild_High_Club_Homage.mp3"));
        this.audioList.put(Sound.CATS_ON_MARS, this.loadMusic("The_Seatbelts_-_Cats_on_Mars.mp3"));
        this.audioList.put(Sound.CATS_ON_MARS10S, this.loadMusic("URSO.mp3"));
        this.audioList.put(Sound.BONK, this.loadMusic("BONK.mp3"));
        this.audioList.put(Sound.e, this.loadMusic("e.mp3"));
        this.audioList.put(Sound.GAME_OVER, this.loadMusic("gameOverMusic.mp3"));
        this.audioList.put(Sound.AUDIO_TESTE, this.loadMusic("audio_teste.mp3"));
        this.audioList.put(Sound.EAT, this.loadMusic("eat.wav"));
        this.audioList.put(Sound.PAUSE, this.loadMusic("pause.mp3"));
        this.audioList.put(Sound.UNPAUSE, this.loadMusic("unpause2.mp3"));

        // remover nulos
        // for (Song song : this.musicList.keySet()) {
        //     Media media = this.musicList.get(song);

        //     if (media == null)
        //         this.musicList.remove(song);
        // }
    }

    public static AudioManager getInstance() {
        if(instance == null)
            instance = new AudioManager();
        
       return instance;
    }

    public MediaPlayer getMediaPlayer() {
        return this.player;
    }

    public void playSound(Sound sound) {
        this.playSound(sound, 0.5);
    }

    public void playSound(Sound sound, double volume) {
        Media media = this.audioList.get(sound);

        if (media == null) {
            System.out.println("Erro ao tocar " + sound.name());
            return;
        }

        MediaPlayer mp = new MediaPlayer(media);
        mp.setVolume(volume);
        mp.setAutoPlay(true);
        mp.play();
    }

    public void playMusic(Sound sound) {
        this.play(sound, 0.2, true);
    }

    public void playMusic(Sound sound, float volume) {
        this.play(sound, volume, true);
    }

    private void play(Sound sound, double volume, boolean loop) {
        Media media = this.audioList.get(sound);

        if (media == null) {
            System.out.println("Erro ao tocar " + sound.name());
            return;
        }

        this.player = new MediaPlayer(media);
        this.player.setVolume(volume);
        this.player.setAutoPlay(true);
        this.player.play();

        if(loop) {
            this.player.setOnEndOfMedia(new Runnable() {    
                public void run() {
                    player.seek(Duration.ZERO); 
            }
            });
        }
    }

    public void stop() {
        if(this.player == null) return;
        this.player.stop();
    }

    public void pause() {
        if(this.player == null) return;
        this.player.pause();
    }

    public void resume() {
        if(this.player == null) return;
        if(this.player.getStatus() != Status.PAUSED) return;
        
        this.player.play();
    }

    private Media loadMusic(String songName) {
        try {
            return new Media( getClass().getClassLoader().getResource(this.audioPath + songName).toString() );
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    
}
