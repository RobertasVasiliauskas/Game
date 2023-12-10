package Utilities;

import Main.Game;

import java.awt.geom.Rectangle2D;
import java.time.Year;

public class helpMethods {
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (isSolid(x, y, lvlData)) {
            return false;
        }
        if (isSolid(x + width, y + height, lvlData)) {
            return false;
        }
        if (isSolid(x + width, y, lvlData)) {
            return false;
        }
        return !isSolid(x, y + height, lvlData);
    }
    private static boolean isSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length*Game.TILES_SIZE;

        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return isTileSolid((int)xIndex,(int) yIndex, lvlData);
    }

    public static boolean isTileSolid(int xTile, int yTile, int[][] level){
        int value = level[yTile][xTile];
        return value != 11;
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * Game.TILES_SIZE;
    }

    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitBox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * Game.TILES_SIZE;

    }

    public static boolean noEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
        return isSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData) || isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData);
    }

    public static boolean isFloor(Rectangle2D.Float hitBox, float xSpeed, int[][] level) {
        return isSolid(hitBox.x + xSpeed, hitBox.y + hitBox.height + 1, level);
    }

    public static boolean isAllTileWalkable(int xStart, int xEnd, int y, int[][] level){
        for (int i = 0; i < xEnd - xStart; i++) {
            if(isTileSolid(xStart + i, y, level)){
                return false;
            }
            if(!isTileSolid(xStart + i, y+1, level)){
                return false;
            }
        }
        return true;
    }

    public static boolean isPathClear(int[][] level, Rectangle2D.Float hitBox_one, Rectangle2D.Float hitBox_two, int tileY) {
        int firstXTile = (int) hitBox_one.x / Game.TILES_SIZE;
        int secondXTile = (int) hitBox_two.x / Game.TILES_SIZE;

        if (firstXTile > secondXTile) return isAllTileWalkable(secondXTile, firstXTile, tileY, level);
        else return isAllTileWalkable(firstXTile, secondXTile, tileY, level);
    }
}
