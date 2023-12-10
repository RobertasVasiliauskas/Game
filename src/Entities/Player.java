package Entities;

import Main.Game;
import Utilities.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static Utilities.Constants.PlayerConstants.*;
import static Utilities.helpMethods.*;


public class Player extends Entity {

    private BufferedImage[][] animations;
    private int animationTick;
    private int animationIndex;
    private final int animationSpeed = 20;
    private int playerStatus = IDLE;
    private boolean left, up, down, right, jump;
    private boolean attacking = false;
    private boolean playerMoving = false;
    private float playerSpeed = 2.0f;
    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    //jumping, gravity
    private float airSpeed = 0.0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    //level camera

    private int levelXOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int levelTileWide = LoadSave.getLevelData()[0].length;
    private int maxTileOffset = levelTileWide - Game.TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTileOffset * Game.TILES_SIZE;

    private int flipX = 0;
    private int flipY = 1;


    //status bar ui

    private BufferedImage statusBarImage;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    // attack possible

    private Rectangle2D.Float attackBox;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();

        initHitBox(x, y, (int)(20*Game.SCALE), (int)(27*Game.SCALE));
        initAttackBox();
    }

    public void update() {
        updateHealth();
        updateAttackBox();

        updatePosition();
        updateAnimationTick();
        setAnimation();

        checkCloseToBoarder(this);
    }

    private void updateAttackBox() {
        if(right){
            attackBox.x = hitBox.x + hitBox.width+ (int)(Game.SCALE * 10);
        }else if(left){
            attackBox.x = hitBox.x - hitBox.width - (int)(Game.SCALE * 10);
        }
        attackBox.y = hitBox.y + (int)(Game.SCALE *10);
    }

    private void updateHealth() {
        healthWidth = (int) ((currentHealth / (float)maxHealth) * healthBarWidth);
    }

    private void initAttackBox(){
        attackBox = new Rectangle2D.Float(x, y, (int)(20*Game.SCALE), (int)(20*Game.SCALE));
    }

    private void checkCloseToBoarder(Player player) {
        int playerX = (int) player.getHitBox().x;
        int diff = playerX - levelXOffset;

        if (diff > rightBorder){
            levelXOffset += diff - rightBorder;
        }
        else if (diff < leftBorder){
            levelXOffset += diff - leftBorder;
        }
        if(levelXOffset > maxLevelOffsetX){
            levelXOffset = maxLevelOffsetX;
        }
        else if(levelXOffset < 0){
            levelXOffset = 0;
        }

    }

    public int getLevelXOffset(){
        return levelXOffset;
    }

    public void render(Graphics g, int levelOffset) {
        g.drawImage(animations[playerStatus][animationIndex], (int) (hitBox.x - xDrawOffset) - levelOffset + flipX , (int) (hitBox.y - yDrawOffset), width * flipY, height, null);

        drawUI(g);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImage, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    public void setHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0){
            currentHealth = 0;
            //gameOver();
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    private void setAnimation() {
        int animationIndex = playerStatus;

        if (playerMoving) playerStatus = RUNNING;
        else playerStatus = IDLE;

        if (inAir) {
            if (airSpeed < 0)
            playerStatus = JUMPING;
            else if(airSpeed > 0) playerStatus = RUNNING;
        }

        if (attacking) playerStatus = ATTACK;

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

        if (jump)
            jump();
        if (!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipY = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipY = 1;
        }

        if (!inAir)
            if (!noEntityOnFloor(hitBox, levelData))
                inAir = true;

        if (inAir) {
            if (canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = getEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        playerMoving = true;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = getEntityXPosNextToWall(hitBox, xSpeed);
        }

    }

    private void loadAnimations() {
        BufferedImage image = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[7][8];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = image.getSubimage(j * 64, i * 40, 64, 40);
            }
        }

         statusBarImage = LoadSave.getSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLevel(int[][] level) {
        this.levelData = level;
        if (!noEntityOnFloor(hitBox, levelData)) inAir = true;
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
