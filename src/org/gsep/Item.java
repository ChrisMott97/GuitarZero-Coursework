package org.gsep;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class Item {

    private String text;

    public Item(String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
