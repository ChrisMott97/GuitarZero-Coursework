package org.gsep.carousel;

/*
 * Item.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public abstract class Item {

    private int id;

    private String name;

    private String imageURL;

    /**
     * Constructor for an Item with no image.
     */
    public Item(){

    }

    /**
     * Constructor for an Item with a title and image.
     *
     * @param name name of the item.
     * @param imageURL the relative path to the item image.
     */
    public Item(String name, String imageURL){
        this.name = name;
        this.imageURL = imageURL;
    }//TODO: Change to new item based on ID

    /**
     * Getter for name.
     *
     * @return name.
     */
    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return name;
    }
}
