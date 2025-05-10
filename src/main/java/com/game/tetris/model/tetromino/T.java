package com.game.tetris.model.tetromino;

public class T extends Tetromino {
    public T(int posX, int posY) {
        super(
                new int[][]{
                        {0, 1, 0},
                        {1, 1, 1},
                        {0, 0, 0}
                }, 2, posX, posY
        );
    }
}
