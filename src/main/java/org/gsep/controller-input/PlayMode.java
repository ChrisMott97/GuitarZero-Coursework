/**
 * Place holder Play Mode to simulate real game action.
 *
 * @author  Abigail Lilley
 * @author  Humzah Malik
 * @version 2.0, March 2019.
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
                    currentPhysicalFretboard[0] = Note.WHITE;
                    break;
                case "fret1_black":
                    currentPhysicalFretboard[0] = Note.BLACK;
                    break;
                case "fret2_white":
                    currentPhysicalFretboard[1] = Note.WHITE;
                    break;
                case "fret2_black":
                    currentPhysicalFretboard[1] = Note.BLACK;
                    break;
                case "fret3_white":
                    currentPhysicalFretboard[2] = Note.WHITE;
                    break;
                case "fret3_black":
                    currentPhysicalFretboard[2] = Note.BLACK;
                    break;
                case "escape":
                    System.out.println("******* Going to slash mode *********");
                    break;
                case "bender":
                    System.out.println("*******   Guitar strummed   *********");
                    break;
            }

        } else if (event.state() == ButtonState.OFF) {
            switch (buttonName) {
                case "fret1_white":
                    currentPhysicalFretboard[0] = Note.OPEN;
                    break;
                case "fret1_black":
                    currentPhysicalFretboard[0] = Note.OPEN;
                    break;
                case "fret2_white":
                    currentPhysicalFretboard[1] = Note.OPEN;
                    break;
                case "fret2_black":
                    currentPhysicalFretboard[1] = Note.OPEN;
                    break;
                case "fret3_white":
                    currentPhysicalFretboard[2] = Note.OPEN;
                    break;
                case "fret3_black":
                    currentPhysicalFretboard[2] = Note.OPEN;
                    break;
            }
        }
        if (!buttonName.equals("strumBar")
                && !buttonName.equals("whammy")
                && !buttonName.equals("zeroPower")
                && !buttonName.equals("escape")
                && !buttonName.equals("bender")
                && !buttonName.equals("power")) {
            printArray(currentPhysicalFretboard);
        }
    }

    public void printArray (Note[] notes) {
        String[] stringNotes = new String[3];
        for (int i = 0; i < notes.length; i++) {
            System.out.println(notes[i].toString());
        }
        System.out.println("\n");
    }

}    