package org.gsep.play;

import java.io.IOException;
import java.util.*;

public class NoteHighwayController {
    private NoteHighwayModel model;
    private NoteHighwayView view;
    private final int noteHighwayLength = 4;

    /**
     * constructor for {@link NoteHighwayController}
     *
     * @param model the NoteHighwayModel
     * @param view the NoteHighwayView
     * @throws IOException 
     */
    NoteHighwayController(NoteHighwayModel model, NoteHighwayView view){
        this.model = model;
        this.view = view;
    }

    /**
     * plays notes down the highway at a set tempo, mediating between the
     * {@link NoteHighwayModel} and {@link NoteHighwayView}
     */
    public void play(Map<Integer, Note[]> songSequence, double tempo){
        model.setSongSequence(songSequence);
        model.setNoteHighwayLength(noteHighwayLength);
        view.setNoteHighwayLength(noteHighwayLength);
        view.setPeriod(tempo);

        
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                //advance model to next beat and takes the top of the model and gives it to the view
                if (model.top() != null){
                    view.sendNotes(model.top());
                    model.advance();
                }else{
                    return;
                }
            }
        };

        Timer timer = new Timer();

        //This calls repeatedTask
        long period = (long)(60f/(float)tempo*1000);
        timer.scheduleAtFixedRate(repeatedTask,0, period);
    }
}
