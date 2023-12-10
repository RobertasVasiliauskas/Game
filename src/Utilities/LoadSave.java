package Utilities;

import Entities.Crabby;
import Main.Game;

import static Utilities.Constants.EnemyConstants.*;

import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoadSave {
    public static final String PLAYER_ATLAS = "Player/player_sprites.png";
    public static final String LEVEL_ATLAS = "Level/LevelAtlas.png";
    public static final String LEVEL_ONE = "Level/Level_one_data.png";
    public static final String LEVEL_ONE_LONG = "Level/Level_one_data_long.png";
    public static final String CRAB = "Enemy/crabby_sprite.png";
    public static final String STATUS_BAR = "Player/health_power_bar.png";
    public static BufferedImage getSpriteAtlas(String filename){
        BufferedImage image = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static ArrayList<Crabby> getCrabs(){
        BufferedImage img = getSpriteAtlas(LEVEL_ONE_LONG);
        ArrayList<Crabby> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == CRABBY){
                    list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        return list;
    }
    public static int[][] getLevelData(){
        BufferedImage img = getSpriteAtlas(LEVEL_ONE_LONG);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;
    }
}



















