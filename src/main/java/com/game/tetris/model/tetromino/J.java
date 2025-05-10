package com.game.tetris.model.tetromino;

public class J extends Tetromino {
    public J(int posX, int posY) {
        super(
                new int[][]{
                        {1, 0, 0},
                        {1, 1, 1},
                        {0, 0, 0}
                }, 3, posX, posY
        );
    }
}
