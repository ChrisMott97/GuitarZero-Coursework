package org.gsep.select;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.gsep.carousel.Item;

import java.io.File;

/*
 * MusicItem.
 *
 * @author  Chris Mott.
 * @version 2.00, March 2019.
 */
@JsonIgnoreProperties({"noteFile", "midiFile"})
public class MusicItem extends Item {
    private File noteFile;
    private File midiFile;

    public MusicItem() {
        super();
    }

    public File getNoteFile() {
        return noteFile;
    }

    public void setNoteFile(File noteFile) {
        this.noteFile = noteFile;
    }

    public File getMidiFile() {
        return midiFile;
    }

    public void setMidiFile(File midiFile) {
        this.midiFile = midiFile;
    }
}
