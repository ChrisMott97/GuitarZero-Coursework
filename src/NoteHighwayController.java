import java.util.Timer;
import java.util.TimerTask;

public class NoteHighwayController {
    private NoteHighwayModel model;
    private NoteHighwayView view;
    private int tempo;
    private Note[][] songSequence;

    /**
     * constructor for {@link NoteHighwayController}
     *
     * @param model the NoteHighwayModel
     * @param view the NoteHighwayView
     */
    NoteHighwayController(NoteHighwayModel model, NoteHighwayView view){
        this.model = model;
        this.view = view;
        //loads note sequence and tempo like this temporarily until proprietary files can be loaded
        this.tempo = 120;
        this.songSequence = new Note[][] {
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.BLACK, Note.WHITE, Note.BLACK},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.BLACK, Note.OPEN, Note.OPEN},
        };
    }

    /**
     * plays notes down the highway at a set tempo, mediating between the
     * {@link NoteHighwayModel} and {@link NoteHighwayView}
     */
    public void play(){
        model.setNoteSequence(songSequence);

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                model.advance();
                view.sendNotes(model.top());
            }
        };

        Timer timer = new Timer();

        long period = (long)(60f/(float)tempo*1000);
        timer.scheduleAtFixedRate(repeatedTask,0, period);
    }

}
