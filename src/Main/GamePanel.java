package Main;

import Game.Inputs.KeyBoardInputs;
import Game.Inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    float WIDTH = 1280, HEIGHT = 800;
    Game game;

    public GamePanel(Game game) {
        this.game = game;
        setBackground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        setPanelSize();

        addKeyListener(new KeyBoardInputs(this));
       addMouseListener(new MouseInputs(this));
        addMouseMotionListener(new MouseInputs(this));
    }

    private void setPanelSize() {
        setPreferredSize(new Dimension((int) WIDTH, (int) HEIGHT));
    }

    public void updateGame() {

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.render(g);
    }

    public Game getGame() {
        return game;
    }

}
