package com.game.tetris.model.tetromino;

public class I extends Tetromino {
    public I(int posX, int posY) {
        super(
                new int[][]{
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                }, 7, posX, posY
        );
    }
}
