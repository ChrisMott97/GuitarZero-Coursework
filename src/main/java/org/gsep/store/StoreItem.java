package org.gsep.store;

import org.gsep.carousel.Item;

/*
 * MusicItem.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
public class StoreItem extends Item {
    //TODO: Add property to store the type so each type can be distinguished.
    StoreItem(){
        super();
    }
    /**
     * Constructor for the Music Item that passes the arguments to the parent Item.
     *
     * @param text the name of the item.
     * @param url the url to the image of the item.
     */
    StoreItem(String text, String url){
        super(text, url);
    }
}
