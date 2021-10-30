package com.joao.manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
// import com.joao.communication.HandshakeMessage;
// import com.joao.game.GameWorld;
// import com.joao.game.Player;
// import com.joao.logger.Logger;
// import com.joao.logger.LogLevel;
import java.util.ArrayList;
import java.util.List;

import com.joao.network.GameStartMessage;
import com.joao.network.Message;
import com.joao.network.MovePlayerMessage;
import com.joao.network.Player;

public class ServerManager {
    private static ServerManager instance;

    private int port = 6868;
    private ServerSocket server;

    private boolean isRunning = true;
    private Thread serverThread;
    private List<Player> players = new ArrayList<>();
    
    public boolean isActive = false;
    // GameWorld gameWorld;

    // public GameServer() {
    //     this.gameWorld = new GameWorld();
    // }

    public static ServerManager getInstance() {
        if(instance == null)
            instance = new ServerManager();
        
       return instance;
    }

    public void stop() {
        try {
            this.server.close();
        } catch(IOException ioe) {

        }

        this.isRunning = false;
        // if (this.serverThread.isAlive())
        //     this.serverThread.interrupt();
    }

    public void start() throws IOException {
        server = new ServerSocket(this.port);
        System.out.println( String.format( "*** Server Online ! (Port:%d) ***", port ));
        // Logger.log( String.format( "*** Server Online ! (Port:%d) ***", port ));
        this.isActive = true;
        serverThread = new Thread() {
            public void run() {
                try {
                    while (isRunning) {
                        Socket client = server.accept();
                        System.out.println("Nova conexão recebida: " + client.getRemoteSocketAddress().toString());
                        
                        Thread t = new Thread() {
                            public void run() {
                                handleClient(client);
                            }
                        };
                        t.start();
                    }
                } catch (IOException ioe) {
                    System.out.println("Erro server.accept()");
                    isRunning = false;
                }
            }
        };
        serverThread.start();
    }
    
    private void broadcast(Message m) {
        for(Player player : this.players) {
            try {
                player.writePlayer(m);
            } catch(Exception e) {
                e.printStackTrace();
                // Logger.log(LogLevel.ERROR, "Broadcast", e.getMessage());
                // removePlayerByID(player.id);
            }
        }
    }

    private void handleClient(Socket client) {
        Player player = new Player(client);
        
        try {
            player.setStreams();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }
        
        this.players.add(player);

        System.out.println("[INFO] Novo jogador !");

        if(this.players.size() == 2) { // pode começar
            this.stop();
            broadcast(new GameStartMessage() );
        }

        Thread playerThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Message m = player.readPlayer();
                        
                        if (m instanceof MovePlayerMessage) {
                            System.out.println("MovePlayerMessage");
                            broadcast(m);
                        }
                    } catch(Exception e) {
                        if(e.getMessage().contains("Connection reset")) {
                            System.out.println("[INFO] "+ String.format("Cliente %s (%s) desconectou !", player.name, player.id));
                            // removePlayerByID(player.id);
                            break;
                        } else {
                            e.printStackTrace();
                            // Logger.log(LogLevel.ERROR, "MovePlayer", e.getMessage());
                            // Logger.log(LogLevel.DEBUG, "Causado por:" + player.id);
                        }
                    }
                }
            }
        };
        // thread.setName(thread.getId() + "-player-" + player.id);
        playerThread.start();
        // Player player = new Player(clientConnection);
        
        // try {
        //     player.client.writePlayer( new HandshakeMessage(player.id));
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     Logger.log(LogLevel.ERROR, "HandleClient", e.getMessage());
        //     return;
        // }

        // gameWorld.addPlayer(player);
    }
}