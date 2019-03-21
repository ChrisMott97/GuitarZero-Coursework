package org.gsep.play;

import org.gsep.play.exception.MidiPlayerException;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.io.File;

public class MidiPlayer {
    private final Sequence sequence;
    private final Sequencer sequencer;

    public MidiPlayer(File file) throws MidiPlayerException {
        try{
            this.sequence = MidiSystem.getSequence(file);
            this.sequencer = MidiSystem.getSequencer();

//            sequencer.setTempoInBPM(60);
            sequencer.open();
            sequencer.setSequence(sequence);
        } catch (Exception e){
            e.printStackTrace();
            throw new MidiPlayerException(e.getMessage());
        }
    }

    public void start(){
        sequencer.start();
    }

    public long getMicroSecsPerTick(){
        return (long)(sequencer.getTempoInMPQ()/sequence.getResolution());
    }

    public long getMicroSecsPerBeat(int beats){
        return (long)(beats*sequencer.getTempoInMPQ());
    }
}
