package org.gsep.play;

import java.util.*;

public class NoteHighwayModel {
    private int beat = 0;
    private int noteHighwayLength;
    private Map<Integer, Note[]> songSequence;

    /**
     * Sets the note sequence that the note highway plays
     *
     * @param songSequence Dictionary of ticks
     */
    public void setSongSequence(Map<Integer, Note[]> songSequence){
        this.songSequence = songSequence;
    }

    public void setNoteHighwayLength(int noteHighwayLength){
        this.noteHighwayLength = noteHighwayLength;
    }

    /**
     * Moves on to the next beat
     */
    public void advance(){
        beat++;
    }

//    /**
//     * returns the notes that appear at the top of the note highway
//     * so that notes can be sent down the highway
//     *
//     * @return list of note types
//     */
//    public Note[] top(long tick){
//        if (songSequence.containsKey((int)tick+noteHighwayLength)){
//
//            System.out.println("note");
//        }
//         return songSequence.get((int)tick+noteHighwayLength);
//
//    }
    public Note[] top(long tick){
        if (songSequence.containsKey((int)tick+noteHighwayLength)){


        }
         return songSequence.get((int)tick+noteHighwayLength);

    }

    /**
     * returns the notes that appear at the bottom of the note highway
     * so that user input can be checked against it
     *
     * @return list of note types
     */
    public Note[] bottom(long tick){
        if (songSequence.containsKey((int)tick)){
            System.out.println("note");
        }
        return songSequence.get(tick);
    }
}