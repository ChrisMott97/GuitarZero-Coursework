/**
 * Place holder PlayMode to simulate real game action.
 *
 * @author  Abigail Lilley
 * @version 1.0, March 2019.
 */
public class PlayMode implements ButtonListener {

   static volatile Note[] currentPhysicalFretboard = {Note.OPEN, Note.OPEN, Note.OPEN};

    /**
     * Method reacts every time an event occurs to simulate Play Mode
     * @param buttonName assigned name of the button to make the code more readable and intuitive.
     *                   Implementations process the event depending on the button, identified by this name.
     * @param event event triggered. The Button's state can be found from this.
     */
    public void stateReceived(String buttonName, ButtonEvent event) {


        if (event.state() == ButtonState.ON) {
            switch (buttonName) {
                case "fret1_white":
                    currentPhysicalFretboard[1] = Note.WHITE;

            }

        } else if (event.state() == ButtonState.OFF) {
            switch (buttonName) {
                case "fret1_white":
                    currentPhysicalFretboard[1] = Note.OPEN;
            }


//        if( event.state() == ButtonState.ON )
//        {
//            System.out.println( buttonName + " is ON" );
//        }
//        else if( event.state() == ButtonState.OFF )
//        {
//            System.out.println( buttonName + " is OFF" );
//        }
//        else if( event.state() == ButtonState.FORWARD )
//        {
//            System.out.println( buttonName + " moves forward" );
//        }
//        else if( event.state() == ButtonState.BACKWARD )
//        {
//            System.out.println( buttonName + " moves backward" );
//        }
//        else {
//            System.out.println("Uh Oh!");
//        }
        }
        printArray(currentPhysicalFretboard);
    }

    public void printArray (Note[] notes) {
        String[] stringNotes = new String[3];
        for (int i = 0; i < notes.length; i++) {
            System.out.println(notes[i].toString());
        }
        System.out.println("\n");
    }

}    