package org.gsep.manager;
/*
 * 
 * Jackson maps the fields of a Java object to the fields of a JSON object. This class is of type Song, so it allows us to create a JSON object 
 * of type Song with the desired parameters.
 *
 *
 *
 * @author  Humzah Malik
 * @version 1.00, March 2019.
 */


public class Song {
    private int id;
    private String name;
    
    /**
     * Constructor for song class
     * @param id
     * @param name
     */
    public Song(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    /**
     *  By default Jackson maps the fields of a Java object to fields in a JSON object by 
     *	matching the names of the JSON field to the getter and setter methods in the Java object.
     *	
     *	This is a getter to match the name to a JSON field.
     *	
     * @return id The id of song corresponding to its index. 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets id of song
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets name of song
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of song
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}