package com.game.tetris;

import com.game.tetris.util.SoundUtil;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;


public class GameApp extends Application {

    public final static int BLOCK_SIZE = 20;
    public final static int WIDTH = 380;
    public final static int HEIGHT = 520;
    private final Set<KeyCode> pressedKeys = new HashSet<>();
    private Game game;
    private Render render;
    private long lastTick;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris");
        primaryStage.show();

        lastTick = System.currentTimeMillis();
        game = new Game();
        render = new Render(gc, game);
        SoundUtil.playStart();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(game);
                render.render();
            }
        };

        gameLoop.start();
    }

    private void update(Game game) {
        if (game.isGameOver()) {
            if (pressedKeys.contains(KeyCode.Y)) {
                SoundUtil.playStart();
                this.game = new Game();
                this.render.setGame(this.game);
            }
            if (pressedKeys.contains(KeyCode.N)) {
                Platform.exit();
                System.exit(0);
            }
            return;
        }
        if (System.currentTimeMillis() - lastTick > game.getSpeed()) {
            game.autoMoveDown();
            lastTick = System.currentTimeMillis();
        }
        if (pressedKeys.contains(KeyCode.DOWN)) {
            game.moveDown(pressedKeys);
        }
        if (pressedKeys.contains(KeyCode.LEFT)) {
            pressedKeys.remove(KeyCode.LEFT);
            game.move(Movement.LEFT);
        }
        if (pressedKeys.contains(KeyCode.RIGHT)) {
            pressedKeys.remove(KeyCode.RIGHT);
            game.move(Movement.RIGHT);
        }
        if (pressedKeys.contains(KeyCode.UP)) {
            pressedKeys.remove(KeyCode.UP);
            game.rotate();
        }
    }
}
