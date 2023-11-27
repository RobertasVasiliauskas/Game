import javax.swing.*;

public class Game {
    public Game(){
        SwingUtilities.invokeLater(()->{
            GameWindow gameWindow = new GameWindow("Its boshy time");
            gameWindow.setVisible(true);
        });
    }
}
