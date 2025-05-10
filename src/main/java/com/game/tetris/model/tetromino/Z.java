package com.game.tetris.model.tetromino;

public class Z extends Tetromino {
    public Z(int posX, int posY) {
        super(
                new int[][]{
                        {1, 1, 0},
                        {0, 1, 1},
                        {0, 0, 0}
                }, 6, posX, posY
        );
    }
}
