package org.gsep.slash;

import org.gsep.carousel.Item;

/*
 * MenuItem.
 *
 * @author  Chris Mott.
 * @version 1.00, January 2019.
 */
public class MenuItem extends Item {
    MenuItem(){
        super();
    }
    /**
     * Constructor for the Menu Item that passes the arguments to the parent Item.
     *
     * @param text the name of the item.
     * @param url the url to the image of the item.
     */
    MenuItem(String text, String url){
        super(text, url);
    }
}
