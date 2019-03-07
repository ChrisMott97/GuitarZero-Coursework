package org.gsep.play;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class NoteHighwayController {
    private NoteHighwayModel model;
    private NoteHighwayView view;
    private double tempo;
    private ArrayList<Note[]> songSequence;
    
    /**
     * @author humzahmalik
     * Getter for the song sequence
     * @return 
     * @return 
     */
    public void arraySetter(ArrayList<Note[]> songSequence){
    		this.songSequence=songSequence;
    }

    
    
    /**
     * constructor for {@link NoteHighwayController}
     *
     * @param model the NoteHighwayModel
     * @param view the NoteHighwayView
     * @throws IOException 
     */
    NoteHighwayController(NoteHighwayModel model, NoteHighwayView view) throws IOException{
        this.model = model;
        this.view = view;
        //loads note sequence and tempo like this temporarily until proprietary files can be loaded
        this.songSequence =  songSequence;
       

        
        this.tempo = 100;
        view.setPeriod((long)(60f/(float)tempo*1000));
    }



    /**
     * plays notes down the highway at a set tempo, mediating between the
     * {@link NoteHighwayModel} and {@link NoteHighwayView}
     */
    public void play(){
        model.setNoteSequence(songSequence);
        
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
            		//advanced model to next beat and takes the top of the model and gives it to the view
                model.advance();
                view.sendNotes(model.top());
            }
        };

        Timer timer = new Timer();

      //This calls repeatedTask
        long period = (long)(60f/(float)tempo*1000);
        timer.scheduleAtFixedRate(repeatedTask,0, period);
    }

}
