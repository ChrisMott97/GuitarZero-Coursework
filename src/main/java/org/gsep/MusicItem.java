package org.gsep;

/*
 * MusicItem.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class MusicItem extends Item {
    /**
     * Constructor for the Music Item that passes the arguments to the parent Item.
     *
     * @param text the name of the item.
     * @param url the url to the image of the item.
     */
    MusicItem(String text, String url){
        super(text, url);
    }
}
