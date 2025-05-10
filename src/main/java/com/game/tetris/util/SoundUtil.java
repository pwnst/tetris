package com.game.tetris.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

public class SoundUtil {

    public static void playStart() {
        play("sounds/start.wav");
    }

    public static void playGameOver() {
        play("sounds/game_over.wav");
    }

    public static void playRotate() {
        play("sounds/flip.wav");
    }

    public static void playDrop() {
        play("sounds/drop.wav");
    }

    public static void playClearLine() {
        play("sounds/clear.wav");
    }

    private static void play(String path) {
        try {
            InputStream resourceStream = SoundUtil.class.getClassLoader().getResourceAsStream(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(resourceStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
