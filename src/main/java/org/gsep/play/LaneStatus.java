package org.gsep.play;

import java.util.ArrayList;

public class LaneStatus {
    private ArrayList<Note> notes = new ArrayList<>();

    public void change(Boolean active, Note note){
        if (active && !notes.contains(note)){
            notes.add(note);
        } else if (!active && notes.contains(note)){
            notes.remove(note);
        }
    }

    public Note getNote() {
        if (notes.isEmpty()){
            return Note.OPEN;
        } else {
            return notes.get(notes.size()-1);
        }
    }

    public Boolean getActive() {
        return !notes.isEmpty();
    }
}
