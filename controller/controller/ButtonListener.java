package controller;//package controller;

/**
 * controller.ButtonListener interface.
 *
 * @author  Abigail Lilley
 * @version 1.0, March 2019.
 */
public interface ButtonListener
{
    /**
     * This method will react and handle events that occur to the Buttons
     * being listened to by an implementation of controller.ButtonListener.
     * @param buttonName assigned name of the button to make the code more readable and intuitive.
     *                   Implementations process the event depending on the button, identified by this name.
     * @param event event triggered. The controller.Button's state can be found from this.
     */
    public void stateReceived( String buttonName, ButtonEvent event );
}