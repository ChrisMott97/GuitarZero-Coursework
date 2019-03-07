package org.gsep.play;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteHighwayView {
    private Canvas canvas;
    private List<NoteSprite> noteSprites = Collections.synchronizedList(new ArrayList<NoteSprite>());
    private AnimationTimer animationTimer;
    private double noteHighwayPeriod;
    private int noteHighwayLength;
    
    /**
     * Constructor for {@link NoteHighwayView} which initialises the game clock
     */
    NoteHighwayView(Canvas canvas){
        this.canvas = canvas;

        this.animationTimer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
            if (noteSprites.size() > 1) {
                canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());

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
            }
        };
    }

    public void startRender(){
        this.animationTimer.start();
    }

    public void setPeriod(double period){
        this.noteHighwayPeriod = noteHighwayLength*period;
    }

    public void setNoteHighwayLength(int noteHighwayLength) {
        this.noteHighwayLength = noteHighwayLength;
    }

    /**
     * Sends notes down the highway, determining what colour they are based on their type
     * and queueing them to be rendered
     *
     * @param notes the notes corresponding to each lane
     */
    //for each notes element (contains 3 element within it)
    public void sendNotes(Note[] notes){
        Lane[] lanes = Lane.values();
        for (int i = 0; i < notes.length; i++){
            if (notes[i] != Note.OPEN){
                NoteSprite noteSprite = new NoteSprite(notes[i], lanes[i]);
                noteSprites.add(noteSprite);
            }
        }
        //if there are more notes than can be displayed, remove note sprites
        if (noteSprites.size() > noteHighwayLength*notes.length) {
            for (int i = 0; i < notes.length; i++){
                noteSprites.remove(0);
            }
        }
    }
}