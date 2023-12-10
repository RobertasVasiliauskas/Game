package Entities;

import Main.Game;

import java.awt.geom.Rectangle2D;

import static Utilities.Constants.Directions.LEFT;
import static Utilities.Constants.Directions.RIGHT;
import static Utilities.Constants.EnemyConstants.*;
import static Utilities.helpMethods.*;

public abstract class Enemy extends Entity {
    protected int animationIndex, enemyStatus, enemyType;
    protected int animationTick, animationSpeed = 25;
    protected float runningSpeed = 0.35f * Game.SCALE;
    protected int runningDirection = LEFT;
    protected boolean firstUpdate = true;
    protected boolean inAir = false;
    protected float fallSpeed = 0;
    protected float gravity = 0.04f * Game.SCALE;
    protected int tileY;
    protected int attackDistance = Game.TILES_SIZE;

    protected int maxHealth;
    protected int currentHealth;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitBox(x, y, width, height);
        maxHealth = getMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    protected void firstUpdateCheck(int[][] level) {
        if (!noEntityOnFloor(hitBox, level)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void inAirUpdate(int[][] level) {
        if (canMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, level)) {
            hitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitBox.y = getEntityYPosUnderRoofOrAboveFloor(hitBox, fallSpeed);
            tileY = (int) (hitBox.y / Game.TILES_SIZE);
        }
    }
    protected void move(int[][] level) {
        float xSpeed = 0;

        if (runningDirection == LEFT) {
            xSpeed = -runningSpeed;
        } else {
            xSpeed = runningSpeed;
        }

        if (canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, level))
            if (isFloor(hitBox, xSpeed, level)) {
                hitBox.x += xSpeed;
                return;
            }

        changeRunningDirection();
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(enemyType, enemyStatus)) {
                animationIndex = 0;
                if (enemyStatus == ATTACK) enemyStatus = IDLE;
            }
        }
    }

    protected void changeRunningDirection() {
        if (runningDirection == LEFT)
            runningDirection = RIGHT;
        else runningDirection = LEFT;
    }

    protected void setEnemyStatus(int enemyStatus){
        this.enemyStatus = enemyStatus;
        animationTick = 0;
        animationIndex = 0;
    }

    protected boolean canSeePlayer(int[][] level, Player player){
        int playerTileY = (int)(player.getHitBox().y / Game.TILES_SIZE);
        if(playerTileY == tileY){
            if (isPlayerInRange(player)){
                return isPathClear(level, hitBox, player.hitBox, tileY);
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player){
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= attackDistance;
    }

    protected void turnTowardsPlayer(Player player){
        if(player.hitBox.x > hitBox.x)runningDirection = RIGHT;
        else runningDirection = LEFT;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyStatus() {
        return enemyStatus;
    }

}
