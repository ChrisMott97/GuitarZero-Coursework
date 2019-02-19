package org.gsep;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ItemContainer extends Pane {

    @FXML
    private Label label;

    private int position;

    private Item item;

    public ItemContainer(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("itemContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public ItemContainer(Item item){
        this();
        this.item = item;
        setText(item.getText());
    }

    public String getText(){
        return textProperty().get();
    }

    public void setText(String text){
        textProperty().setValue(text);
    }

    public StringProperty textProperty(){
        return label.textProperty();
    }

    public void setItem(Item item){
        this.item = item;
        this.label.setText(item.getText());
    }

    public Item getItem(){
        return this.item;
    }
}
