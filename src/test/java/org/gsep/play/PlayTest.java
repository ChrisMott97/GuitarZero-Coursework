package org.gsep.play;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.junit.Test;

public class PlayTest {
	Main p1 = new Main();
	private String f= getClass().getResource("/testNoteFile.txt").getFile();
	
	@Test
	public void testCheckNote() {
		 assertEquals(Note.OPEN, p1.checkNote(0));
		 assertEquals(Note.BLACK, p1.checkNote(1));
		 assertEquals(Note.WHITE, p1.checkNote(2));
	}
	
	@Test
	public void testreadFile() throws IOException {
		//Create a map containing a key and value corresponding to the test file
		LinkedHashMap mapA;
		mapA = new LinkedHashMap();
		Note[] dictValue = new Note[] {null, null, null};
		dictValue[0]=Note.OPEN;
		dictValue[1]=Note.BLACK;
		dictValue[2]=Note.OPEN;
		
		//add to dictionary
		mapA.put(10752,dictValue);
		assertEquals(mapA.size(), p1.readFile(f).size());
		
	}

}