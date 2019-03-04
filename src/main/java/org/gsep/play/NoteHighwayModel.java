package org.gsep.play;

import java.util.*;

public class NoteHighwayModel {
	//number of empty notes to run before the file is played
    private final int lanePositions = 0;
    private final int laneQuantity = 3;
    private Note[][] noteSequence;
    private int beat = 0;

    /**
     * Sets the note sequence that the note highway plays
     *
     * @param noteSequence 2D array of note types to set
     */
    public void setNoteSequence(Note[][] noteSequence){
        this.noteSequence = noteSequence;
        this.beat = 0;
    }

    /**
     * Moves on to the next beat
     */
    public void advance(){
        beat++;
    }

    /**
     * returns the notes that appear at the top of the note highway
     * so that notes can be sent down the highway
     *
     * @return list of note types
     */
    public Note[] top(){

        if (beat-lanePositions < noteSequence.length && beat-lanePositions >= 0)
            return noteSequence[beat-lanePositions];
        else
            return new Note[]{Note.OPEN, Note.OPEN, Note.OPEN};
    }

    /**
     * returns the notes that appear at the bottom of the note highway
     * so that user input can be checked against it
     *
     * @return list of note types
     */
    public Note[] bottom(){
    		
        if (beat < noteSequence.length)
            return noteSequence[beat];
        else
            return new Note[]{Note.OPEN, Note.OPEN, Note.OPEN};
    }
}
