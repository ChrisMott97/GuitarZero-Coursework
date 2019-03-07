package org.gsep.cry;

/**
 * ButtonState constants.
 *
 * @author  Abigail Lilley
 * @version 1.0, March 2019.
 */
public class ButtonState {
    public static final ButtonState ON   = new ButtonState( "on" );
    public static final ButtonState OFF = new ButtonState( "off" );
    public static final ButtonState FORWARD   = new ButtonState( "forward" );
    public static final ButtonState BACKWARD = new ButtonState( "backward" );
	
    
    private String _state;
    
    public String toString() {
        return _state;
    }
    
    private ButtonState( String state ) {
        _state = state;
    }
    
}