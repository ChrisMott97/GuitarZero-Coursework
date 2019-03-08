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
    private final int noteHighwayLength = 700;

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



        File file = new File(getClass().getResource("/ORSMIDI.mid").getFile());
        try{
            Sequence midiSequence = MidiSystem.getSequence(file);
            Sequencer midiSequencer = MidiSystem.getSequencer();
            midiSequencer.open();
            midiSequencer.setSequence(midiSequence);
            midiSequencer.start();


            System.out.println((long)6000/midiSequencer.getTempoInMPQ()*midiSequence.getResolution());
            view.setPeriod((long)6000/midiSequencer.getTempoInMPQ()*midiSequence.getResolution());
//            view.setPeriod(4);
            Thread thread = new Thread(){
                @Override
                public void run() {
                    long lastTick = 0;
//                    long time = System.nanoTime();
                    while (midiSequencer.getTickPosition() < midiSequencer.getTickLength()) {
                        if (lastTick < midiSequencer.getTickPosition()){
//                            System.out.println(System.nanoTime() - time);
//                            time = System.nanoTime();
                            advance(midiSequencer.getTickPosition());
                            lastTick = midiSequencer.getTickPosition();
                        }
//                        Thread.sleep(1000);
                    }
                }
            };
            thread.start();


//            ControllerEventListener controllerEventListener = new ControllerEventListener() {
//                public void controlChange(ShortMessage event) {
//                    // TODO convert the event into a readable/desired output
//                    System.out.println(event.);
//                }
//            };
//
//            int[] controllersOfInterest = { 1, 2, 4 };
//            midiSequencer.addControllerEventListener(controllerEventListener, controllersOfInterest);
        } catch (Exception exn) {
            System.out.println(exn); System.exit(1);
        }
        
//        TimerTask repeatedTask = new TimerTask() {
//            public void run() {
//                //advance model to next beat and takes the top of the model and gives it to the view
//                if (model.top() != null){
//                    view.sendNotes(model.top());
//
//                }
//                model.advance();
//            }
//        };
//
//        Timer timer = new Timer();
//
//        //This calls repeatedTask
////        long period = (long)(60f/(float)tempo*1000);
//        timer.scheduleAtFixedRate(repeatedTask,0, (long)tempo);

    }

    public void advance(long tick) {
        //advance model to next beat and takes the top of the model and gives it to the view
        if (model.top(tick) != null){
            view.sendNotes(model.top(tick));

        }
        model.bottom(tick);
//        model.advance();
    }
}