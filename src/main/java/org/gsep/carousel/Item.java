package org.gsep.carousel;

/*
 * Item.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public abstract class Item {

    private String text;

    private String imageURL;

    /**
     * Constructor for an Item with no image.
     *
     * @param text name of the item.
     */
    public Item(String text){
        this.text = text;
    }

    /**
     * Constructor for an Item with a title and image.
     *
     * @param text name of the item.
     * @param imageURL the relative path to the item image.
     */
    public Item(String text, String imageURL){
        this.text = text;
        this.imageURL = imageURL;
    }//TODO: Change to new item based on ID

    /**
     * Getter for text.
     *
     * @return text.
     */
    public String getText(){
        return this.text;
    }

    /**
     * Setter for Image Url if not initially set.
     *
     * @param imageURL relative path to image.
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Getter for Image Url.
     *
     * @return relative path to image.
     */
    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return text;
    }
}
