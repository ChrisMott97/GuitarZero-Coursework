package org.gsep.play;

import java.util.*;

public class NoteHighwayModel {
    private int beat = 0;
    private final int noteHighwayLength = 700;
    private Map<Integer, Note[]> songSequence;
    private final int pointTickRange = noteHighwayLength/10;

    /**
     * @author Örs Barkanyi
     * Sets the note sequence that the note highway plays
     *
     * @param songSequence Dictionary of ticks
     */
    public void setSongSequence(Map<Integer, Note[]> songSequence){
        this.songSequence = songSequence;
    }

    public void advance(){
        beat++;
    }

    /**
     * @author Örs Barkanyi
     * Returns the row of notes in the given tick at the top of the highway
     *
     * @param tick the current tick number
     * @return list of notes in that tick or null
     */
    public Note[] top(){
        return songSequence.get(beat);
    }

    /**
     * @author Örs Barkanyi
     * returns the notes that appear at the bottom of the note highway
     * so that user input can be checked against it
     *
     * @return list of note types or null
     */
//    public Boolean inBottomRange(Note[] notes){
//        songSequence.va
//    }

    public void strum(Note[] notes){
//        if (inBottomRange(notes)){
//            System.out.println("point");
//        } else {
//        }
        System.out.println(Arrays.toString(notes));
////            System.out.println(Arrays.toString(bottom()));
    }
}