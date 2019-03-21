package org.gsep.play;

import java.util.*;

public class NoteHighwayModel {
    public static final int countInBeats = 4;
    public static final int noteHighwayLength = 700;
    public static final int pointTickRange = 100;
    public static final Note[] emptyTick = new Note[] {Note.OPEN, Note.OPEN, Note.OPEN};
    private int tick = 0;
    private Map<Integer, Note[]> songSequence;
    private Note[] bottom = emptyTick;
    private Boolean scoreLock = false;
    private Integer bottomTickPosition = null;
    private int score = 0;

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
        tick++;

        int bottomRangeMax = tick-noteHighwayLength+pointTickRange/2; //the current high end of the pointTickRange

        if (songSequence.containsKey(bottomRangeMax)){
            //found new set of notes that fall in range
            bottom = songSequence.get(bottomRangeMax);
            bottomTickPosition = bottomRangeMax;
            scoreLock = false;
        } else if (bottomTickPosition != null && bottomRangeMax-bottomTickPosition > pointTickRange){
            //current notes in range
            bottom = emptyTick;
            bottomTickPosition = null;
        }

//        System.out.printf("%s  %s\n", bottomRangeMax, Arrays.toString(bottom));
    }

    /**
     * @author Örs Barkanyi
     * Returns the row of notes in the given tick at the top of the highway
     *
     *
     * @return list of notes in that tick or null
     */
    public Note[] top(){
        return songSequence.get(tick);
    }

    /**
     * @author Örs Barkanyi
     * returns the notes that appear at the bottom of the note highway
     * so that user input can be checked against it
     *
     * @return list of note types or null
     */
    public Integer hit(Note[] strum){
        Boolean scored = !scoreLock && bottomTickPosition != null && Arrays.equals(strum, bottom);
//        System.out.printf("%s %s\n", Arrays.toString(strum), Arrays.toString(bottom));
        if (scored){
            scoreLock = true;
            score += 1;
            return bottomTickPosition;
        } else {
            return null;
        }

    }

    public int getScore() {
        return score;
    }

    public int getTickPosition(){
        return tick;
    }
}