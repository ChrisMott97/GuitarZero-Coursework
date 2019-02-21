package org.gsep;

import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ItemContainerModel {
    private List<ItemContainer> list = new ArrayList<>();
    private ItemContainer current;
    private double distance;

    public void loadData(List<ItemContainer> list){
        this.list = list;
        distance = 120;
        for (ItemContainer ic : list) {
            ic.setMoveRight(distance);
            ic.setHalfMoveRight(distance/2);
        }
    }

    public void next(){
        list.get(list.size()-1).setJumpLeft(-600);

        for (int i = 0; i < list.size()-1; i++) {
            list.get(i).getMoveRight().play();
        }
        list.get(list.size()-1).getCycle().play();

        this.list.add(0, this.list.remove(this.list.size()-1));
    }
}
