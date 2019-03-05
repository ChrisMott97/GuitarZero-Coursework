package org.gsep.play;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteHighwayView {
    private final int CANVASWIDTH = 950;
    private final int CANVASHEIGHT = 700;
    private Canvas canvas = new Canvas (CANVASWIDTH, CANVASHEIGHT);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private List<Sprite> noteSprites = Collections.synchronizedList(new ArrayList<>());  
    
    //HM - Creating constants to hold incrementation and starting positions of sprites
    private int xPosition= 537;
    private int closeness = 60;
    private final double INCREMENTLEFT=.65;
    private final double INCREMENTRIGHT=.3;
    private final double INCREMENTCENTRE=-.2;
    private double incrementY=2;
  
    
    
    /**
     * Constructor for {@link NoteHighwayView} which starts rendering all the sprites within it
     */
    NoteHighwayView(){
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
            		//HM - WHAT DOES THIS DO.
                if (noteSprites.size() > 1) {
                    graphicsContext.clearRect(0,0, CANVASWIDTH, CANVASHEIGHT);
                    for (Sprite noteSprite : noteSprites) {
                        noteSprite.render(graphicsContext);
                        
                        //HM - If note is centre note, ensure its X position stays central as it travels down the board
                       if (noteSprite.getNoteID()==1 ) {
                    	   	noteSprite.setPos(noteSprite.getPosX()+INCREMENTCENTRE, noteSprite.getPosY() + incrementY);
                       }
                     //HM - If note is right-most note, ensure its X position moves to the right as it travels down the board
                       if (noteSprite.getNoteID()==0) {
                   	   	noteSprite.setPos(noteSprite.getPosX()+INCREMENTRIGHT, noteSprite.getPosY() + incrementY);
                      }
                       //HM - If note is left-most note, ensure its X position moves to the left as it travels down the board
                       if (noteSprite.getNoteID()==2) {
                   	   	noteSprite.setPos(noteSprite.getPosX()+-INCREMENTLEFT, noteSprite.getPosY() + incrementY);
                      }
                        
                        //HM- Increase size
                       noteSprite.setNoteSize(noteSprite.getNoteSizeIncrease()+0.45);
                        
                    }
                }
            }
        }.start();
    }

    /**
     * gets the canvas
     *
     * @return the canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Sends notes down the highway, determining what colour they are based on their type
     * and queueing them to be rendered
     *
     * @param notes the notes corresponding to each lane
     */
    
    //for each notes element (contains 3 element within it)
    public void sendNotes(Note[] notes){

        for (int i = 0; i < notes.length; i++){
            Sprite sprite = new Sprite();
            //HM- Set position of note on board and give it an ID number, relating to its position on the board
            sprite.setPos(xPosition - closeness*i, 0);
            sprite.setNoteID(i);
            
            switch (notes[i]){
                case OPEN:
                		sprite.identifyNoteColour(Note.OPEN);
                    break;
                case BLACK:
                    sprite.identifyNoteColour(Note.BLACK);
                    break;
                case WHITE:
                    sprite.identifyNoteColour(Note.WHITE);
                    
            }
            //adding sprite to list of noteSprites
            noteSprites.add(sprite);
        }
        
        //If there are more than 24 elents on the board, remove the sprites from the display.
        //HM - CHANGE THIS TO A POSITION. SAY ONCE IT REACHES Y=300 OR SOMETHING, THEN REMOVE IT FROM THE LIST
        if (noteSprites.get(0).getPosY()>300){
            noteSprites.remove(0);
            noteSprites.remove(0);
            noteSprites.remove(0);
        }
    }
}
