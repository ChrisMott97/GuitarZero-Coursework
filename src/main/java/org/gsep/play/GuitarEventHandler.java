package org.gsep.play;

//import javafx.event.EventHandler;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
import org.gsep.controller.ButtonEvent;
import org.gsep.controller.ButtonListener;
import org.gsep.controller.ButtonState;

import java.util.ArrayList;

//public class GuitarEventHandler implements EventHandler<KeyEvent>{
public class GuitarEventHandler implements ButtonListener {
    private NoteHighwayController controller;
    //private ArrayList<KeyCode> pressedKeys = new ArrayList<>();
    private ArrayList<String> pressedButtons = new ArrayList<>();

    GuitarEventHandler(NoteHighwayController controller) {
        this.controller = controller;
    }


    public void stateReceived(String buttonName, ButtonEvent event) {

        //String keycode = keyEvent.getCode();
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
//            System.out.println(keycode);
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
