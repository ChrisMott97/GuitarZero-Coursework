package org.gsep.play;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

public class NoteHighwayView {
    public static double noteHighwayPeriod;
    private Renderer renderer;
    private NoteShieldSprite leftNoteShieldSprite = new NoteShieldSprite(Lane.LEFT);
    private NoteShieldSprite middleNoteShieldSprite = new NoteShieldSprite(Lane.MIDDLE);
    private NoteShieldSprite rightNoteShieldSprite = new NoteShieldSprite(Lane.RIGHT);

    /**
     * @author Örs Barkanyi
     *
     * Constructor for {@link NoteHighwayView} which initialises the game render clock
     */
    NoteHighwayView(Group root){
        this.renderer = new Renderer(root);

        renderer.add(leftNoteShieldSprite);
        renderer.add(middleNoteShieldSprite);
        renderer.add(rightNoteShieldSprite);
    }

    /**
     * @author Örs Barkanyi
     *
     * Starts the game render clock
     */
    public void startRender(){
        this.renderer.start();
    }

    /**
     * @author Örs Barkanyi
     * @param period the time in microseconds taken for notes travel from top to bottom
     */
    public void setNoteHighwayPeriod(double period){
        this.noteHighwayPeriod = period/1000;
    }

    /**
     * @author Örs Barkanyi
     *
     * Sends notes down the highway, determining what colour they are based on their type
     * and queueing them to be rendered and manages the size of the render queue
     *
     * @param notes the notes corresponding to each lane
     */
    public void sendNotes(Note[] notes){
        //initialise note sprites and queue them to render
        Lane[] lanes = Lane.values();
        for (int i = 0; i < notes.length; i++){
            if (notes[i] != Note.OPEN){
                NoteSprite noteSprite = new NoteSprite(notes[i], lanes[i]);
                renderer.add(noteSprite);
            }
        }
    }

    public void leftLaneActive(Boolean status, Note note){
        this.leftNoteShieldSprite.setVisible(status, note);
    }

    public void rightLaneActive(Boolean status, Note note){
        this.rightNoteShieldSprite.setVisible(status, note);
    }

    public void middleLaneActive(Boolean status, Note note){
        this.middleNoteShieldSprite.setVisible(status, note);
    }
}