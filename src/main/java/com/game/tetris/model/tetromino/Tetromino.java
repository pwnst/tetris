package com.game.tetris.model.tetromino;

import com.game.tetris.Movement;
import com.game.tetris.model.Block;

import java.util.LinkedList;
import java.util.List;

public class Tetromino {

    private final int[][] grid;
    private final int colorIndex;
    private int posX;
    private int posY;

    public Tetromino(int[][] grid, int colorIndex, int posX, int posY) {
        this.grid = grid;
        this.posX = posX;
        this.posY = posY;
        this.colorIndex = colorIndex;
    }

    public void move(Movement movement) {
        switch (movement) {
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
            case DOWN -> moveDown();
        }
    }

    public Tetromino getRotatedCopy() {
        int[][] rotatedGrid = rotateGrid(this.grid);
        return new Tetromino(rotatedGrid, this.colorIndex, this.posX, this.posY);
    }

    public List<Block> getBlocks() {
        List<Block> blocks = new LinkedList<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                int curr = grid[y][x];
                if (curr != 0) {
                    blocks.add(new Block(x + posX, y + posY));
                }
            }
        }
        return blocks;
    }

    public int getColorIndex() {
        return this.colorIndex;
    }

    public void moveLeft() {
        this.posX -= 1;
    }

    public void moveRight() {
        this.posX += 1;
    }

    public void moveDown() {
        this.posY += 1;
    }

    private int[][] rotateGrid(int[][] grid) {
        int n = grid.length;
        int[][] newGrid = new int[n][n];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                newGrid[x][n - 1 - y] = grid[y][x];
            }
        }
        return newGrid;
    }
}
