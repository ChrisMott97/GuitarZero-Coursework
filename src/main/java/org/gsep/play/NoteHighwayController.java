package org.gsep.play;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
            midiSequencer.start();

            view.setPeriod((long)6000/midiSequencer.getTempoInMPQ()*midiSequence.getResolution()*midiSequencer.getTempoFactor());

            //advances the model when there is a change in the sequencer tick position
            Thread thread = new Thread(() -> {
                long lastTick = 0;
                while (midiSequencer.getTickPosition() < midiSequencer.getTickLength()) {
                    if (lastTick < midiSequencer.getTickPosition()){
                        long tick = midiSequencer.getTickPosition();

                        //synchronise view with current tick from model
                        if (model.top(tick) != null){
                            view.sendNotes(model.top(tick));
                        }

                        lastTick = tick;
                    }
                }
            });

            thread.start();
        } catch (Exception e) {
            System.out.println("Invalid MIDI file");
            e.printStackTrace();
            System.exit(1);
        }
    }
}