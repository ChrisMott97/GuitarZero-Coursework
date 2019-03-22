package org.gsep.play;

import org.gsep.play.exception.MidiPlayerException;

import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * note highway controller
 *
 * @author orsbarkanyi
 */
public class NoteHighwayController {
    private NoteHighwayModel model;
    private NoteHighwayView view;
    private LaneStatus leftLaneStatus = new LaneStatus();
    private LaneStatus middleLaneStatus = new LaneStatus();
    private LaneStatus rightLaneStatus = new LaneStatus();
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

    /**
     * Constructor for {@link NoteHighwayController}
     * @author Örs Barkanyi
     *
     * @param model the NoteHighwayModel
     * @param view the NoteHighwayView
     */
    NoteHighwayController(NoteHighwayModel model, NoteHighwayView view){
        this.model = model;
        this.view = view;
    }

    /**
     * Plays a midi file and advances the model and updates the view using a separate thread that polls the current
     * position of the sequencer
     *
     * @author Örs Barkanyi
     *
     * @param songSequence the notes to play along the note highway
     * @param midiFile the midi file to play with {@link MidiPlayer}
     */
    public void play(Map<Integer, Note[]> songSequence, File midiFile) throws MidiPlayerException {
        model.setSongSequence(songSequence);

        MidiPlayer midiPlayer = new MidiPlayer(midiFile);

        long tickPeriod = midiPlayer.getMicroSecsPerTick();
        long countInPeriod = midiPlayer.getMicroSecsPerBeat(NoteHighwayModel.countInBeats);
        long noteHighwayPeriod = NoteHighwayModel.noteHighwayLength*tickPeriod;

        view.setNoteHighwayPeriod(noteHighwayPeriod);

        //advances model to next beat and takes the top of the model and gives it to the view
        Runnable tick = () -> {
            if (model.top() != null) {
                view.sendNotes(model.top(), model.getTickPosition());
                view.setNoteStreak(model.getNoteStreak());
                view.setMultiplier(model.getMultiplier());
                view.setScore(model.getScore());
                view.setCurrency(model.getCurrency());
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

        //if successfully scored, remove the corresponding notes from the highway
        Integer hitTick = model.hit(notes);
        if (hitTick != null){
            view.destroyNotes(hitTick);
        }
    }
}