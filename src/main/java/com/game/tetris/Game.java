package com.game.tetris;

import com.game.tetris.model.Block;
import com.game.tetris.model.tetromino.Tetromino;
import com.game.tetris.model.tetromino.Type;
import com.game.tetris.util.RandomBag;
import com.game.tetris.util.SoundUtil;

import java.util.LinkedList;
import java.util.List;

public class Game {

    private final int[][] grid;
    private final RandomBag randomBag;
    private Tetromino currentTetromino;
    private int score;
    private int level;
    private int speed;
    private boolean gameOver;

    public Game() {
        this.grid = new int[27][10];
        this.randomBag = new RandomBag();
        this.currentTetromino = randomBag.getNext().create(4, 2);
        this.score = 0;
        this.level = 1;
        this.speed = 1000;
        this.gameOver = false;
    }

    public void rotate() {
        var rotatedCopy = currentTetromino.getRotatedCopy();
        if (!hasCollisionWithGrid(rotatedCopy)) {
            SoundUtil.playRotate();
            currentTetromino = rotatedCopy;
        }
    }


    public void move(Movement movement) {
        if (noCollision(movement)) {
            currentTetromino.move(movement);
        }
    }

    public boolean moveDownWithCollapse() {
        if (noCollision(Movement.DOWN)) {
            currentTetromino.move(Movement.DOWN);
            return false;
        } else {
            mergeCollapseSpawnNew();
            return true;
        }
    }

    public boolean noCollision(Movement movement) {
        return switch (movement) {
            case LEFT -> noCollisionLeft();
            case RIGHT -> noCollisionRight();
            case DOWN -> noCollisionDown();
        };
    }

    public void spawnTetromino() {
        Tetromino tetromino = randomBag.getNext().create(4, 2);
        if (hasCollisionWithGrid(tetromino)) {
            SoundUtil.playGameOver();
            this.gameOver = true;
        } else {
            this.currentTetromino = tetromino;
        }
    }

    public void mergeCollapseSpawnNew() {
        merge();
        collapse();
        spawnTetromino();
    }

    public void merge() {
        SoundUtil.playDrop();
        for (Block block : currentTetromino.getBlocks()) {
            grid[block.y()][block.x()] = currentTetromino.getColorIndex();
        }
    }

    public void collapse() {
        List<Integer> rowsToCollapse = new LinkedList<>();
        for (int y = 0; y < grid.length; y++) {
            boolean collapseRow = true;
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 0) {
                    collapseRow = false;
                    break;
                }
            }
            if (collapseRow) {
                rowsToCollapse.add(y);
            }
        }
        for (Integer rowIndex : rowsToCollapse) {
            for (int y = rowIndex; y >= 1; y--) {
                grid[y] = grid[y - 1];
            }
            grid[0] = new int[10];
        }
        if (!rowsToCollapse.isEmpty()) {
            SoundUtil.playClearLine();
            updateScore(rowsToCollapse.size());
        }
    }

    private boolean hasCollisionWithGrid(Tetromino tetromino) {
        for (Block block : tetromino.getBlocks()) {
            int blockX = block.x();
            int blockY = block.y();
            if (blockX > 9 || blockX < 0 || blockY < 0 || blockY > grid.length - 1 || grid[blockY][blockX] > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean noCollisionLeft() {
        for (Block block : currentTetromino.getBlocks()) {
            if (block.x() <= 0 || grid[block.y()][block.x() - 1] > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean noCollisionRight() {
        for (Block block : currentTetromino.getBlocks()) {
            if (block.x() >= 9 || grid[block.y()][block.x() + 1] > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean noCollisionDown() {
        for (Block block : currentTetromino.getBlocks()) {
            if (block.y() >= grid.length - 1 || grid[block.y() + 1][block.x()] > 0) {
                return false;
            }
        }
        return true;
    }

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public Type getNextType() {
        return randomBag.showNext();
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getSpeed() {
        return speed;
    }

    private void updateScore(int linesCollapsed) {
        if (linesCollapsed == 1) {
            score += 100;
        } else if (linesCollapsed == 2) {
            score += 225;
        } else if (linesCollapsed == 3) {
            score += 375;
        } else {
            score += 500;
        }
        if (score >= level * 5000) {
            level += 1;
            speed -= 100;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
