package Game.Inputs;

import GameStates.Gamestate;
import Main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static Utilities.Constants.Directions.*;

public class KeyBoardInputs implements KeyListener {
    private GamePanel gamePanel;
    public KeyBoardInputs(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state){
            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyPressed(e);
            }
            case MENU -> {
                gamePanel.getGame().getMenu().keyPressed(e);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state){
            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyReleased(e);
            }
            case MENU -> {
                gamePanel.getGame().getMenu().keyReleased(e);
            }
        }
    }
}
