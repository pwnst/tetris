package com.game.tetris;

import com.game.tetris.model.Block;
import com.game.tetris.model.tetromino.Tetromino;
import com.game.tetris.model.tetromino.Type;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static com.game.tetris.GameApp.BLOCK_SIZE;
import static com.game.tetris.GameApp.HEIGHT;
import static com.game.tetris.GameApp.WIDTH;
import static com.game.tetris.model.tetromino.Type.I;
import static com.game.tetris.util.ColorUtil.COLOR;

public class Render {

    private static final int GRID_WIDTH = BLOCK_SIZE * 10;
    private static final int GRID_HEIGHT = BLOCK_SIZE * 24;
    private static final int GRID_BLOCK_Y_OFFSET = 3;
    private static final int SCORE_AND_LEVEL_X = BLOCK_SIZE * 12;
    private static final int SCORE_Y = BLOCK_SIZE * 2;
    private static final int LEVEL_Y = BLOCK_SIZE * 3;
    private static final Font ARIAL_16 = Font.font("Arial", FontWeight.BOLD, 16);
    private static final Font ARIAL_24 = Font.font("Arial", FontWeight.BOLD, 24);
    private static final int PREVIEW_FRAME_X = BLOCK_SIZE * 12;
    private static final int PREVIEW_FRAME_Y = BLOCK_SIZE * 4;
    private static final int PREVIEW_FRAME_WIDTH = BLOCK_SIZE * 6;
    private static final int PREVIEW_FRAME_HEIGHT = BLOCK_SIZE * 4;
    private static final int PREVIEW_I_ADJUST_X = BLOCK_SIZE * 13;
    private static final int PREVIEW_O_ADJUST_X = BLOCK_SIZE * 14;
    private static final int PREVIEW_DEFAULT_ADJUST_X = BLOCK_SIZE * 13 + BLOCK_SIZE / 2;
    private static final int PREVIEW_I_ADJUST_Y = BLOCK_SIZE * 4 + BLOCK_SIZE / 2;
    private static final int PREVIEW_DEFAULT_ADJUST_Y = BLOCK_SIZE * 5;
    private final GraphicsContext gc;
    private Game game;

    public Render(GraphicsContext gc, Game game) {
        this.gc = gc;
        this.game = game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void renderBackground() {
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
    }

    private void renderGridBackground() {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(BLOCK_SIZE, BLOCK_SIZE, GRID_WIDTH, GRID_HEIGHT);
    }

    private void renderGrid() {
        int[][] grid = game.getGrid();
        for (int y = GRID_BLOCK_Y_OFFSET; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                int blockColorIndex = grid[y][x];
                if (blockColorIndex > 0) {
                    int blockX = BLOCK_SIZE + (BLOCK_SIZE * x);
                    int blockY = BLOCK_SIZE + (BLOCK_SIZE * (y - GRID_BLOCK_Y_OFFSET));
                    gc.setFill(COLOR[blockColorIndex]);
                    renderBlock(blockX, blockY);
                }
            }
        }

    }

    private void renderScoreAndLevel() {
        gc.setFill(Color.WHITE);
        gc.setFont(ARIAL_16);
        gc.fillText("SCORE: " + game.getScore(), SCORE_AND_LEVEL_X, SCORE_Y);
        gc.fillText("LEVEL: " + game.getLevel(), SCORE_AND_LEVEL_X, LEVEL_Y);
    }

    public void render() {
        renderBackground();
        renderScoreAndLevel();
        renderNextTetrominoPreview();
        renderGridBackground();
        renderGrid();
        renderTetromino();
        renderGameOver();
    }

    public void renderTetromino() {
        var tetromino = game.getCurrentTetromino();
        Color color = COLOR[tetromino.getColorIndex()];
        gc.setFill(color);
        for (Block block : tetromino.getBlocks()) {
            if (block.y() >= GRID_BLOCK_Y_OFFSET) {
                int x = BLOCK_SIZE + (BLOCK_SIZE * block.x());
                int y = BLOCK_SIZE + (BLOCK_SIZE * (block.y() - GRID_BLOCK_Y_OFFSET));
                renderBlock(x, y);
            }
        }
    }

    public void renderBlock(int x, int y) {
        gc.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
        gc.setStroke(Color.rgb(0, 0, 100));
        gc.strokeLine(x, y, x + BLOCK_SIZE, y);
        gc.strokeLine(x, y, x, y + BLOCK_SIZE);
        gc.strokeLine(x + BLOCK_SIZE, y, x + BLOCK_SIZE, y + BLOCK_SIZE);
        gc.strokeLine(x, y + BLOCK_SIZE, x + BLOCK_SIZE, y + BLOCK_SIZE);
    }

    private void renderNextTetrominoPreview() {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(PREVIEW_FRAME_X, PREVIEW_FRAME_Y, PREVIEW_FRAME_WIDTH, PREVIEW_FRAME_HEIGHT);

        Type nextType = game.getNextType();
        Tetromino tetromino = nextType.create(0, 0);
        Color color = COLOR[tetromino.getColorIndex()];
        gc.setFill(color);

        int toCenterX = tetrominoPreviewXAdjustCenter(nextType);
        int toCenterY = tetrominoPreviewYAdjustCenter(nextType);


        for (Block block : tetromino.getBlocks()) {
            int x = toCenterX + (BLOCK_SIZE * block.x());
            int y = toCenterY + (BLOCK_SIZE * block.y());
            renderBlock(x, y);
        }
    }

    private int tetrominoPreviewXAdjustCenter(Type type) {
        return switch (type) {
            case I -> PREVIEW_I_ADJUST_X;
            case O -> PREVIEW_O_ADJUST_X;
            default -> PREVIEW_DEFAULT_ADJUST_X;
        };
    }

    private int tetrominoPreviewYAdjustCenter(Type type) {
        return type == I ? PREVIEW_I_ADJUST_Y : PREVIEW_DEFAULT_ADJUST_Y;
    }

    private void renderGameOver() {
        if (game.isGameOver()) {
            gc.setFill(Color.BLACK);
            gc.fillRect(55, 175, 270, 150);

            gc.setFill(Color.DARKVIOLET);
            gc.fillRect(60, 180, 260, 140);

            gc.setFill(Color.WHITE);
            gc.setFont(ARIAL_24);
            gc.fillText("GAME OVER", BLOCK_SIZE * 6, BLOCK_SIZE * 11);

            gc.setFont(ARIAL_16);
            gc.fillText("SCORE: " + game.getScore(), BLOCK_SIZE * 6, BLOCK_SIZE * 13);

            gc.fillText("try again? y/n", BLOCK_SIZE * 7, BLOCK_SIZE * 15);
        }
    }
}
