package org.gsep;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class CarouselController {

    private MusicItemModel model;

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

    private Glow g;


    public void initialize() {
        containers.add(ic_one);
        containers.add(ic_two);
        containers.add(ic_three);
        containers.add(ic_four);
        containers.add(ic_five);
        distance = (ic_two.getLayoutX()/2)+10;
        System.out.println(distance);
        System.out.println(ic_five.getLayoutX());
        g = new Glow();
        g.setLevel(1);
        containers.get(2).setEffect(g);
    }

    public void linkModel(MusicItemModel model){
        if(this.model != null)
            throw new IllegalStateException("Model can only be linked once!");

        this.model = model;
        this.load();
        for (int i = 0; i < 5; i++) {
            containers.get(i).setItem(model.getItems().get(i));
        }
    }

    public void load(){
        model.loadData();
    }

    public void next(){
        System.out.println("Moving...");

        TranslateTransition t_one = new TranslateTransition(Duration.seconds(1), containers.get(0));
        TranslateTransition t_two = new TranslateTransition(Duration.seconds(1), containers.get(1));
        TranslateTransition t_three = new TranslateTransition(Duration.seconds(1), containers.get(2));
        TranslateTransition t_four = new TranslateTransition(Duration.seconds(1), containers.get(3));

        FadeTransition out_five = new FadeTransition(Duration.seconds(.5), containers.get(4));
        TranslateTransition right_five = new TranslateTransition(Duration.millis(500), containers.get(4));
        TranslateTransition t_five = new TranslateTransition(Duration.millis(10), containers.get(4));
        FadeTransition in_five = new FadeTransition(Duration.seconds(.5), containers.get(4));

        t_one.setByX(distance);
        t_two.setByX(distance);
        t_three.setByX(distance);
        t_four.setByX(distance);

        out_five.setToValue(0);
        right_five.setByX(distance/2);
        t_five.setToX(-containers.get(4).getLayoutX()-(distance/2)+20);
        in_five.setToValue(1);

        ParallelTransition outr_five = new ParallelTransition(out_five, right_five);
        ParallelTransition inr_five = new ParallelTransition(in_five, right_five);


        SequentialTransition st_five = new SequentialTransition(outr_five, t_five, inr_five);

        t_one.play();
        t_two.play();
        t_three.play();
        t_four.play();
        st_five.play();

        containers.add(0, containers.remove(containers.size()-1));

        containers.get(3).setEffect(null);
        containers.get(2).setEffect(g);
        System.out.println(containers);
    }

}
