package org.gsep.play;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class CustomReceiver implements Receiver {

    public CustomReceiver() {

    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        ShortMessage msg = (ShortMessage)message;
        System.out.println(msg.getData1());
    }

    @Override
    public void close() {

    }
}
