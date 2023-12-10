package Entities;

import GameStates.Playing;
import Utilities.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Utilities.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] image;
    private ArrayList<Crabby> enemies = new ArrayList<>();
    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        enemies = LoadSave.getCrabs();
    }

    public void update(int[][] level, Player player){
        for (Crabby enemy : enemies)
            enemy.update(level, player);
    }

    public void draw(Graphics g, int levelXOffset){
        drawEnemies(g, levelXOffset);
    }
    private void drawEnemies(Graphics g, int levelXOffset) {
        for (Crabby enemy : enemies) {
            g.drawImage(image[enemy.getEnemyStatus()][enemy.getAnimationIndex()],(int)enemy.getHitBox().x - levelXOffset - CRABBY_DRAWOFFSET_X + enemy.flipX(), (int)enemy.getHitBox().y - CRABBY_DRAWOFFSET_Y, CRABBY_WIDTH * enemy.flipY(), CRABBY_HEIGHT, null);
        }
    }

    private void loadEnemyImages() {
        image = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.CRAB);
        for(int i = 0; i < image.length; i++){
            for (int j = 0; j < image[i].length; j++) {
                image[i][j] = temp.getSubimage(j * CRABBY_WIDTH_DEFAULT, i * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
            }
        }
    }
}
