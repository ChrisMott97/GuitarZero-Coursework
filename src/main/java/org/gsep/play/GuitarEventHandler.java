package org.gsep.play;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GuitarEventHandler implements EventHandler<KeyEvent>{
    NoteHighwayController controller;

    public GuitarEventHandler(NoteHighwayController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
//        switch (keyEvent.getCode()){
//            case Q:
//                controller.leftLaneActive(Note.BLACK);
//                break;
//            case W:
//                controller.middleLaneActive(Note.BLACK);
//                break;
//            case E:
//                controller.rightLaneActive(Note.BLACK);
//                break;
//            case A:
//                controller.leftLaneActive(Note.WHITE);
//                break;
//            case S:
//                controller.middleLaneActive(Note.WHITE);
//                break;
//            case D:
//                controller.middleLaneActive(Note.WHITE);
//                break;
//            case SPACE:
//                controller.strum();
//                break;
//        }
    }
}
