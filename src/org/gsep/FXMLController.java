package org.gsep;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


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

    private List<ItemContainer> containers = new ArrayList<>();

    private double distance;


    public void initialize() {
        containers.add(ic_one);
        containers.add(ic_two);
        containers.add(ic_three);
        containers.add(ic_four);
        containers.add(ic_five);
        distance = (ic_two.getLayoutX()/2)+10;
        System.out.println(distance);
        System.out.println(ic_five.getLayoutX());
    }

    public void next(){
        System.out.println("Moving...");

        TranslateTransition t_one = new TranslateTransition(Duration.seconds(2), containers.get(0));
        TranslateTransition t_two = new TranslateTransition(Duration.seconds(2), containers.get(1));
        TranslateTransition t_three = new TranslateTransition(Duration.seconds(2), containers.get(2));
        TranslateTransition t_four = new TranslateTransition(Duration.seconds(2), containers.get(3));

        FadeTransition out_five = new FadeTransition(Duration.seconds(.8), containers.get(4));
        TranslateTransition t_five = new TranslateTransition(Duration.millis(1000), containers.get(4));
        FadeTransition in_five = new FadeTransition(Duration.seconds(.8), containers.get(4));



        t_one.setByX(distance);
        t_two.setByX(distance);
        t_three.setByX(distance);
        t_four.setByX(distance);

        out_five.setToValue(0);
        t_five.setToX(-containers.get(4).getLayoutX()+20);
        in_five.setToValue(1);

        SequentialTransition st_five = new SequentialTransition(out_five, t_five, in_five);

        t_one.play();
        t_two.play();
        t_three.play();
        t_four.play();
        st_five.play();

        containers.add(0, containers.remove(containers.size()-1));
        System.out.println(containers);
    }

}
