package com.joao.scenes;

import java.io.IOException;

import com.joao.manager.NetworkManager;
import com.joao.manager.SceneManager;
import com.joao.network.GameStartMessage;
import com.joao.network.Message;

import javafx.scene.paint.Color;

public class LobbyScene extends GameScene {

    @Override
    public void init() {
        this.readServer();
    }

    @Override
    public void render(long now) {
        this.gc.setFill(Color.BLACK);
        this.gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getWidth());
        this.gc.setFill(Color.WHITE);
        this.gc.fillText("Você está no lobby, meu chapa" , (this.canvas.getWidth()/2) - 150, this.canvas.getHeight()/2);
    }

    @Override
    public void cleanUp() {
        // TODO Auto-generated method stub
        
    }
    
    private void readServer() {
        Thread t = new Thread() {
            boolean isRunning = true;

            public void run() {
                while(isRunning) {
                    try {
                        Message m = NetworkManager.getInstance().readMessage();

                        if (m instanceof GameStartMessage) {
                            SceneManager.getInstance().changeScene( new UrsoGameScene() );
                        }
                    } catch (IOException ioe) {
                        if(ioe.getMessage().contains("Connection reset")) {
                            System.out.println("Desconectado do servidor");
                            NetworkManager.getInstance().isConnected = false;
                            isRunning = false;
                        } else 
                            ioe.printStackTrace();
                            // System.out.println(e.getMessage());
                    } catch(ClassNotFoundException cnfe) {

                    }
                }
            }
        };
        t.start();
    }
}
