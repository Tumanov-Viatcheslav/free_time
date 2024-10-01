package org.example.arkanoid.source;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import org.example.BorderGame;

public class Platform extends Rectangle {
    @FXML
    private final ObjectProperty<BorderGame> border = new ObjectPropertyBase<BorderGame>() {
        @Override
        public Object getBean() {
            return new BorderGame();
        }

        @Override
        public String getName() {
            return "border";
        }
    };

    public Platform() {
        super();
    }

    public void setBorder(BorderGame border) {
        this.border.set(border);
    }

    public BorderGame getBorder() {
        return border.get();
    }

    public double getBorderX() {
        return getBorder().getX();
    }

    public double getBorderWidth() {
        return getBorder().getWidth();
    }

    public ObjectProperty<BorderGame> borderProperty() {
        return border;
    }

    public void moveLeft(double step) {
        if (getX() - step > getBorderX())
            setX(getX() - step);
        else setX(getBorderX());
    }

    public void moveRight(double step) {
        if (getX() + getWidth() + step < getBorderWidth())
            setX(getX() + step);
        else setX(getBorderWidth() - getWidth());
    }
}
