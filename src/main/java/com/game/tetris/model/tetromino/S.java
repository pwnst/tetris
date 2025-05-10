package com.game.tetris.model.tetromino;

public class S extends Tetromino {
    public S(int posX, int posY) {
        super(
                new int[][]{
                        {0, 1, 1},
                        {1, 1, 0},
                        {0, 0, 0}
                }, 5, posX, posY
        );
    }
}
