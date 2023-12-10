package Entities;

import Main.Game;

import java.awt.geom.Rectangle2D;

import static Utilities.Constants.Directions.*;
import static Utilities.Constants.EnemyConstants.*;

public class Crabby extends Enemy {
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitBox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y, (int)(82 * Game.SCALE), (int)(19 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE*30);
    }

    public void update(int[][] level, Player player) {
        updateMove(level, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitBox.x - attackBoxOffsetX;
        attackBox.y = hitBox.y ;
    }

    private void updateMove(int[][] level, Player player) {
        if (firstUpdate) firstUpdateCheck(level);
        if (inAir) inAirUpdate(level);
        else {
            switch (enemyStatus) {
                case IDLE -> {
                    setEnemyStatus(RUNNING);
                }
                case RUNNING -> {

                    if (canSeePlayer(level, player)) turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player)) setEnemyStatus(ATTACK);

                    move(level);
                }
            }
        }
    }

    public int flipX() {
        if (runningDirection == RIGHT) {
            return width;
        } else {
            return 0;
        }
    }

    public int flipY(){
        if (runningDirection == RIGHT) {
            return -1;
        } else {
            return 1;
        }
    }
}
