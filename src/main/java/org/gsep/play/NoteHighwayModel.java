package org.gsep.play;

import java.util.*;

public class NoteHighwayModel {
    public static final int countInBeats = 4;
    public static final int noteHighwayLength = 700;
    public static final int pointTickRange = 100;
    public static final Note[] emptyTick = new Note[] {Note.OPEN, Note.OPEN, Note.OPEN};

    public static final int scoreMultiplierInterval = 10;
    public static final int scoreMultiplierFactor = 2;
    public static final int currencyInterval = 5;

    private int tick = 0;
    private Map<Integer, Note[]> songSequence;
    private Note[] bottom = emptyTick;
    private Boolean scoreRegistered = false;
    private Integer bottomTickPosition = null;

    private int noteStreak = 0;
    private int scoreMultiplier = 1;
    private int score = 0;
    private int currency = 0;

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

        int bottomRangeMax = tick-noteHighwayLength+pointTickRange/2; //the current leading end of the pointTickRange

        if (songSequence.containsKey(bottomRangeMax)){
            //found new set of notes that fall in range
            bottom = songSequence.get(bottomRangeMax);
            bottomTickPosition = bottomRangeMax;
            scoreRegistered = false;
        } else if (bottomTickPosition != null && bottomRangeMax-bottomTickPosition > pointTickRange){
            //current notes in range
            bottom = emptyTick;
            bottomTickPosition = null;
            if (!scoreRegistered){
                resetStreak();
            }
        }
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
        Boolean scored = !scoreRegistered && bottomTickPosition != null && Arrays.equals(strum, bottom);
//        System.out.printf("%s %s\n", Arrays.toString(strum), Arrays.toString(bottom));
        if (scored){
            scoreRegistered = true;
            advanceScoring();
            return bottomTickPosition;
        } else {
            return null;
        }

    }

    private void advanceScoring(){
        noteStreak += 1;
        scoreMultiplier = noteStreak%scoreMultiplierInterval == 0 ? scoreMultiplier*scoreMultiplierFactor : scoreMultiplier;
        score = score + scoreMultiplier;
        currency = score%currencyInterval == 0 && currency < 5 ? currency+1 : currency;
    }

    private void resetStreak(){
        noteStreak = 0;
        scoreMultiplier = 1;
    }

    public int getNoteStreak() {
        return noteStreak;
    }

    public int getMultiplier() { return scoreMultiplier; }

    public int getScore(){
        return score;
    }

    public int getCurrency(){return currency;}
    public int getTickPosition(){
        return tick;
    }
}