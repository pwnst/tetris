package com.game.tetris.model.tetromino;

public enum Type {
    I(I.class),
    J(J.class),
    L(L.class),
    O(O.class),
    S(S.class),
    T(T.class),
    Z(Z.class);

    private final Class<?> clazz;

    Type(Class<?> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("Unchecked")
    public <E extends Tetromino> E create(int x, int y) {
        try {
            return (E) clazz.getConstructor(int.class, int.class).newInstance(x, y);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
