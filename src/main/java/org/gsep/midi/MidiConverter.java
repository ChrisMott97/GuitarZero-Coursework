package org.gsep.midi;//package org.gsep.midi;
//
//import javax.sound.midi.MidiSystem;
//import javax.sound.midi.Sequence;
//import javax.sound.midi.Sequencer;
//import javax.sound.midi.Track;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class MidiConverter {
//
//    public MidiConverter(){
//
//    }
//
//    /**
//     * Takes the name of a MIDI file and returns a .txt file where information for each note is written on a new line.
//     * A line looks as follows: timing, lane, colour
//     *      Timing:         when the note is played in the song
//     *      Lane (1-3):     the path the note takes in play mode
//     *      Colour (0/1):   which button for the lane corresponds to that note (0 for black, 1 for white)
//     * @param MIDIFileName      Name of the MIDI file you wish to convert to a play mode compatible .txt file
//     * @return                  A .txt file, each line has information needed in play mode for a given note
//     */
//    public File convert(String midiFileName){
//
//        //TODO make sure it handles when a) file not found b) input file in not a MIDI file
//        try {
//            File midiFile = new File(getClass().getResource(midiFileName).getFile());
//
//            Sequencer sequencer = MidiSystem.getSequencer();
//            Sequence sequence = MidiSystem.getSequence(midiFile);
//            sequencer.setSequence(sequence);
//
//            List<Track> tracks = Arrays.asList(sequence.getTracks());
//
//            Float bpm = sequencer.getTempoInBPM();
//
//            List<Track> sortedTracks = tracks.sort((Track track1, Track track2) -> track1.size().(track2.size()));
//
//            return xyz(trackArray);
//        } catch ( Exception e ) {
//            System.out.println( e ); System.exit( 1 );
//        }
//        return null;
//    }
//
//    public Track[] sortTracksByLength(Track[] tracks){
//        int longestLength = 0;
//        Track[] tracks = tracks[0];
//
//        for (Track track : tracks) {
//            if (track.size() > longestLength) {
//                longestLength = track.size();
//                longestTrack = track;
//            }
//        }
//
//        return longestTrack;
//    }
//}
