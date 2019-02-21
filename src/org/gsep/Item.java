package org.gsep;

public abstract class Item {

    private String text;

    private String imageURL;

    public Item(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return text;
    }
}
