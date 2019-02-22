import java.util.*;

public class NoteHighwayModel {
    private final int lanePositions = 8;
    private final int laneQuantity = 3;
    private Note[][] noteSequence;
    private int beat = 0;

    public void setNoteSequence(Note[][] noteSequence){
        this.noteSequence = noteSequence;
        this.beat = 0;
    }

    public void advance(){
        beat++;
    }

    //notes to appear at the top of the highway
    public Note[] top(){

        if (beat-lanePositions < noteSequence.length && beat-lanePositions >= 0)
            return noteSequence[beat-lanePositions];
        else
            return new Note[]{Note.OPEN, Note.OPEN, Note.OPEN};
    }

    //notes at the end of the highway
    public Note[] bottom(){
        if (beat < noteSequence.length)
            return noteSequence[beat];
        else
            return new Note[]{Note.OPEN, Note.OPEN, Note.OPEN};
    }
}
