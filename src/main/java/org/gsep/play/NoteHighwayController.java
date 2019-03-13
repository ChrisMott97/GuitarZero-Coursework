package org.gsep.play;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NoteHighwayController {
    private NoteHighwayModel model;
    private NoteHighwayView view;

    /**
     * @author Örs Barkanyi
     * Constructor for {@link NoteHighwayController}
     *
     * @param model the NoteHighwayModel
     * @param view the NoteHighwayView
     */
    NoteHighwayController(NoteHighwayModel model, NoteHighwayView view){
        this.model = model;
        this.view = view;
    }

    /**
     * @author Örs Barkanyi
     *
     * Plays a midi file and advances the model and updates the view using a separate thread that polls the current
     * position of the sequencer
     * {@link NoteHighwayModel} and {@link NoteHighwayView}
     */
    public void play(Map<Integer, Note[]> songSequence, File midiFile){
        model.setSongSequence(songSequence);

        try{
            Sequence midiSequence = MidiSystem.getSequence(midiFile);
            Sequencer midiSequencer = MidiSystem.getSequencer();

            midiSequencer.open();
            midiSequencer.setSequence(midiSequence);

            long period = (long)midiSequencer.getTempoInMPQ()/midiSequence.getResolution();

            view.setPeriod(period);

            //advances model to next beat and takes the top of the model and gives it to the view
            Runnable helloRunnable = () -> {
                if (model.top() != null) {
                    view.sendNotes(model.top());
                }
                model.advance();
            };

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(helloRunnable, 0, period, TimeUnit.MICROSECONDS);
            midiSequencer.start();

        } catch (Exception e) {
            System.out.println("Invalid MIDI file");
            e.printStackTrace();
            System.exit(1);
        }
    }

    //lane 1 pass type
    //lane 2 pass type
    //lane 3 pass type
}