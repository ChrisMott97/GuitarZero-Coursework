package org.gsep.play;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class NoteHighwayController {
    private NoteHighwayModel model;
    private NoteHighwayView view;
    private int tempo;
    private ArrayList<Note[]> songSequenceHolder;
    Note[] notesHolder; 
    
    private ArrayList<Note[]> songSequence;
    ArrayList<String[]> songNotes;
   
    
    /**
     * @author humzahmalik
     * Method that reads a file, line by line, into an array.
     * @return 
     * @throws IOException 
     *
     */
    public  ArrayList<String[]> readFile(String f) throws IOException {
    	
			BufferedReader in = new BufferedReader(new FileReader(f));
			String str;
			//Create ArrayList to hold the lists of notes
		    songNotes = new ArrayList<String[]>();

			
			//While there is a line, add it to the list
			while((str = in.readLine()) != null){
				//split string
				String[] split = str.split(",");
				
				//adds the note into a song arraylist
				songNotes.add(split);
				
			}
			
			return songNotes;
    }
    

    /**
     * @author humzahmalik

     * Converts arraylist into a notes array
     * @return 
     */
    public ArrayList<Note[]> songToGameNotes(ArrayList<String[]> arrayList) {
  
    		songSequenceHolder = new ArrayList<Note[]>();
    		
	    	 for (String[] i : arrayList) { 
	    		 	String zero = "0";
	    		 	//Create array of array to hold every three notes
	     		notesHolder=new Note[] {null, null, null};
	     		
	     		 if (i[2].equals("0")) {
	     		 	notesHolder[0]=Note.WHITE;
	     		 	System.out.println("Row " + i + " is white");
		     	 }
		     	 
	     		 else if (i[2].equals("1")) {
		     		 notesHolder[0]=Note.BLACK;
		     	 }
	            
	             //if the list has empty elements, add an open note object to that index
	             for(int p = 0; p < notesHolder.length; p++) {
	            	 	if (notesHolder[p]==null) {
	            	 		notesHolder[p]=Note.OPEN;
	            	 	}
	             }
			
	             
	             //add the created arraylist to the large arraylist
	             songSequenceHolder.add(notesHolder);
	             
	        }
	    	 	    	 
	    	 return songSequenceHolder;
    }
    
    
    /**
     * constructor for {@link NoteHighwayController}
     *
     * @param model the NoteHighwayModel
     * @param view the NoteHighwayView
     * @throws IOException 
     */
    NoteHighwayController(NoteHighwayModel model, NoteHighwayView view) throws IOException{
        this.model = model;
        this.view = view;
        //loads note sequence and tempo like this temporarily until proprietary files can be loaded
        this.tempo = 1000;
        this.songSequence =   songToGameNotes(readFile(getClass().getResource("/notes.txt").getFile()));
      

        
    }

    /**
     * plays notes down the highway at a set tempo, mediating between the
     * {@link NoteHighwayModel} and {@link NoteHighwayView}
     */
    public void play(){
        model.setNoteSequence(songSequence);
        
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
            		//advanced model to next beat and takes the top of the model and gives it to the view
                model.advance();
                view.sendNotes(model.top());
            }
        };

        Timer timer = new Timer();

        long period = (long)(60f/(float)tempo*7000);
      //This calls repeatedTask
        timer.scheduleAtFixedRate(repeatedTask,0, period);
    }
    /**
     * @author humzahmalik
     * Updates view with correct score and displays score
     */
    
    public void updateViewScore() {
    		model.setScore(3);
    		view.displayScore(model.getScore());
    }

}
