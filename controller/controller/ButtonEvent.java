package controller;//package controller;

import java.util.EventObject;

/**
 * controller.ButtonEvent.
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
     * Expecting this method to be called by an implementation of controller.ButtonListener
     * @return      State object of the event
     */
    public ButtonState state() {
        return _state;
    }
    
}