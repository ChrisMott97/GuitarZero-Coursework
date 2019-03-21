package org.gsep.carousel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.scene.image.Image;

import java.io.File;

/*
 * Item.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public abstract class Item {

    private int id;

    private String name;

    @JsonIgnore
    private File imageFile;

    @JsonIgnore
    private String prefix;

    public Item() {
    }

    /**
     * Getter for name.
     *
     * @return name.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Setter for name
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for ID, mainly for JSON reading.
     *
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the ID, mainly for JSON writing.
     *
     * @param id the id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the correct file prefix for finding images.
     *
     * @return the string prefix.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the correct file prefix for images.
     *
     * @param prefix the prefix to set.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String toString() {
        return name;
    }
}
