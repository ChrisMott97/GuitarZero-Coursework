package org.gsep.play;

import java.util.ArrayList;

/**
 * object for managing the note catching of lanes from controller input
 *
 * @author orsbarkanyi
 */
public class LaneStatus {
    private ArrayList<Note> notes = new ArrayList<>();

    /**
     * method to change the active status of a note in a lane
     *
     * @param active whether the note is active in this lane
     * @param note what note type the note is
     */
    public void change(Boolean active, Note note){
        if (active && !notes.contains(note)){
            notes.add(note);
        } else if (!active && notes.contains(note)){
            notes.remove(note);
        }
    }

    /**
     * method to get resultant note in a lane when multiple notes could be active
     *
     * @return the most recently pressed note
     */
    public Note getNote() {
        if (notes.isEmpty()){
            return Note.OPEN;
        } else {
            return notes.get(notes.size()-1);
        }
    }

    /**
     * method to determine whether the lane has any active notes
     *
     * @return whether the lane has active notes
     */
    public Boolean getActive() {
        return !notes.isEmpty();
    }
}
