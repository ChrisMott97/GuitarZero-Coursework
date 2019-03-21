package org.gsep.carousel;

import java.util.ArrayList;
import java.util.List;

/*
 * ItemContainerModel.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class ItemContainerModel {
    private List<ItemContainer> containers = new ArrayList<>();
    private List<? extends Item> items = new ArrayList<>();

    private ItemContainer intendedContainer;

    private double betweenContainers = 190;

    /**
     * Loads data into the model.
     *
     * @param list of containers.
     */
    public void loadData(List<ItemContainer> list){
        this.containers = list;
//        betweenContainers = containers.get(1).getTranslateX() - containers.get(0).getTranslateX();
        for (ItemContainer ic : list) {
            ic.setMove(betweenContainers);
            ic.setJumpLeft(ic.getTranslateX()-(betweenContainers*(ic.getInitialPosition()-1)));
            ic.setJumpRight(ic.getTranslateX()+(betweenContainers*(5-ic.getInitialPosition())));
        }
    }

    /**
     * Ensures all animations play and correctly cycles the list of containers.
     */
    public void previous(){
        for (int i = 0; i < containers.size()-1; i++) {
            containers.get(i).getMoveRight().play();
        }
        containers.get(containers.size()-1).getCycleRight().play();


        this.containers.add(0, this.containers.remove(this.containers.size()-1));
    }

    /**
     * Ensures all animations play and correctly cycles the list of containers.
     */
    public void next(){
        for (int i = 1; i < containers.size(); i++) {
            containers.get(i).getMoveLeft().play();
        }
        containers.get(0).getCycleLeft().play();


        this.containers.add(this.containers.remove(0));
    }

    /**
     * Maps the theoretically visible items onto the item containers
     * to ensure the containers are always up to date.
     *
     * @param items the list of items to be mapped.
     */
    public void map(List<? extends Item> items){
        this.items = items;
        if(items.isEmpty())
            return;
        if(items.size() >= 5){
            for (int i = 0; i < 5; i++) {
                if(containers.get(i).getItem() != items.get(i))
                    containers.get(i).setItem(items.get(i));
            }
        }else{
            for (int i = 0; i < items.size(); i++) {
                containers.get(i).setItem(items.get(i));
            }
        }
        intendedContainer = containers.get((int)Math.ceil(items.size()/2));
        unintend();
        intendedContainer.setIntended();
    }

    /**
     * Resets intended status for all item containers.
     */
    private void unintend(){
        for (ItemContainer ic :
                containers) {
            ic.removeIntended();
        }
    }

    /**
     * Getter for list of item containers.
     *
     * @return list of item containers.
     */
    public List<ItemContainer> getContainers() {
        return containers;
    }

    /**
     * Getter for intended item container.
     *
     * @return intended item container.
     */
    public ItemContainer getIntendedContainer() {
        return intendedContainer;
    }
}
