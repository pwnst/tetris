package com.game.tetris.model.tetromino;

public class O extends Tetromino {
    public O(int posX, int posY) {
        super(
                new int[][]{
                        {1, 1},
                        {1, 1},
                }, 1, posX, posY
        );
    }
}
