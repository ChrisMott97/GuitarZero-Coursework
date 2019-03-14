package org.gsep.play;

import org.gsep.play.exception.MidiPlayerException;

import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NoteHighwayController {
    private NoteHighwayModel model;
    private NoteHighwayView view;
    private LaneStatus leftLaneStatus = new LaneStatus();
    private LaneStatus middleLaneStatus = new LaneStatus();
    private LaneStatus rightLaneStatus = new LaneStatus();
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

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
    public void play(Map<Integer, Note[]> songSequence, File midiFile) throws MidiPlayerException {
        model.setSongSequence(songSequence);

        MidiPlayer midiPlayer = new MidiPlayer(midiFile);

        long tickPeriod = midiPlayer.getMicroSecsPerTick();
        long countInPeriod = midiPlayer.getMicroSecsPerBeat(model.getCountInBeats());
        long noteHighwayPeriod = model.getNoteHighwayPeriod(tickPeriod);

        view.setNoteHighwayPeriod(noteHighwayPeriod);

        //advances model to next beat and takes the top of the model and gives it to the view
        Runnable tick = () -> {
            if (model.top() != null) {
                view.sendNotes(model.top());
            }
            model.advance();
        };

        //starts playing the midi file
        Runnable midiPlay = () -> midiPlayer.start();

        executor.scheduleAtFixedRate(tick, countInPeriod, tickPeriod, TimeUnit.MICROSECONDS);
        executor.schedule(midiPlay, countInPeriod+noteHighwayPeriod, TimeUnit.MICROSECONDS);
    }

    public void setLeftLaneStatus(Boolean status, Note note){
        leftLaneStatus.change(status, note);
        view.leftLaneActive(leftLaneStatus.getActive(), leftLaneStatus.getNote());
    }

    public void setMiddleLaneStatus(Boolean status, Note note){
        middleLaneStatus.change(status, note);
        view.middleLaneActive(middleLaneStatus.getActive(), middleLaneStatus.getNote());
    }

    public void setRightLaneStatus(Boolean status, Note note){
        rightLaneStatus.change(status, note);
        view.rightLaneActive(rightLaneStatus.getActive(), rightLaneStatus.getNote());
    }

    public void strum(){
        Note[] notes = new Note[] {leftLaneStatus.getNote(), middleLaneStatus.getNote(), rightLaneStatus.getNote()};
        model.strum(notes);
    }
}