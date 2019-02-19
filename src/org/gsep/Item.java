package org.gsep;

public abstract class Item {

    private String text;

    public Item(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    @Override
    public String toString() {
        return text;
    }
}
