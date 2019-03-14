package org.gsep.controller;//package org.gsep.controller;

import java.util.EventObject;

/**
 * ButtonEvent.
 *
 * @author  Abigail Lilley
 * @version 1.0, March 2019.
 */
public class ButtonEvent extends EventObject {
    private ButtonState _state;
    
    public ButtonEvent( Object source, ButtonState state ) {
        super( source );                                           //Source identifies the button triggering the event
        _state = state;
    }

    /**
     * Returns the state object of the event.
     * Expecting this method to be called by an implementation of ButtonListener
     * @return      State object of the event
     */
    public ButtonState state() {
        return _state;
    }
    
}