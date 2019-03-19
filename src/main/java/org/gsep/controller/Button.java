package org.gsep.controller;//package org.gsep.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
/**
 * A Button object represents a button/component on the plastic guitar org.gsep.controller.
 *
 * @author  Abigail Lilley
 * @version 1.0, March 2019.
 */
public class Button implements Runnable {
    private final static String GUITAR_HERO = "Guitar Hero"; /* Identifier  */
    private final static int DELAY = 50;

    private ButtonState _state = ButtonState.OFF;
    private List _listeners = new ArrayList();
	private String _name;
	private int _num;

    /**
     * Button Constructor.
     * @param name      Human readable name of the button corresponding to the physical guitar org.gsep.controller
     * @param num       Index of this button in the constant cmps[] array (components array)
     */
	public Button( String name, int num ) {
		this._name = name;
		this._num  = num;
	}

    /**
     * Returns the assigned, human readable name of the button.
     * @return      name of the button
     */
	public String getName() {
		return this._name;
	}


    private synchronized void buttonOn() {
            _state = ButtonState.ON;
            _fireMoodEvent();
    }
	
    private synchronized void buttonOff() {
            _state = ButtonState.OFF;
            _fireMoodEvent();
    }
	
    private synchronized void scrollForward() {
            _state = ButtonState.FORWARD;
            _fireMoodEvent();
    }
	
    private synchronized void scrollBackward() {
            _state = ButtonState.BACKWARD;
            _fireMoodEvent();
    }

    /**
     * Adds a listener to the button.
     *
     * @param l      Object of a class that implements ButtonListener
     */
    public synchronized void addButtonListener( ButtonListener l ) {
        _listeners.add( l );
    }
    
    public synchronized void removeButtonListener( ButtonListener l ) {
        _listeners.remove( l );
    }


    /**
     * Informs all of the listeners listening to this button that an event has occurred
     */
    private synchronized void _fireMoodEvent() {

        ButtonEvent state = new ButtonEvent( this, _state );
        for (Object listener : _listeners) {
            ((ButtonListener) listener).stateReceived(this.getName(), state);
        }
    }

    /**
     * Each Button polled forever on it's own thread to ensure a change of state is detected ASAP.
     * Buttons are handled according to the type of data they provide and what will be most useful for the listeners.
     */
    public void run() {

        ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
        Controller[] ctrls = cenv.getControllers();                           /* Gets all controllers registered */

        for (Controller ctrl : ctrls) {
            if (ctrl.getName() != null && ctrl.getName().contains(GUITAR_HERO)) {                       /* Selects only relevant org.gsep.controller */

                Component[] cmps = ctrl.getComponents();                      /* Get all components of the org.gsep.controller */

                float originalVal = cmps[ _num ].getPollData();
	            float oldVal = originalVal;                                   /* Set base value for button */

                if ( _name.equals("strumBar" )) {
                                                                             /* Strum Bar only used for carousels, so
                                                                              has specialised forward/backward states */
                    while (true) {
                        if (ctrl.poll()) {

                            float newVal = cmps[ _num ].getPollData();

                            if (oldVal != newVal) {                         /* State of physical org.gsep.controller changed */

                                if ( newVal == originalVal ) {
                                    buttonOff();
                                } else if ( newVal == 1.0 ) {
                                    scrollForward();
                                } else if ( newVal == -1.0 ) {
                                    scrollBackward();
                                }

                                oldVal = newVal;
                            }

                            try { /* delay */
                                Thread.sleep(DELAY);
                            } catch (Exception exn) {
                                System.out.println(exn);
                                System.exit(1);
                            }
                        }
                    }
                } else if ( _name.equals( "whammy" ) || _name.equals("bender")) {
                            /* Whammy and bender have variable values when 'ON', this method treats them as 'ON'/'OFF'*/
                    while (true) {
                        if (ctrl.poll()) {

                            float newVal = cmps[ _num ].getPollData();

                            if (oldVal != newVal) {

                                if ( oldVal == originalVal ) {
                                    buttonOn();
                                } else if ( newVal == originalVal ) {
                                    buttonOff();
                                }

                                oldVal = newVal;
                            }

                            try { /* delay */
                                Thread.sleep(DELAY);
                            } catch (Exception exn) {
                                System.out.println(exn);
                                System.exit(1);
                            }
                        }
                    }
                } else {                                                         /*All other button are simple ON/OFF*/

                    while (true) {
                        if (ctrl.poll()) {

                            float newVal = cmps[ _num ].getPollData();

                            if (oldVal != newVal) {

                                if (newVal == originalVal) {
                                    buttonOff();
                                } else if (newVal != originalVal) {
                                    buttonOn();
                                }

                                oldVal = newVal;
                            }

                            try { /* delay */
                                Thread.sleep(DELAY);
                            } catch (Exception exn) {
                                System.out.println(exn);
                                System.exit(1);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(GUITAR_HERO + " controller not found");
                System.exit(1);
    }
}