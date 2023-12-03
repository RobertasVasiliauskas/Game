package Main;

import Game.Inputs.KeyBoardInputs;
import Game.Inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Utilities.Constants.Directions.*;
import static Utilities.Constants.PlayerConstants.*;

public class GamePanel extends JPanel {

    float WIDTH = 1280;
    float HEIGHT = 800;
    float dx = 150, dy = 300;
    private BufferedImage image;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerStatus = IDLE;
    private int playerDirection = -1;
    private boolean playerMoving = false;

    public GamePanel() {
        importImg();
        loadAnimations();

        setBackground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        setPanelSize();

        addKeyListener(new KeyBoardInputs(this));
        addMouseMotionListener(new MouseInputs());
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * 64, i * 40, 64, 40);
            }
        }

    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/Player/player_sprites.png");

        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        setPreferredSize(new Dimension((int) WIDTH, (int) HEIGHT));
    }

    private void setAnimation() {
        if (playerMoving) playerStatus = RUNNING;
        else playerStatus = IDLE;
    }

    public void setDirection(int direction) {
        this.playerDirection = direction;
        playerMoving = true;
    }

    public void setMove(boolean playerMoving) {
        this.playerMoving = playerMoving;
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(playerStatus)) {
                animationIndex = 0;
            }
        }
    }

    private void updatePosition() {
        if (playerMoving) {
            switch (playerDirection) {
                case LEFT:
                    dx -= 5;
                    break;
                case UP:
                    dy -= 5;
                    break;
                case RIGHT:
                    dx += 5;
                    break;
                case DOWN:
                    dy += 5;
                    break;
            }
        }

    }

    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.drawImage(animations[playerStatus][animationIndex], (int) dx, (int) dy, 256, 160, null);
    }
}
