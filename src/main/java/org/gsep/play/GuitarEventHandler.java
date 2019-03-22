package org.gsep.play;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
//public class GuitarEventHandler implements ButtonListener {
//    private NoteHighwayController controller;
//    private ArrayList<String> pressedButtons = new ArrayList<>();
//
//    GuitarEventHandler(NoteHighwayController controller) {
//        this.controller = controller;
//    }
//
//
//    public void stateReceived(String buttonName, ButtonEvent event) {
//
//        Boolean setStatus = false;
//        boolean firstCall = false;
//
//        if (event.state() == ButtonState.ON){
//            if(!pressedButtons.contains(buttonName)){
//                pressedButtons.add(buttonName);
//                firstCall = true;
//            }
//            setStatus = true;
//        } else if (event.state() == ButtonState.OFF){
//            if(pressedButtons.contains(buttonName)){
//                pressedButtons.remove(buttonName);
//                firstCall = true;
//            }
//        }
//
//        if (firstCall){
//            switch (buttonName){
//                case "fret1_black":
//                    controller.setLeftLaneStatus(setStatus, Note.BLACK);
//                    break;
//                case "fret2_black":
//                    controller.setMiddleLaneStatus(setStatus, Note.BLACK);
//                    break;
//                case "fret3_black":
//                    controller.setRightLaneStatus(setStatus, Note.BLACK);
//                    break;
//                case "fret1_white":
//                    controller.setLeftLaneStatus(setStatus, Note.WHITE);
//                    break;
//                case "fret2_white":
//                    controller.setMiddleLaneStatus(setStatus, Note.WHITE);
//                    break;
//                case "fret3_white":
//                    controller.setRightLaneStatus(setStatus, Note.WHITE);
//                    break;
//                case "bender":
//                    controller.strum();
//                    break;
//            }
//        }
//    }
//}
public class GuitarEventHandler implements EventHandler<KeyEvent> {
    private NoteHighwayController controller;
    private ArrayList<KeyCode> pressedKeys = new ArrayList<>();

    public GuitarEventHandler( NoteHighwayController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode keycode = keyEvent.getCode();
        Boolean setStatus = false;
        Boolean firstCall = false;

        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
            if(!pressedKeys.contains(keycode)){
                pressedKeys.add(keycode);
                firstCall = true;
            }
            setStatus = true;
        } else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED){
            if(pressedKeys.contains(keycode)){
                pressedKeys.remove(keycode);
                firstCall = true;
            }
        }

        if (firstCall){
//            System.out.println(keycode);
            switch (keycode){
                case Q:
                    controller.setLeftLaneStatus(setStatus, Note.BLACK);
                    break;
                case W:
                    controller.setMiddleLaneStatus(setStatus, Note.BLACK);
                    break;
                case E:
                    controller.setRightLaneStatus(setStatus, Note.BLACK);
                    break;
                case A:
                    controller.setLeftLaneStatus(setStatus, Note.WHITE);
                    break;
                case S:
                    controller.setMiddleLaneStatus(setStatus, Note.WHITE);
                    break;
                case D:
                    controller.setRightLaneStatus(setStatus, Note.WHITE);
                    break;
                case SPACE:
                    if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                        controller.strum();
                    break;
            }

        }
    }
}