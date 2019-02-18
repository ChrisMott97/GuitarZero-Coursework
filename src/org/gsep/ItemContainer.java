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

    public ItemContainer(String text){
        this();
        setText(text);
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

    @FXML
    public void doSomething(){
        System.out.println("Some action");
    }
}
