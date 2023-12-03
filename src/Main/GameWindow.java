package Main;

import javax.swing.*;

public class GameWindow extends JFrame {

    int WIDTH = 1300;
    int HEIGHT = 900;

    public GameWindow(String title, GamePanel gamePanel) {
        super(title);
        buildGUI(gamePanel);
    }

    private void buildGUI(GamePanel gamePanel) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gamePanel);
        setResizable(false);
        pack();
    }

}
