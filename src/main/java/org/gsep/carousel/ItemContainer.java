package org.gsep.carousel;

import javafx.animation.*;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/*
 * ItemContainer.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class ItemContainer extends Pane {

    @FXML
    private Label label;

    @FXML
    private ImageView imageView;

    private int initialPosition;

    private Item item;

    private TranslateTransition moveRight = new TranslateTransition(Duration.seconds(1.0), this);
    private TranslateTransition moveLeft = new TranslateTransition(Duration.seconds(1.0), this);

    private FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), this);
    private FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), this);

    private TranslateTransition halfMoveRight = new TranslateTransition(Duration.seconds(0.5), this);
    private TranslateTransition halfMoveLeft = new TranslateTransition(Duration.seconds(0.5), this);
    private TranslateTransition jumpLeft = new TranslateTransition(Duration.millis(10), this);
    private TranslateTransition jumpRight = new TranslateTransition(Duration.millis(10), this);

//    private ParallelTransition fadeOutRight = new ParallelTransition(fadeOut, halfMoveRight);
//    private ParallelTransition fadeOutLeft = new ParallelTransition(fadeOut, halfMoveLeft);
//    private ParallelTransition fadeInRight = new ParallelTransition(fadeIn, halfMoveRight);
//    private ParallelTransition fadeInLeft = new ParallelTransition(fadeIn, halfMoveLeft);

    private PauseTransition pt = new PauseTransition(Duration.millis(2000));

    private SequentialTransition cycleRight = new SequentialTransition(fadeOut, jumpLeft, fadeIn);
    private SequentialTransition cycleLeft = new SequentialTransition(fadeOut, jumpRight, fadeIn);

    /**
     * Constructor for the custom FXML component Item Container.
     * Manages loading the FXML.
     */
    public ItemContainer(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ItemContainerView.fxml"));
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

    /**
     * Changes style if the given instance is the intended ItemContainer.
     */
    public void setIntended(){
        this.setStyle("-fx-border-color: red; -fx-border-width: 3px");
    }

    /**
     * Changes style back to default if the given instance is not the intended ItemContainer.
     */
    public void removeIntended(){
        this.setStyle("-fx-border-color: black;");
    }

    /**
     * Sets how far each movement animation goes.
     *
     * @param distance the distance between two ItemContainers.
     */
    public void setMove(double distance) {
        moveRight.setByX(distance);
        halfMoveRight.setByX(distance/2);
        moveLeft.setByX(-distance);
        halfMoveLeft.setByX(-distance/2);
    }

    /**
     * Sets how far each edge Item Container should jump left when invisible.
     * @param distance distance to jump.
     */
    public void setJumpLeft(double distance) {
        jumpLeft.setToX(distance);
    }

    /**
     * Sets how far each edge Item Container should jump right when invisible.
     * @param distance distance to jump.
     */
    public void setJumpRight(double distance) {
        jumpRight.setToX(distance);
    }

    /**
     * Getter for transition to move right.
     *
     * @return move right transition.
     */
    public TranslateTransition getMoveRight() {
        return moveRight;
    }

    /**
     * Getter for transition to move left.
     *
     * @return move left transition.
     */
    public TranslateTransition getMoveLeft() {
        return moveLeft;
    }

    /**
     * Getter for sequential cycle right transition.
     *
     * @return sequential cycle right transition.
     */
    public SequentialTransition getCycleRight(){
        return cycleRight;
    }

    /**
     * Getter for sequential cycle left transition.
     *
     * @return sequential cycle left transition.
     */
    public SequentialTransition getCycleLeft() {
        return cycleLeft;
    }

    /**
     * Getter for the text of the container.
     *
     * @return the text.
     */
    public String getText(){
        return textProperty().get();
    }

    /**
     * Setter for the text of the Item Container.
     * @param text the text to be set.
     */
    public void setText(String text){
        textProperty().setValue(text);
    }

    /**
     * Getter for the property of the text of the label.
     * @return the property of the text of the label.
     */
    public StringProperty textProperty(){
        return label.textProperty();
    }

    /**
     * Links a given Item to the Item Container.
     *
     * @param item the Item to be linked.
     */
    public void setItem(Item item){
        //TODO: Move to ItemContainerModel?
        File file;
        Image image;
        try{
            file = new File(getClass().getResource("/"+item.getPrefix()+"/img/"+item.getId()+".png").getFile());
        }catch(Exception e){
            file = new File(getClass().getResource("/"+item.getPrefix()+"/img/error.png").getFile());
        }
        image = new Image(file.toURI().toString(), 65, 65, true, true, true);

        this.item = item;
        this.label.setText(item.getName());
        this.imageView.setImage(image);
        this.imageView.setPreserveRatio(true);
        this.imageView.setSmooth(true);
        this.imageView.setCache(true);
        this.imageView.fitWidthProperty().bind(this.widthProperty());
        this.imageView.fitHeightProperty().bind(this.heightProperty());
    }

    /**
     * Getter for the currently contained Item.
     *
     * @return the currently contained Item.
     */
    public Item getItem(){
        return this.item;
    }

    /**
     * Setter for the initial position of the Item Container
     * which is used to calculate transitions.
     * @param initialPosition
     */
    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }

    /**
     * Getter for the initial position of the Item Container
     * which is used to calculate transitions.
     *
     * @return the initial position (1-5) of the Item Container.
     */
    public int getInitialPosition() {
        return initialPosition;
    }

    /**
     * Returns whether there is any animation currently running in the item container.
     * TODO: More elegant solution
     *
     * @return status of whether an animation is running.
     */
    public Animation.Status getStatus(){
        if(moveRight.getStatus() == Animation.Status.RUNNING)
            return Animation.Status.RUNNING;
        if(moveLeft.getStatus() == Animation.Status.RUNNING)
            return Animation.Status.RUNNING;
        if(cycleRight.getStatus() == Animation.Status.RUNNING)
            return Animation.Status.RUNNING;
        if(cycleLeft.getStatus() == Animation.Status.RUNNING)
            return Animation.Status.RUNNING;
        return Animation.Status.STOPPED;
    }
}
