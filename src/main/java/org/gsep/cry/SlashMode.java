package org.gsep.cry;

/**
 * Place holder Slash Mode to simulate real game action.
 *
 * @author  Abigail Lilley
 * @version 1.0, March 2019.
 */
public class SlashMode implements ButtonListener {

    /**
     * Method reacts every time an event occurs to simulate Slash Mode
     * @param buttonName assigned name of the button to make the code more readable and intuitive.
     *                   Implementations process the event depending on the button, identified by this name.
     * @param event event triggered. The Button's state can be found from this.
     */
    public void stateReceived(String buttonName, ButtonEvent event) {

        if (event.state() == ButtonState.ON) {
            switch (buttonName) {
                case "escape":
                    System.out.println("******* Going to slash mode *********");
                    break;
                case "zeroPower":
                    System.out.println("******* Selecting the mode *********");
                    break;
            }
        } else if (event.state() == ButtonState.FORWARD) {
            System.out.println("******* Scroll carousel forwards one place *********");
        } else if (event.state() == ButtonState.BACKWARD) {
            System.out.println("******* Scroll carousel backwards one place *********");
        }
        /* Ignore all other events */
    }
}