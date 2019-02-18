package org.gsep;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;


public class FXMLController {
    @FXML
    protected Button btn_next;

    @FXML
    protected ItemContainer ic_one;

    @FXML
    protected ItemContainer ic_two;

    @FXML
    protected ItemContainer ic_three;

    @FXML
    protected ItemContainer ic_four;

    @FXML
    protected ItemContainer ic_five;


    public void initialize() {

    }

    public void move(){
        System.out.println("Moving...");
        TranslateTransition t_one = new TranslateTransition(Duration.seconds(2), ic_one);
        TranslateTransition t_two = new TranslateTransition(Duration.seconds(2), ic_two);
        TranslateTransition t_three = new TranslateTransition(Duration.seconds(2), ic_three);
        TranslateTransition t_four = new TranslateTransition(Duration.seconds(2), ic_four);
        TranslateTransition t_five = new TranslateTransition(Duration.seconds(2), ic_five);


        t_one.setToX(ic_two.getLayoutX()- ic_one.getLayoutX());
        t_two.setToX(ic_three.getLayoutX()- ic_two.getLayoutX());
        t_three.setToX(ic_four.getLayoutX()- ic_three.getLayoutX());
        t_four.setToX(ic_five.getLayoutX()- ic_four.getLayoutX());
        t_five.setToX(ic_one.getLayoutX()- ic_five.getLayoutX());

        t_one.play();
        t_two.play();
        t_three.play();
        t_four.play();
        t_five.play();
    }

}
