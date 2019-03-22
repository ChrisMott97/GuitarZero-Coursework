package org.gsep.play;

import javafx.scene.Group;

import java.util.ArrayList;

public class NoteHighwayView {
    public static double noteHighwayPeriod;

    private Renderer renderer;

    private NoteShieldIcon leftNoteShieldSprite = new NoteShieldIcon(Lane.LEFT);
    private NoteShieldIcon middleNoteShieldSprite = new NoteShieldIcon(Lane.MIDDLE);
    private NoteShieldIcon rightNoteShieldSprite = new NoteShieldIcon(Lane.RIGHT);
    private ArrayList<NoteIcon> noteIcons = new ArrayList<>();

    private StreakCounter noteStreakCounter = new StreakCounter();
    private ScoreCounter scoreCounter = new ScoreCounter();
    private MultiplierRoundel multiplierRoundel = new MultiplierRoundel();
    private CurrencyCounter currencyCounter = new CurrencyCounter();

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

        renderer.add(noteStreakCounter);
        renderer.add(multiplierRoundel);
        renderer.add(scoreCounter);
        renderer.add(currencyCounter);
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
    public void sendNotes(Note[] notes, int tickPosition){
        Lane[] lanes = Lane.values();

        for (int i = 0; i < notes.length; i++){
            if (notes[i] != Note.OPEN){
                //assign lane based on position in given notes array
                NoteIcon noteIcon = new NoteIcon(notes[i], lanes[i], tickPosition);

                noteIcons.add(noteIcon);
                renderer.add(noteIcon);
            }
        }
    }

    public void destroyNotes(int tick){
        for (NoteIcon noteIcon : noteIcons){
            if (noteIcon.getTickPosition() == tick) {
                noteIcon.caught();
            }
        }
    }

    public void setNoteStreak(int noteStreak) {
        this.noteStreakCounter.setNoteStreak(noteStreak);
    }

    public void setMultiplier(int multiplier){
        this.multiplierRoundel.setMultiplier(multiplier);
    }

    public void setScore(int score){
        this.scoreCounter.setScore(score);
    }

    public void setCurrency(int currency){this.currencyCounter.setCurrency(currency);}

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