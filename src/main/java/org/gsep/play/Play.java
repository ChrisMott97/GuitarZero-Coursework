package org.gsep.play;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView; 
import javafx.scene.layout.*;

public class Play extends Application{
    private final int CANVASWIDTH = 950;
    private final int CANVASHEIGHT = 700;
    private ArrayList<Note[]> songSequenceHolder;
    private String f= getClass().getResource("/noteFile.txt").getFile();
    private Note[] notesHolder; 
    private ArrayList<String[]> songNotes;
    private ArrayList<Note[]> songSequence;
    LinkedHashMap mapA;

    public static void main(String[] args) {
        launch(args);
    }
    

    public void start(Stage stage) throws IOException{
    	/*
        stage.setTitle("Play");

        //initialise scene
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        Canvas canvas = new Canvas(CANVASWIDTH, CANVASHEIGHT);

        root.getChildren().add(createBackground());
        root.getChildren().add(canvas);

        //temporary to find point values
        scene.setOnMouseClicked(e -> {
            System.out.println(e.getSceneX());
            System.out.println(e.getSceneY());
        });

        NoteHighwayModel model = new NoteHighwayModel();
        NoteHighwayView view = new NoteHighwayView(canvas);
        NoteHighwayController controller = new NoteHighwayController(model, view);
        controller.arraySetter(songToGameNotes(readFile(f)));

        stage.show();
        view.startRender();
        controller.play();
        */
    	
    		//Hash map function
    		mapA=arrayToDictionary(songToGameNotes(readFile(f)), readFile(f));
    	
    }
    
    /**
     * @author humzahmalik
     * Setting image view
     */
    public ImageView createBackground(){
        File file = new File(getClass().getResource("/play/highway.png").getFile());
        Image image = new Image(file.toURI().toString());

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(CANVASWIDTH);
        imageView.setPreserveRatio(true);
        

        return imageView;
    }
    
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
		    
		    in.readLine(); 
			
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
     * Method checking whether number corresponds to black, white or empty not
     * @return Note value
     */
    
    public Note checkNote(int num) {
    		Note type = null;
    		
    		if(num==0) {
    			type= Note.OPEN;
    		}
    		if(num==1) {
    			type= Note.BLACK;
    		}
    		if(num==2) {
    			type= Note.WHITE;
    		}
    		
    		return type;
    	
    }

    /**
     * @author humzahmalik

     * Converts arraylist into a notes array
     * @return 
     */
    public ArrayList<Note[]> songToGameNotes(ArrayList<String[]> songNotes) {
  
    		songSequenceHolder = new ArrayList<Note[]>();
    		
    		//For each line of notes in the arraylist
	    	 for (String[] i : songNotes) { 
	    		 	//Create array of array to hold every three notes
	     		notesHolder=new Note[] {null, null, null};
	     		
	     		//For each element s in the list {}
	     		for (int s = 1; s < i.length; s++) {
	     			notesHolder[s-1]=checkNote(Integer.parseInt(i[s]));
	     		}
			
	             
	             //add the created arraylist to the large arraylist
	             songSequenceHolder.add(notesHolder);
	             
	        }
	    	 
	    	 //Return final array  	 
	    	 return songSequenceHolder;
    }

    /**
     * @author humzahmalik
     * Takes the list of notes and converts it to a dictionary with the key equaling the tick time.
     * 
     */
    public LinkedHashMap arrayToDictionary(ArrayList<Note[]> listInput, ArrayList<String[]> fileList){
    		mapA = new LinkedHashMap();
    		
    		//For each element s in the list {}
     		for (int i = 1; i < listInput.size(); i++) {
     			mapA.put(fileList.get(i)[0], listInput.get(i));  
     		}
		
    		return mapA;
    		
    		
    		
    }

}