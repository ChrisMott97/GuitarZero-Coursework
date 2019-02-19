import java.io.File;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

/* TEST*/

public class guitarMIDI {
    final static String FILE = "MamaDo.mid";

    public static String instrumentName( int n ) {
        try {
            final Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            final Instrument[] instrs = synth.getAvailableInstruments();
            synth.close();
            return instrs[ n ].getName();
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 ); return "";
        }
    }

    public static String noteName( int n ) {
        final String[] NAMES =
                { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        final int octave = (n / 12) - 1;
        final int note   = n % 12;
        return NAMES[ note ] + octave;
    }

//    public static void getInstrument(String name){
//
//    }
    public static void displayTrack( Track trk ) {

        int guitarchan = 0;
        for ( int i = 0; i < trk.size(); i = i + 1 ) {
            MidiEvent   evt  = trk.get( i );
            MidiMessage msg = evt.getMessage();
            if ( msg instanceof ShortMessage ) {
                final long         tick = evt.getTick();
                final ShortMessage smsg = (ShortMessage) msg;
                final int          chan = smsg.getChannel();
                final int          cmd  = smsg.getCommand();
                final int          dat1 = smsg.getData1();
                    switch (cmd) {
                        case ShortMessage.PROGRAM_CHANGE:
                            if(instrumentName(dat1).contains("Gt")) {
                                guitarchan = chan;
                                System.out.print("@" + tick + ", ");
                                System.out.println("Program change: " + instrumentName(dat1));
                            }
                            break;
                        case ShortMessage.NOTE_ON:
                            if(guitarchan == chan) {
                                System.out.print("@" + tick + ", ");
                                System.out.println("Note on:  " + dat1);
                            }
                            break;
                        default:
                            /* ignore other commands */
                            break;
                    }
            }
        }
    }
    public static void displaySequence( Sequence seq ) {
        Track[] trks = seq.getTracks();

        for ( int i = 0; i < trks.length; i++ ) {
            System.out.println( "Track " + i );
            displayTrack( trks[ i ] );
        }
    }

    public static void main( String[] argv ) {
        try {
            Sequence seq = MidiSystem.getSequence( new File( FILE ) );
            displaySequence( seq );
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
    }
}
