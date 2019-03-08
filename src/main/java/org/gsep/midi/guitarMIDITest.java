package org.gsep.midi;

import org.junit.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class guitarMIDITest {
    ArrayList<String> arr = new ArrayList<>();

    @Before
    public void setUp() throws InvalidMidiDataException, IOException {


    }
    public void tearDown(){

    }

    /**
     * Creates an array and checks if the created file is the same as the array
     * @throws IOException
     */
    @Test
    public void testWriteToFile() throws IOException {
        arr.add("Hello World");
        arr.add("Hello");
        arr.add("This is a test");
        arr.add("The test is done");
        arr.add("Bye");
        File noteFile = guitarMIDI.writeToFile(arr);
        FileReader fr = new FileReader(noteFile);
        BufferedReader br = new BufferedReader(fr);
        String line;
        ArrayList<String> filearr = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            filearr.add(line);
        }
        assertEquals(arr,filearr);
    }

    /**
     * Compares the array returned by the getTrackNotes method to a file
     * i've created that I know is correct
     * @throws IOException
     * @throws InvalidMidiDataException
     */
    @Test
    public void testGetTrackNotesGuitar() throws IOException, InvalidMidiDataException {

        ClassLoader classLoader = new guitarMIDI().getClass().getClassLoader();
        FileReader fr = new FileReader(classLoader.getResource("songs/notes/actualMamaDo.txt").getFile());
        BufferedReader br = new BufferedReader(fr);
        String line;
        ArrayList<String> filearr = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            filearr.add(line);
        }
        String fileName = "songs/midi/1.mid";
        Sequence seq = MidiSystem.getSequence( new File( classLoader.getResource(fileName).getFile() ) );
        Track[] track = seq.getTracks();
        assertEquals(filearr,guitarMIDI.getTrackNotes(track[0]));
    }
    @Test
    public void testGetTrackNotesNoGuitar(){

    }

    @Test
    public void testConcatArr(){

    }
}
