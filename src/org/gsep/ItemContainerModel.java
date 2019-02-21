package org.gsep;

import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ItemContainerModel {
    private List<ItemContainer> list = new ArrayList<>();
    private ItemContainer current;

    public void loadData(List<ItemContainer> list){
        this.list = list;
        double distance = (list.get(1).getLayoutX()/2)+10;
        for (ItemContainer ic : list) {
            ic.setMoveRight(distance);
            ic.setHalfMoveRight(distance/2);
            ic.setJumpLeft(-(this.list.get(this.list.size()-1)).getLayoutX()-(distance/2)+20);
        }
    }

    public void next(){
        for (ItemContainer ic : list) {
            if(ic == list.get(list.size()-1)){
                ic.getCycle().play();
                break;
            }
            ic.getMoveRight().play();


        }
    }
}
