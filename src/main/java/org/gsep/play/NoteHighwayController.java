package org.gsep.play;

import org.gsep.play.exception.MidiPlayerException;

import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NoteHighwayController {
    private Runnable midiPlay;
    private MidiPlayer midiPlayer;
    private boolean isRunning = false;
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
     * @author Abigail Lilley
     *
     * Plays a midi file and advances the model and updates the view using a separate thread that polls the current
     * position of the sequencer
     * {@link NoteHighwayModel} and {@link NoteHighwayView}
     */
    public void play(Map<Integer, Note[]> songSequence, File midiFile) throws MidiPlayerException {
        model.setSongSequence(songSequence);

        midiPlayer = new MidiPlayer(midiFile);

        long tickPeriod = midiPlayer.getMicroSecsPerTick();
        long countInPeriod = midiPlayer.getMicroSecsPerBeat(NoteHighwayModel.countInBeats);
        long noteHighwayPeriod = NoteHighwayModel.noteHighwayLength*tickPeriod;

        view.setNoteHighwayPeriod(noteHighwayPeriod);

        //advances model to next beat and takes the top of the model and gives it to the view
        Runnable tick = () -> {
            if (model.top() != null) {
                view.sendNotes(model.top(), model.getTickPosition());
            }
            model.advance();
        };

        //starts playing the midi file
        midiPlay = () -> midiPlayer.start();
        isRunning = true;

        executor.scheduleAtFixedRate(tick, countInPeriod, tickPeriod, TimeUnit.MICROSECONDS);
        executor.schedule(midiPlay, countInPeriod+noteHighwayPeriod, TimeUnit.MICROSECONDS);
    }

    public void stop() {
        if (isRunning && midiPlayer != null) {
            midiPlay = () -> midiPlayer.stop();
            isRunning = false;
        }
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

        Integer hitTick = model.hit(notes);
        if (hitTick != null){
            view.destroyNotes(hitTick);
            view.setScore(model.getScore());
        }
    }
}