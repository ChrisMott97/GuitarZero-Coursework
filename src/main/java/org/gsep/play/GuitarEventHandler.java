package org.gsep.play;

import org.gsep.controller.ButtonEvent;
import org.gsep.controller.ButtonListener;
import org.gsep.controller.ButtonState;

import java.util.ArrayList;


/**
 * @author Örs Barkanyi
 * @author Abigail Lilley
 * @version 2.0, March 18th 2019
 * Constructor for Play Mode
 *
 * Original keyboard implementation by Örs
 * Conversion to plastic guitar input by Abigail
 */
public class GuitarEventHandler implements ButtonListener {
    private NoteHighwayController controller;
    private ArrayList<String> pressedButtons = new ArrayList<>();

    GuitarEventHandler(NoteHighwayController controller) {
        this.controller = controller;
    }


    public void stateReceived(String buttonName, ButtonEvent event) {

        Boolean setStatus = false;
        boolean firstCall = false;

        if (event.state() == ButtonState.ON){
            if(!pressedButtons.contains(buttonName)){
                pressedButtons.add(buttonName);
                firstCall = true;
            }
            setStatus = true;
        } else if (event.state() == ButtonState.OFF){
            if(pressedButtons.contains(buttonName)){
                pressedButtons.remove(buttonName);
                firstCall = true;
            }
        }

        if (firstCall){
            switch (buttonName){
                case "fret1_black":
                    controller.setLeftLaneStatus(setStatus, Note.BLACK);
                    break;
                case "fret2_black":
                    controller.setMiddleLaneStatus(setStatus, Note.BLACK);
                    break;
                case "fret3_black":
                    controller.setRightLaneStatus(setStatus, Note.BLACK);
                    break;
                case "fret1_white":
                    controller.setLeftLaneStatus(setStatus, Note.WHITE);
                    break;
                case "fret2_white":
                    controller.setMiddleLaneStatus(setStatus, Note.WHITE);
                    break;
                case "fret3_white":
                    controller.setRightLaneStatus(setStatus, Note.WHITE);
                    break;
                case "bender":
                    controller.strum();
                    break;
            }

        }
    }
}
