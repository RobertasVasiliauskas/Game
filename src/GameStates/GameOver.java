package GameStates;

import Main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOver {
    private Playing playing;
    public GameOver(Playing playing) {
        this.playing = playing;
    }

    public void draw(Graphics2D graphics) {
        graphics.setColor(new Color(0,0,0,200));
        graphics.fillRect(0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        graphics.setColor(Color.white);
        graphics.drawString("Game Over", Game.GAME_WIDTH/2, 150);
        graphics.drawString("Press escape to enter menu", Game.GAME_WIDTH/2, 300);
    }

    public void keyPressed(KeyEvent key){
        if (key.getKeyCode() == KeyEvent.VK_ESCAPE){
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}
