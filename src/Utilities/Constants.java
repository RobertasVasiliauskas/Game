package Utilities;

import Main.Game;

public class Constants {

    public static class EnemyConstants{
        public static final int CRABBY = 0;
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;
        public static final int CRABBY_WIDTH = (int)(CRABBY_WIDTH_DEFAULT * Game.SCALE);
        public static final int CRABBY_HEIGHT = (int)(CRABBY_HEIGHT_DEFAULT * Game.SCALE);

        public static final int CRABBY_DRAWOFFSET_X = (int)(26*Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int)(9*Game.SCALE);

        public static int getSpriteAmount(int enemyType, int enemyStatus){
            switch (enemyType) {
                case CRABBY:
                    switch(enemyStatus){
                        case IDLE:
                            return 9;
                        case RUNNING:
                            return 6;
                        case ATTACK:
                            return 7;
                        case HIT:
                            return 4;
                        case DEAD:
                            return 5;
                    }
            }
            return 0;
        }
        public static int getMaxHealth(int enemyType){
            return switch (enemyType) {
                case CRABBY -> 10;
                default -> 0;
            };
        }

        public static int getEnemyDamage(int enemyType){
            return switch (enemyType) {
                case CRABBY -> 15;
                default -> 0;
            };
        }
    }
    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3 ;
    }
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPING = 2;
        public static final int FALLING = 3;
        public static final int ATTACK = 4;
        public static final int HIT = 5;
        public static final int DEAD = 6;

        public static int getSpriteAmount(int player_status) {
            return switch (player_status) {
                case DEAD -> 8;
                case RUNNING -> 6;
                case IDLE -> 5;
                case HIT -> 4;
                case JUMPING, ATTACK -> 3;
                case FALLING -> 1;
                default -> 0;
            };

        }
    }
}