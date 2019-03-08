package org.gsep.play;

import java.util.*;

public class NoteHighwayModel {
    private int beat = 0;
    private int noteHighwayLength = 700;
    private Map<Integer, Note[]> songSequence;

    /**
     * @author Örs Barkanyi
     * Sets the note sequence that the note highway plays
     *
     * @param songSequence Dictionary of ticks
     */
    public void setSongSequence(Map<Integer, Note[]> songSequence){
        this.songSequence = songSequence;
    }

    /**
     * @author Örs Barkanyi
     * Returns the row of notes in the given tick at the top of the highway
     *
     * @param tick the current tick number
     * @return list of notes in that tick
     */
    public Note[] top(long tick){
        return songSequence.get((int)tick+noteHighwayLength);
    }

    /**
     * @author Örs Barkanyi
     * returns the notes that appear at the bottom of the note highway
     * so that user input can be checked against it
     *
     * @return list of note types
     */
    public Note[] bottom(long tick){
        return songSequence.get(tick);
    }
}