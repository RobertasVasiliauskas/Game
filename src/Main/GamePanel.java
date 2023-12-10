package Main;

import Game.Inputs.KeyBoardInputs;
import Game.Inputs.MouseInputs;
import static Main.Game.GAME_HEIGHT;
import static Main.Game.GAME_WIDTH;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
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
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
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
