package com.game.tetris.model.tetromino;

public class L extends Tetromino {
    public L(int posX, int posY) {
        super(
                new int[][]{
                        {0, 0, 1},
                        {1, 1, 1},
                        {0, 0, 0}
                }, 4, posX, posY
        );
    }
}
