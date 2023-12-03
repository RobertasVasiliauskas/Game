package Main;

import javax.swing.*;
import Entities.*;

import java.awt.*;

public class Game implements Runnable {
    private GamePanel gamePanel;
    private GameWindow gameWindow;

    private int update = 0;
    private int frames = 0;

    Player player;

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
        player = new Player(100,100);
    }

    public void update() {
        player.update();
    }

    public void render(Graphics g) {
        player.render(g);
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
        player.resetMovement();
    }

    public Player getPlayer(){
        return player;
    }

}
