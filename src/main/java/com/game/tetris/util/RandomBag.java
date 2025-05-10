package com.game.tetris.util;

import com.game.tetris.model.tetromino.Type;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class RandomBag {

    private final Queue<Type> queue;

    public RandomBag() {
        this.queue = new LinkedList<>();
    }

    public Type showNext() {
        ensureCapacity();
        return queue.peek();
    }

    public Type getNext() {
        ensureCapacity();
        return queue.poll();
    }

    private void ensureCapacity() {
        if (queue.size() < 3) {
            addBag();
        }
    }

    private void addBag() {
        List<Type> types = Arrays.stream(Type.values())
                .collect(Collectors.toList());
        Collections.shuffle(types);
        queue.addAll(types);
    }
}
