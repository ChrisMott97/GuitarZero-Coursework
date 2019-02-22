import java.util.Timer;
import java.util.TimerTask;

public class NoteHighwayController {
    private NoteHighwayModel model;
    private NoteHighwayView view;
    private int tempo = 120;

    NoteHighwayController(NoteHighwayModel model, NoteHighwayView view){
        this.model = model;
        this.view = view;
    }

    public void play(){
        Note[][] songSequence = {
                {Note.BLACK, Note.OPEN, Note.OPEN},
                {Note.OPEN, Note.WHITE, Note.OPEN},
                {Note.OPEN, Note.OPEN, Note.BLACK},
                {Note.BLACK, Note.WHITE, Note.BLACK}
        };

        model.setNoteSequence(songSequence);

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                model.advance();
                view.nextBeat(model.top());
            }
        };

        Timer timer = new Timer();

        long period = (long)(60f/(float)tempo*1000);
        timer.scheduleAtFixedRate(repeatedTask,0, period);
    }

}
