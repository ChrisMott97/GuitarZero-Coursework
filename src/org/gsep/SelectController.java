package org.gsep;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class SelectController {

    private ItemModel iModel;
    private ItemContainerModel icModel;

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

    public void linkModels(ItemModel iModel, ItemContainerModel icModel){
        if(this.icModel != null || this.iModel != null)
            throw new IllegalStateException("Model can only be linked once!");

        this.iModel = iModel;
        this.icModel = icModel;
        this.loadData();


    }

    private void loadData(){
        List<MusicItem> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            items.add(new MusicItem("Song "+(i+1)));
        }
        iModel.loadData(items);
        icModel.loadData(containers);
    }

    public void next(){

        for (int i = 0; i < 4; i++) {
            containers.get(i).getMoveRight().setByX(distance);
            containers.get(i).getMoveRight().play();
        }

        FadeTransition out_five = new FadeTransition(Duration.seconds(.5), containers.get(4));
        TranslateTransition right_five = new TranslateTransition(Duration.millis(500), containers.get(4));
        TranslateTransition t_five = new TranslateTransition(Duration.millis(10), containers.get(4));
        FadeTransition in_five = new FadeTransition(Duration.seconds(.5), containers.get(4));

        out_five.setToValue(0);
        right_five.setByX(distance/2);
        t_five.setToX(-containers.get(4).getLayoutX()-(distance/2)+20);
        in_five.setToValue(1);

        ParallelTransition outr_five = new ParallelTransition(out_five, right_five);
        ParallelTransition inr_five = new ParallelTransition(in_five, right_five);


        SequentialTransition st_five = new SequentialTransition(outr_five, t_five, inr_five);

        st_five.play();


        containers.add(0, containers.remove(containers.size()-1));

        containers.get(3).setEffect(null);
        containers.get(2).setEffect(g);
    }

}
