package org.gsep;

import java.util.ArrayList;
import java.util.List;

/*
 * ItemModel.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class ItemModel<T extends Item> {
//Theoretical Carousel

    private List<T> all;
    private List<T> visible = new ArrayList<T>();
    private T intended;
    private static final int maxVisibleLength = 5;

    ItemModel(){}

    ItemModel(List<T> all){
        this.all = all;
        this.update();
    }

    public void loadData(List<T> all){
        this.all = all;
        this.update();
    }

    private List<T> update(){
        if(all.size() >= maxVisibleLength){
            visible = all.subList(0, maxVisibleLength);
            intended = visible.get((int)Math.ceil(maxVisibleLength /2));
        }else{
            visible = all;
            intended = visible.get((int)Math.ceil(visible.size()/2));
        }
        return visible;
    }

    public List<T> getAll() {
        return all;
    }

    public List<T> getVisible() {
        return visible;
    }

    public Item getIntended(){
        return intended;
    }

    public List<T> previous(){
        System.out.println("Previous...");

        T moving = all.remove(all.size()-1);
        all.add(0, moving);
        update();
        return visible;
    }

    public List<T> next(){
        System.out.println("Next...");

        T moving = all.remove(0);
        all.add(moving);
        update();
        return visible;
    }
}
