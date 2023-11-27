import javax.swing.*;

public class GameWindow extends JFrame {

    int WIDTH = 1200;
    int HEIGHT = 900;

    public GameWindow(String title){
        super(title);
        buildGUI();
    }

    private void buildGUI(){
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();

        add(gamePanel);
    }

}
