package org.gsep;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class ItemContainer extends Pane {

    @FXML
    private Label label;

    private int position;

    private Item item;

    private TranslateTransition moveRight = new TranslateTransition(Duration.seconds(1.0), this);

    private FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), this);
    private FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), this);
    private TranslateTransition halfMoveRight = new TranslateTransition(Duration.seconds(0.5), this);
    private TranslateTransition jumpLeft = new TranslateTransition(Duration.millis(10), this);

    private ParallelTransition fadeOutRight = new ParallelTransition(fadeOut, halfMoveRight);
    private ParallelTransition fadeInRight = new ParallelTransition(fadeIn, halfMoveRight);

    private SequentialTransition cycle = new SequentialTransition(fadeOutRight, jumpLeft, fadeInRight);

    public ItemContainer(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ItemContainerView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        fadeOut.setToValue(0);
        fadeIn.setToValue(1);
    }

    public ItemContainer(Item item){
        this();
        this.item = item;
        setText(item.getText());
    }

    public void combineTransitions(){

    }

    public void setMoveRight(double distance) {
        moveRight.setByX(distance);
    }

    public void setHalfMoveRight(double distance) {
        halfMoveRight.setByX(distance);
    }

    public void setJumpLeft(double distance) {
        jumpLeft.setByX(distance);
    }

    public TranslateTransition getMoveRight() {
        return moveRight;
    }

    public SequentialTransition getCycle(){
        return cycle;
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
