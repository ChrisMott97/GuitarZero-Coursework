package org.gsep.play;

import java.util.*;

public class NoteHighwayModel {
	//number of empty notes to run before the file is played
    private final int lanePositions = 4;
    private int beat = lanePositions;
    private ArrayList<Note[]> noteSequence;
    private int score = 0;
    
    /**
     * @author humzahmalik
     * Sets the score of the game
     *
     * @param score - 
     */
    public void setScore(int score){
        this.score=score;
    }
    
    /**
     * @author humzahmalik
     * Gets the score of the game
     *
     * @param score - 
     */
    public int getScore(){
    		return score;
		}

    /**
     * Sets the note sequence that the note highway plays
     *
     * @param songSequence 2D array of note types to set
     */
    public void setNoteSequence(ArrayList<Note[]> songSequence){
        this.noteSequence = songSequence;
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
    public Note[] top(long tick){
        if (songSequence.containsKey((int)tick)){

            System.out.println("note");
        }
         return songSequence.get((int)tick+noteHighwayLength);

    }

    /**
     * returns the notes that appear at the bottom of the note highway
     * so that user input can be checked against it
     *
     * @return list of note types
     */
    public Note[] bottom(){
    		
        if (beat < noteSequence.size())
            return noteSequence.get(beat);
        else
            return new Note[]{Note.OPEN, Note.OPEN, Note.OPEN};
    }
}
