package org.example.arkanoid.source;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.example.arkanoid.source.baseShapes.BaseRectangle;

public class Brick extends BaseRectangle {
    private final IntegerProperty hp = new SimpleIntegerProperty();
    private final BooleanProperty destroyed = new SimpleBooleanProperty(false);

    public Brick() {
        hp.set(3);
    }

    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height);
        hp.set(3);
    }

    public int getHp() {
        return hp.get();
    }

    public IntegerProperty hpProperty() {
        return hp;
    }

    public void decreaseHp() {
        this.hp.set(getHp() - 1);
        if (hp.get() == 0)
            setDestroyed();
    }

    public boolean isDestroyed() {
        return destroyed.get();
    }

    public void setDestroyed() {
        this.destroyed.set(true);
    }

    public BooleanProperty destroyedProperty() {
        return destroyed;
    }
}
