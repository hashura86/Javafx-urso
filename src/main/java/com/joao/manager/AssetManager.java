package com.joao.manager;

import java.util.List;

import com.joao.entidade.UrsoColor;

import javafx.scene.image.Image;


public class AssetManager {
    ////////////////////////////// UI //////////////////////////////
    ///////////// BACKGROUND
    public static final Image BACKGROUND = new Image("/assets/ui/bg.png");
    public static final Image MENU_BACKGROUND = new Image("/assets/ui/URSOSMENUALT.png");
    public static final Image GAME_OVER = new Image("/assets/ui/gameOverkk.png");
    public static final Image GAME_OVER_2 = new Image("/assets/ui/GameOver.png");
    public static final Image CREDITS_BACKGROUND = new Image("assets/ui/credits.png");

    
    ///////////// BUTTONS
    public static final Image BTN_BOTAO = new Image("/assets/ui/botão.png");
    public static final Image BTN_ICEBERG = new Image("/assets/ui/botaonew.png");
    public static final Image BTN_BACK = AssetManager.BTN_BOTAO;

    
    ////////////////////////////// URSO //////////////////////////////
    public static final Image URSO_LEFT_1 = new Image("assets/character/default/urso_l01.png");
    public static final Image URSO_LEFT_2 = new Image("assets/character/default/urso_l02.png");
    public static final Image URSO_RIGHT_1 = new Image("assets/character/default/urso_r01.png");
    public static final Image URSO_RIGHT_2 = new Image("assets/character/default/urso_r02.png");

    ////////////////////////////// COLLECTABLES //////////////////////////////
    ///////////// COLLECTABLES
    
    public static final Image BAD_APPLE = new Image("assets/collectables/maçãPodre.png");
    public static final Image APPLE = new Image("assets/collectables/maçã.png");
    public static final Image FISH = new Image("assets/collectables/pexe.png");
    public static final Image BAD_FISH = new Image("assets/collectables/pexeqdadano.png");

    ///////////// UI
    public static final Image RED_HEART = new Image("assets/elements/red_heart.png");
    public static final Image WHITE_HEART = new Image("assets/elements/white_heart.png");

    public static Image[] getUrsoByColor(UrsoColor color) {
        String[] animations = {
            "urso_l01.png",
            "urso_l02.png",
            "urso_r01.png",
            "urso_r02.png"
        };
        String path = "assets/character/" + color.toString().toLowerCase() + "/";

        try {
            return List.of(animations).stream().map(anim -> new Image(path + anim) ).toArray(Image[]::new);
        } catch (Exception e) {
            e.printStackTrace();
            return new Image[] {
                URSO_LEFT_1, URSO_LEFT_2, URSO_RIGHT_1, URSO_RIGHT_2
            };
        }
    }
}
