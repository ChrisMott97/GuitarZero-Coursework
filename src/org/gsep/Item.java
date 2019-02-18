package org.gsep;

import javafx.scene.layout.Pane;

abstract class Item extends Pane {
    private String text;
    private String pictureUrl;

    Item(String text){
        this.text = text;
    }

    Item(String text, String pictureUrl){
        this.text = text;
        this.pictureUrl = pictureUrl;
    }

    public String getText() {
        return text;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
