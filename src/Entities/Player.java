package Entities;

import Utilities.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Utilities.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 20;
    private int playerStatus = IDLE;
    private boolean left, up, down, right;
    private boolean attacking = false;
    private boolean playerMoving = false;
    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerStatus][animationIndex], (int) x, (int) y, 128, 80, null);
    }

    private void setAnimation() {
        int animationIndex = playerStatus;

        if (playerMoving) playerStatus = RUNNING;
        else playerStatus = IDLE;

        if (attacking) playerStatus = ATTACK_1;

        if (animationIndex != playerStatus) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(playerStatus)) {
                attacking = false;
                animationIndex = 0;
            }
        }
    }

    private void updatePosition() {

        playerMoving = false;

        if (left && !right) {
            x -= playerSpeed;
            playerMoving = true;
        } else if (right && !left) {
            x += playerSpeed;
            playerMoving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            playerMoving = true;
        } else if (down && !up) {
            y += playerSpeed;
            playerMoving = true;
        }
    }

    private void loadAnimations() {

        BufferedImage image = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[9][6];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * 64, i * 40, 64, 40);
            }
        }
    }

    public void resetMovement() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttack(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
