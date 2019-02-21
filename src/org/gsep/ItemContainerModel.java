package org.gsep;

import java.util.ArrayList;
import java.util.List;

public class ItemContainerModel<T extends Item> {
    private List<ItemContainer> containers = new ArrayList<>();
    private List<T> items = new ArrayList<>();

    private ItemContainer current;

    private final double betweenContainers = 120;

    public void loadData(List<ItemContainer> list){
        this.containers = list;
        for (ItemContainer ic : list) {
            ic.setMove(betweenContainers);
            ic.setJumpLeft(ic.getTranslateX()-(120*(ic.getInitialPosition()-1)));
            ic.setJumpRight(ic.getTranslateX()+(120*(5-ic.getInitialPosition())));
        }
    }

    public void previous(){
        for (int i = 0; i < containers.size()-1; i++) {
            containers.get(i).getMoveRight().play();
        }
        containers.get(containers.size()-1).getCycleRight().play();


        this.containers.add(0, this.containers.remove(this.containers.size()-1));
    }

    public void next(){
        for (int i = 1; i < containers.size(); i++) {
            containers.get(i).getMoveLeft().play();
        }
        containers.get(0).getCycleLeft().play();


        this.containers.add(this.containers.remove(0));
    }

    public void map(List<T> items){
        this.items = items;
        for (int i = 0; i < 5; i++) {
            containers.get(i).setItem(items.get(i));
        }
    }

    public List<ItemContainer> getContainers() {
        return containers;
    }
}
