package org.gsep.carousel;

import java.util.ArrayList;
import java.util.List;

/*
 * ItemModel.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class ItemModel<T extends Item> {
//Theoretical Carousel

    private List<Item> all;
    private List<Item> visible = new ArrayList<Item>();
    private Item intended;
    private static final int maxVisibleLength = 5;


    /**
     * Used after an empty constructor to load the list.
     *
     * @param all the list of items to be loaded.
     */
    public void loadData(List<Item> all){
        this.all = all;
        this.update();
    }

    /**
     * Ensures that the visible list is kept up to date when the full list is updated.
     * TODO: Refactor with binding or event listeners.
     *
     * @return the visible list.
     */
    private List<Item> update(){
        if(all.size() >= maxVisibleLength){
            visible = all.subList(0, maxVisibleLength);
            intended = visible.get((int)Math.ceil(maxVisibleLength /2));
        }else{
            visible = all;
            intended = visible.get((int)Math.ceil(visible.size()/2));
        }
        return visible;
    }

    /**
     * Getter for the full list of Items.
     *
     * @return the full list of Items.
     */
    public List<Item> getAll() {
        return all;
    }

    /**
     * Getter for the list of visible Items.
     *
     * @return the list of visible Items.
     */
    public List<Item> getVisible() {
        return visible;
    }

    /**
     * Getter for the intended Item (the one in the middle of the carousel).
     *
     * @return the intended item.
     */
    public Item getIntended(){
        return intended;
    }

    /**
     * Cycles the carousel so that the previous Item becomes the intended.
     *
     * @return the resulting visible list.
     */
    public List<Item> previous(){
        Item moving = all.remove(all.size()-1);
        all.add(0, moving);
        update();
        return visible;
    }

    /**
     * Cycles the carousel so that the next Item becomes the intended.
     *
     * @return the resulting visible list.
     */
    public List<Item> next(){
        Item moving = all.remove(0);
        all.add(moving);
        update();
        return visible;
    }
}
