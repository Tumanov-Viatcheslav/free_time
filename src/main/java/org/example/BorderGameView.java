package org.example;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class BorderGameView extends Rectangle {
    @FXML
    BorderGame border;

    public BorderGameView() {
        border = new BorderGame();
    }

    public BorderGame getBorder() {
        return border;
    }

    public void setBorder(BorderGame border) {
        this.border = border;
    }
}
