/**
 * Place holder PlayMode to simulate real game action.
 *
 * @author  Abigail Lilley
 * @version 1.0, March 2019.
 */
public class PlayMode implements ButtonListener {

    /**
     * Method reacts every time an event occurs to simulate Play Mode
     * @param buttonName assigned name of the button to make the code more readable and intuitive.
     *                   Implementations process the event depending on the button, identified by this name.
     * @param event event triggered. The Button's state can be found from this.
     */
    public void stateReceived(String buttonName, ButtonEvent event) {

        if( event.state() == ButtonState.ON )
        {
            System.out.println( buttonName + " is ON" );
        }
        else if( event.state() == ButtonState.OFF )
        {
            System.out.println( buttonName + " is OFF" );
        }
        else if( event.state() == ButtonState.FORWARD )
        {
            System.out.println( buttonName + " moves forward" );
        }
        else if( event.state() == ButtonState.BACKWARD )
        {
            System.out.println( buttonName + " moves backward" );
        }
        else {
            System.out.println("Uh Oh!");
        }
    }
}    