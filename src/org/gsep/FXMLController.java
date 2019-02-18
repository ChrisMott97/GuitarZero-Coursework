package org.gsep;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class FXMLController {
    @FXML
    protected Button btn_next;

    @FXML
    protected Pane pane_one;
    protected double one_pos;

    @FXML
    protected Pane pane_two;
    protected double two_pos;

    @FXML
    protected Pane pane_three;
    protected double three_pos;

    @FXML
    protected Pane pane_four;
    protected double four_pos;

    @FXML
    protected Pane pane_five;
    protected double five_pos;


    public void initialize() {
        
        one_pos = pane_one.getLayoutX();
        two_pos = pane_two.getLayoutX();
        three_pos = pane_three.getLayoutX();
        four_pos = pane_four.getLayoutX();
        five_pos = pane_five.getLayoutX();
    }

    public void move(){
        System.out.println("Moving...");
        TranslateTransition t_one = new TranslateTransition(Duration.seconds(2), pane_one);
        TranslateTransition t_two = new TranslateTransition(Duration.seconds(2), pane_two);
        TranslateTransition t_three = new TranslateTransition(Duration.seconds(2), pane_three);
        TranslateTransition t_four = new TranslateTransition(Duration.seconds(2), pane_four);
        TranslateTransition t_five = new TranslateTransition(Duration.seconds(2), pane_five);


        t_one.setToX(pane_two.getLayoutX()- pane_one.getLayoutX());
        t_two.setToX(pane_three.getLayoutX()-pane_two.getLayoutX());
        t_three.setToX(pane_four.getLayoutX()-pane_three.getLayoutX());
        t_four.setToX(pane_five.getLayoutX()-pane_four.getLayoutX());
        t_five.setToX(pane_one.getLayoutX()-pane_five.getLayoutX());

        t_one.play();
        t_two.play();
        t_three.play();
        t_four.play();
        t_five.play();
    }

}
