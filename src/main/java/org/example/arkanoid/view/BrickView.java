package org.example.arkanoid.view;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class BrickView extends Rectangle {
    public static final Map<Integer, Color> COLOR_OF_HP = Map.of(
            1, Color.RED,
            2, Color.ORANGE,
            3, Color.GREEN
    );
}
