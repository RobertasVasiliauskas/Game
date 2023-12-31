package Main;

import javax.swing.*;
import GameStates.Gamestate;
import GameStates.Playing;
import GameStates.Menu;

import java.awt.*;

public class Game implements Runnable {
    private GamePanel gamePanel;
    private GameWindow gameWindow;

    private int update = 0;
    private int frames = 0;

    private Playing playing;
    private Menu menu;

    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1.5f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE =(int)(TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;



    private void startGame() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public Game() {
        SwingUtilities.invokeLater(() -> {
            initClasses();

            gamePanel = new GamePanel(this);
            gameWindow = new GameWindow("Its Boshy time", gamePanel);
            gameWindow.setLocationRelativeTo(null);
            gameWindow.setVisible(true);
            gamePanel.requestFocusInWindow();
            startGame();
        });
    }

    private void initClasses() {
        playing = new Playing(this);
        menu = new Menu(this);
    }

    public void update() {
        switch (Gamestate.state){
            case PLAYING -> {
                playing.update();
            }
            case MENU -> {
                menu.update();
            }
        }
    }

    public void render(Graphics g) {
        switch (Gamestate.state){
            case PLAYING -> {
                playing.draw(g);
            }
            case MENU -> {
                menu.draw(g);
            }
        }
    }

    @Override
    public void run() {
        int fpsCap = 120;
        int upsCap = 200;
        double timePerFrame = 1000000000.0 / (double) fpsCap;
        double timePerUpdate = 1000000000.0 / (double) upsCap;

        long previousTime = System.nanoTime();
        double deltaUpdate = 0;
        double deltaFrames = 0;

        long lastCheck = System.currentTimeMillis();

        while (true) {
            long currentTime = System.nanoTime();

            deltaUpdate += (currentTime - previousTime) / timePerUpdate;
            deltaFrames += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaUpdate >= 1) {
                update();
                update++;
                deltaUpdate--;
            }

            if (deltaFrames >= 1) {
                gamePanel.repaint();
                deltaFrames--;
                frames++;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("Fps:" + frames + " |  UPS:" + update);
                frames = 0;
                update = 0;
            }
        }
    }

    public void windowFocusLost(){
        if (Gamestate.state == Gamestate.PLAYING){
            playing.getPlayer().resetMovement();
        }
    }

    public Menu getMenu(){
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }
}
