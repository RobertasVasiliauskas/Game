package GameStates;

import Entities.EnemyManager;
import Entities.Player;
import Levels.LevelManager;
import Main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements Statemethods {
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private Player player;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
        player.loadLevel(levelManager.getLevelData().getLevelData());
    }

    @Override
    public void update() {
        levelManager.update();
        player.update();
        enemyManager.update(levelManager.getLevelData().getLevelData(), player);
    }

    @Override
    public void draw(Graphics graphics) {
        levelManager.draw(graphics, player.getLevelXOffset());
        player.render(graphics, player.getLevelXOffset());
        enemyManager.draw(graphics, player.getLevelXOffset());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            player.setAttack(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_SPACE -> player.setJump(true);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D -> player.setRight(false);
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_SPACE -> player.setJump(false);
        }
    }

    public void windowFocusLost() {
        player.resetMovement();
    }

    public Player getPlayer() {
        return player;
    }

    public void resetAll() {
    }
}
