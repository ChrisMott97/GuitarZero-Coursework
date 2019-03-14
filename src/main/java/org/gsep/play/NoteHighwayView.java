package org.gsep.play;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

import java.util.*;

public class NoteHighwayView {
    private Canvas canvas;
    private List<NoteSprite> noteSprites = Collections.synchronizedList(new ArrayList<>());
    private NoteShieldSprite[] noteShieldSprites;
    private AnimationTimer animationTimer;
    private double noteHighwayPeriod;
    private final int noteHighwayLength = 700;
    private final int numberOfLanes = 3;
    
    /**
     * @author Örs Barkanyi
     *
     * Constructor for {@link NoteHighwayView} which initialises the game render clock
     */
    NoteHighwayView(Canvas canvas){
        this.canvas = canvas;

        this.animationTimer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight()); //clear the canvas

                if (noteSprites.size() > 1) {
                    //render each note in the queue
                    for (NoteSprite noteSprite : noteSprites) {
                        double currentTime = System.currentTimeMillis();
                        double spawnTime = noteSprite.getSpawnTime();
                        double progress = (currentTime-spawnTime)/noteHighwayPeriod;

                        if (progress <= 1){
                            noteSprite.updateProgress(progress);
                            noteSprite.render(canvas.getGraphicsContext2D());
                        }
                    }
                }

                //render each note shield in the queue
                for (NoteShieldSprite noteShieldSprite : noteShieldSprites){
                    noteShieldSprite.render(canvas.getGraphicsContext2D());
                }
            }
        };

        noteShieldSprites = new NoteShieldSprite[] {
                new NoteShieldSprite(Lane.LEFT),
                new NoteShieldSprite(Lane.MIDDLE),
                new NoteShieldSprite(Lane.RIGHT)
        };
    }

    /**
     * @author Örs Barkanyi
     *
     * Starts the game render clock
     */
    public void startRender(){
        this.animationTimer.start();
    }

    /**
     * @author Örs Barkanyi
     * @param period the time in microseconds taken for notes travel from top to bottom
     */
    public void setPeriod(double period){
        this.noteHighwayPeriod = noteHighwayLength*period/1000;
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
                noteSprites.add(0,noteSprite);
            }
        }

        //if there are more notes than can be displayed, remove note sprites
        if (noteSprites.size() > noteHighwayLength*numberOfLanes) {
            for (int i = 0; i < notes.length; i++){
                noteSprites.remove(noteSprites.size()-1);
            }
        }
    }

    public void leftLaneActive(Boolean status, Note note){
        this.noteShieldSprites[0].setVisible(status, note);
    }

    public void rightLaneActive(Boolean status, Note note){
        this.noteShieldSprites[2].setVisible(status, note);
    }

    public void middleLaneActive(Boolean status, Note note){
        this.noteShieldSprites[1].setVisible(status, note);
    }
}