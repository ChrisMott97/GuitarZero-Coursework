package org.gsep.play;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class GuitarEventHandler implements EventHandler<KeyEvent>{
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
            System.out.println(keycode);
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
                    controller.strum();
                    break;
            }

        }
    }
}
