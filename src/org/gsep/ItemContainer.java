package org.gsep;

import javafx.animation.*;
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

    private int initialPosition;

    private Item item;

    private TranslateTransition moveRight = new TranslateTransition(Duration.seconds(1.0), this);
    private TranslateTransition moveLeft = new TranslateTransition(Duration.seconds(1.0), this);

    private FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), this);
    private FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), this);

    private TranslateTransition halfMoveRight = new TranslateTransition(Duration.seconds(0.5), this);
    private TranslateTransition halfMoveLeft = new TranslateTransition(Duration.seconds(0.5), this);
    public TranslateTransition jumpLeft = new TranslateTransition(Duration.millis(10), this);
    public TranslateTransition jumpRight = new TranslateTransition(Duration.millis(10), this);

//    private ParallelTransition fadeOutRight = new ParallelTransition(fadeOut, halfMoveRight);
//    private ParallelTransition fadeOutLeft = new ParallelTransition(fadeOut, halfMoveLeft);
//    private ParallelTransition fadeInRight = new ParallelTransition(fadeIn, halfMoveRight);
//    private ParallelTransition fadeInLeft = new ParallelTransition(fadeIn, halfMoveLeft);

    private PauseTransition pt = new PauseTransition(Duration.millis(2000));

    private SequentialTransition cycleRight = new SequentialTransition(fadeOut, jumpLeft, fadeIn);
    private SequentialTransition cycleLeft = new SequentialTransition(fadeOut, jumpRight, fadeIn);


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

    public void setMove(double distance) {
        moveRight.setByX(distance);
        halfMoveRight.setByX(distance/2);
        moveLeft.setByX(-distance);
        halfMoveLeft.setByX(-distance/2);
    }

    public void setJumpLeft(double distance) {
        jumpLeft.setToX(distance);
    }

    public void setJumpRight(double distance) {
        jumpRight.setToX(distance);
    }

    public TranslateTransition getMoveRight() {
        return moveRight;
    }

    public TranslateTransition getMoveLeft() {
        return moveLeft;
    }

    public SequentialTransition getCycleRight(){
        return cycleRight;
    }

    public SequentialTransition getCycleLeft() {
        return cycleLeft;
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

    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }

    public int getInitialPosition() {
        return initialPosition;
    }
}
